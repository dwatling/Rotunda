package com.synaptik.rotunda.managers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class ImageManager {
	private static final String TAG = "ImageManager";
	static Map<String, Bitmap> mImages;
	
	public static void init(Context c) {
		Log.d(TAG, "init(" + c + ")");
		mImages = new HashMap<String, Bitmap>();
		try {
			AssetManager am = c.getAssets();
			String[] images = am.list("img");
			loadImages(am, images);
		} catch (IOException e) {
			Log.w(TAG, "Unable to load images", e);
		}
	}
	
	private static void loadImages(AssetManager am, String[] images) throws IOException {
		Log.w(TAG, "Found " + images.length + " images.");
		for (String img : images) {
			InputStream is = null;
			try {
				is = am.open("img/" + img);
				Options opts = new Options();
				Bitmap bmp = BitmapFactory.decodeStream(is, null, opts);
				String key = img.substring(0, img.indexOf("."));
				
				mImages.put(key, bmp);
				
				Log.w(TAG, "Loaded image '" + key + "'.");
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
	}
	
	public static Bitmap getBitmap(String key) {
		Bitmap result = null;
		if (mImages != null) {
			if (mImages.containsKey(key)) {
				result = mImages.get(key);
			} else {
				Log.w(TAG, "Unable to find bitmap '" + key + "'");
			}
		}
		return result;
	}
	
	public static Bitmap cloneBitmap(String key, String newKey, Paint paint) {
		Bitmap result = null;
		Bitmap src = getBitmap(key);
		if (src != null) {
			result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
			Canvas c = new Canvas(result);
			c.drawBitmap(src, 0, 0, paint);
			mImages.put(newKey, result);
		}
		return result;
	}
	
	public static void close() {
		Log.d(TAG, "close");
		if (mImages != null) {
			mImages.clear();
			mImages = null;
		}
	}
}