package com.synaptik.rotunda.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
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
	
	public static void init(Context ctx) {
		Log.d(TAG, "init(" + ctx + ")");
		
		mSoundEffects = new ArrayList<SoundEffect>();
		mActiveSounds = new ArrayList<Integer>();
		
		try {
			loadSounds(ctx, ctx.getAssets().list("sound"));
		} catch (IOException ex) {
			Log.e(TAG, "Unable to load sounds.", ex);
		}
		
		try {
			loadMusic(ctx, ctx.getAssets().list("music"));
		} catch (IOException ex) {
			Log.e(TAG, "Unable to load music.", ex);
		}
		
		try {
			loadAmbience(ctx, ctx.getAssets().list("ambience"));
		} catch (IOException ex) {
			Log.e(TAG, "Unable to load ambience.", ex);
		}
	}
	
	public static void close() {
		mMusicPlayer.release();
		mAmbiencePlayer.release();
		mSoundPool.release();
		
		mSoundEffects.clear();
		mSoundEffects = null;
		
		mActiveSounds.clear();
		mActiveSounds = null;
	}
	
	private static void loadSounds(Context ctx, String sounds[]) throws IOException {
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mSounds = new HashMap<String, Integer>();
		for (String sound : sounds) {
			AssetFileDescriptor fd = ctx.getAssets().openFd("sound/" + sound);
			int soundID = mSoundPool.load(fd, 0);
			String key = sound.substring(0, sound.indexOf("."));
			Log.w(TAG, "Loaded sound '" + key + "'.");
			mSounds.put(key, soundID);
		}
	}
	
	private static void loadMusic(Context ctx, String sounds[]) throws IOException {
		mMusicPlayer = new MediaPlayer();
		if (sounds.length > 0) {
			AssetFileDescriptor fd = ctx.getAssets().openFd("music/" + sounds[0]);
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
		}
	}
	
	private static void loadAmbience(Context ctx, String sounds[]) throws IOException {
		mAmbiencePlayer = new MediaPlayer();
		if (sounds.length > 0) {
			AssetFileDescriptor fd = ctx.getAssets().openFd("ambience/" + sounds[0]);
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
