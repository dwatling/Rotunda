package com.synaptik.rotunda;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.synaptik.rotunda.managers.FontManager;
import com.synaptik.rotunda.managers.ImageManager;
import com.synaptik.rotunda.managers.SoundManager;

public abstract class RotundaActivity extends Activity {
	protected DisplayMetrics sScreen;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sScreen = getApplicationContext().getResources().getDisplayMetrics();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
//		
//		ImageManager.init(this);
//		FontManager.init(this);
//		SoundManager.init(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		ImageManager.close();
		FontManager.close();
		SoundManager.close();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}
