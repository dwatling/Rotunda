package com.synaptik.rotunda.managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

public class FontManager {
	private static final String TAG = "FontManager";
	static Map<String, Typeface> mFonts;
	
	public static void init(Context c) {
		Log.d(TAG, "init(" + c + ")");
		mFonts = new HashMap<String, Typeface>();
		try {
			AssetManager am = c.getAssets();
			String[] fonts = am.list("fonts");
			loadFonts(am, fonts);
		} catch (IOException e) {
			Log.w(TAG, "Unable to load images", e);
		}
	}
	
	private static void loadFonts(AssetManager am, String[] fonts) throws IOException {
		Log.w(TAG, "Found " + fonts.length + " fonts.");
		for (String font : fonts) {
			Typeface tf = Typeface.createFromAsset(am, "fonts/" + font);
			String key = font.substring(0, font.indexOf("."));
			mFonts.put(key, tf);
			Log.w(TAG, "Loaded font '" + key + "'.");
		}
	}
	
	public static Typeface getFont(String key) {
		Typeface result = null;
		if (mFonts.containsKey(key)) {
			result = mFonts.get(key);
		} else {
			Log.w(TAG, "Unable to find font '" + key + "'");
		}
		return result;
	}
	
	public static void close() {
		mFonts.clear();
		mFonts = null;
	}
}