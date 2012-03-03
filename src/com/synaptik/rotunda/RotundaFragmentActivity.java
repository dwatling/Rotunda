package com.synaptik.rotunda;

import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View.OnTouchListener;

import com.synaptik.rotunda.managers.FontManager;
import com.synaptik.rotunda.managers.ImageManager;
import com.synaptik.rotunda.managers.SoundManager;

/**
 * @author dan
 */
public abstract class RotundaFragmentActivity extends FragmentActivity implements OnTouchListener {
	protected DisplayMetrics mScreen;
	
	private SafetyTask mTask;
	
	private static final int SAFETY_DELAY = 500;	// User must press the button twice in this timeframe (millis) to trigger button
	
	/**
	 * Prevents accidental back button presses. In order to trigger it, one has to press it twice within 500 milliseconds.
	 * TODO: prevent accidental home button presses
	 * @return
	 */
	public abstract boolean isSafetyEnabled();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		
		mScreen = getApplicationContext().getResources().getDisplayMetrics();
	}
	
	public int getScreenWidth() {
		return this.mScreen.widthPixels;
	}
	
	public int getScreenHeight() {
		return this.mScreen.heightPixels;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
//		ImageManager.init(getApplicationContext());
//		SoundManager.init(getApplicationContext());
//		FontManager.init(getApplicationContext());
	}

	@Override
	protected void onStop() {
		super.onStop();
		ImageManager.close();
		SoundManager.close();
		FontManager.close();
	}
	
	@Override
	public void onBackPressed() {
		if (isSafetyEnabled()) {
			if (safetyCheck()) {
				super.onBackPressed();
			}
		} else {
			super.onBackPressed();
		}
	}
	
	protected boolean safetyCheck() {
		boolean result = false;
		if (mTask == null) {
			mTask = new SafetyTask();
			mTask.execute();
		} else {
			if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
				result = true;
			}
		}
		return result;
	}
	
	class SafetyTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			SystemClock.sleep(SAFETY_DELAY);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			mTask = null;
		}
	}
}
