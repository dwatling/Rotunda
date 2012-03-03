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
	
	static {
		mFonts = new HashMap<String, Typeface>();
	}
	
//	public static void init(Context c) {
//		Log.d(TAG, "init(" + c + ")");
//		mFonts = new HashMap<String, Typeface>();
//		try {
//			AssetManager am = c.getAssets();
//			String[] fonts = am.list("fonts");
//			loadFonts(c, fonts);
//		} catch (IOException e) {
//			Log.w(TAG, "Unable to load images", e);
//		}
//	}
//	
	private static Map<String,String> buildMap(String[] files) {
		Map<String,String> result = new HashMap<String,String>();
		for (String file : files) {
			String key = file.substring(0, file.indexOf("."));
			result.put(key, file);
		}
		return result;
	}
	
	public static void loadFonts(Context ctx, String... fonts) {
		Log.w(TAG, "Found " + fonts.length + " fonts.");
		AssetManager am = ctx.getAssets();
		Map<String,String> keys = new HashMap<String,String>();
		try {
			keys = buildMap(am.list("fonts"));
		} catch (IOException ex) {
			Log.w(TAG, "Unable to retrieve list of images");
		}
		
		for (String font : fonts) {
			Typeface tf = Typeface.createFromAsset(am, "fonts/" + keys.get(font));
			mFonts.put(font, tf);
			Log.w(TAG, "Loaded font '" + font + "'.");
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
	}
}