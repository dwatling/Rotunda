package com.synaptik.rotunda.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.util.Log;

/**
 * TODO - Need to be able to define ambience sound
 * TODO - Need to be able to define music
 * TODO - Load sounds
 * TODO - Ensure ambience sound effects are working
 *  
 * @author dan
 */
public class SoundManager {
	private static final String TAG = "SoundManager";
	
	private static Map<String, Integer> mSounds;
	private static List<SoundEffect> mSoundEffects;
	private static List<Integer> mActiveSounds;
	private static MediaPlayer mMusicPlayer;
	private static MediaPlayer mAmbiencePlayer;
	private static SoundPool mSoundPool;
	
	static {
		mSoundEffects = new ArrayList<SoundEffect>();
		mActiveSounds = new ArrayList<Integer>();
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mSounds = new HashMap<String, Integer>();
	}
	
//	public static void init(Context ctx) {
//		Log.d(TAG, "init(" + ctx + ")");
//		
//		mSoundEffects = new ArrayList<SoundEffect>();
//		mActiveSounds = new ArrayList<Integer>();
//		
//		try {
//			loadSounds(ctx, ctx.getAssets().list("sound"));
//		} catch (IOException ex) {
//			Log.e(TAG, "Unable to load sounds.", ex);
//		}
//		
//		try {
//			loadMusic(ctx, ctx.getAssets().list("music"));
//		} catch (IOException ex) {
//			Log.e(TAG, "Unable to load music.", ex);
//		}
//		
//		try {
//			loadAmbience(ctx, ctx.getAssets().list("ambience"));
//		} catch (IOException ex) {
//			Log.e(TAG, "Unable to load ambience.", ex);
//		}
//	}
//	
	public static void close() {
		if (mMusicPlayer != null) {
			mMusicPlayer.release();
		}
		
		if (mAmbiencePlayer != null) {
			mAmbiencePlayer.release();
		}
		
		if (mSoundPool != null) {
			mSoundPool.release();
		}
		
		if (mSoundEffects != null) {
			mSoundEffects.clear();
		}
		
		if (mActiveSounds != null) {
			mActiveSounds.clear();
		}
	}
	
	private static Map<String,String> buildMap(String[] files) {
		Map<String,String> result = new HashMap<String,String>();
		for (String file : files) {
			String key = file.substring(0, file.indexOf("."));
			result.put(key, file);
		}
		return result;
	}
	
	public static void loadSounds(Context ctx, String... sounds) {
		AssetManager am = ctx.getAssets();
		Map<String,String> keys = new HashMap<String,String>();
		try {
			keys = buildMap(am.list("sound"));
		} catch (IOException ex) {
			Log.w(TAG, "Unable to retrieve list of images");
		}

		for (String sound : sounds) {
			try {
				AssetFileDescriptor fd = am.openFd("sound/" + keys.get(sound));
				int soundID = mSoundPool.load(fd, 0);
				Log.w(TAG, "Loaded sound '" + sound + "'.");
				mSounds.put(sound, soundID);
			} catch (IOException ex) {
				Log.e(TAG, "Unable to load sound 'assets/sound/" + sound + "'");
			}
		}
	}
	
	public static void loadMusic(Context ctx, String... sounds) {
		mMusicPlayer = new MediaPlayer();
		AssetManager am = ctx.getAssets();
		Map<String,String> keys = new HashMap<String,String>();
		try {
			keys = buildMap(am.list("music"));
		} catch (IOException ex) {
			Log.w(TAG, "Unable to retrieve list of images");
		}
		
		if (sounds.length > 0) {
			try {
				AssetFileDescriptor fd = am.openFd("music/" + keys.get(sounds[0]));
				mMusicPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
				mMusicPlayer.prepareAsync();
				mMusicPlayer.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						Log.d(TAG, "Playing Music");
						mp.setLooping(true);
						mp.start();
					}
				});
			} catch (IOException ex) {
				Log.e(TAG, "Unable to load music 'assets/music/" + sounds[0]);
			}
		}
	}
	
	public static void loadAmbience(Context ctx, String... sounds) {
		mAmbiencePlayer = new MediaPlayer();
		AssetManager am = ctx.getAssets();
		Map<String,String> keys = new HashMap<String,String>();
		try {
			keys = buildMap(am.list("ambience"));
		} catch (IOException ex) {
			Log.w(TAG, "Unable to retrieve list of images");
		}
		
		if (sounds.length > 0) {
			try {
				AssetFileDescriptor fd = ctx.getAssets().openFd("ambience/" + keys.get(sounds[0]));
				mAmbiencePlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
				mAmbiencePlayer.prepareAsync();
				mAmbiencePlayer.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						Log.d(TAG, "Playing Ambience");
						mp.setLooping(true);
						mp.start();
					}
				});
			} catch (IOException ex) {
				Log.e(TAG, "Unable to load ambience 'assets/ambience/" + sounds[0]);
			}
		}
	}
	
	public static void setAmbience(Context ctx, String key) {
		mAmbiencePlayer.setLooping(true);
	}
	public static void playMusic(String key) {
		mAmbiencePlayer.setLooping(false);
	}
	public static void setOnMusicEndEvent(MediaPlayer.OnCompletionListener evt) {
		mMusicPlayer.setOnCompletionListener(evt);
	}
	public static void addAmbienceSoundEffect(String key, float volume, float interval, float delayMin, float delayMax) {
		mSoundEffects.add(new SoundEffect(key, volume, interval, delayMin, delayMax));
	}
	
	public static void update(float elapsed) {
		if (mSoundEffects != null) {
			for (SoundEffect s : mSoundEffects) {
				s.delayElapsed += elapsed;
				if (s.delayElapsed > s.targetDelay) {
					playSound(s.key, s.volume);
					s.updateTargetDelay();
				}
			}
		}
	}
	
	public static void playSound(String key) {
		playSound(key, 1.0f);
	}
	
	public static void playSound(String key, float volume) {
		if (mSounds.containsKey(key)) {
			mActiveSounds.add(mSoundPool.play(mSounds.get(key), volume, volume, 1, 0, 1.0f));
		} else {
			Log.w(TAG, "Sound '" + key + "' has not been loaded.");
		}
	}
	
	public static void pause() {
		for (Integer soundStream : mActiveSounds) {
			mSoundPool.pause(soundStream);
		}
		mMusicPlayer.pause();
		mAmbiencePlayer.pause();
	}
	
	public static void resume() {
		mMusicPlayer.start();
		mAmbiencePlayer.start();
		
		for (Integer soundStream : mActiveSounds) {
			mSoundPool.resume(soundStream);
		}
	}
	
	private static class SoundEffect {
		String key;
		float volume;
		float interval;
		float delayMin;
		float delayMax;
		
		float delayElapsed;
		float targetDelay;
		
		public SoundEffect(String key, float volume, float interval, float delayMin, float delayMax) {
			this.key = key;
			this.volume = volume;
			this.interval = interval;
			this.delayMin = delayMin;
			this.delayMax = delayMax;
			updateTargetDelay();
		}
		public void updateTargetDelay() {
			this.delayElapsed = 0.0f;
			this.targetDelay = delayMin;
			// TODO - targetDelay should be a random number between delayMin and delayMax
		}
	}
}
