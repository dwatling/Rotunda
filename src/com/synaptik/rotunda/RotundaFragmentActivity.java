package com.synaptik.rotunda;

import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		
		mScreen = getApplicationContext().getResources().getDisplayMetrics();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ImageManager.init(getApplicationContext());
		SoundManager.init(getApplicationContext());
		FontManager.init(getApplicationContext());
	}

	@Override
	protected void onStop() {
		super.onStop();
		ImageManager.close();
		SoundManager.close();
		FontManager.close();
	}
}
