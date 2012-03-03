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
	
	static {
		mImages = new HashMap<String, Bitmap>();
	}
//	public static void init(Context c) {
//		Log.d(TAG, "init(" + c + ")");
//		mImages = new HashMap<String, Bitmap>();
//		try {
//			AssetManager am = c.getAssets();
//			String[] images = am.list("img");
//			loadImages(c, images);
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
	public static void loadImages(Context ctx, String... images) {
		AssetManager am = ctx.getAssets();
		Map<String,String> keys = new HashMap<String,String>();
		try {
			keys = buildMap(am.list("img"));
		} catch (IOException ex) {
			Log.w(TAG, "Unable to retrieve list of images");
		}
		
		for (String img : images) {
			InputStream is = null;
			try {
				is = am.open("img/" + keys.get(img));
				Options opts = new Options();
				Bitmap bmp = BitmapFactory.decodeStream(is, null, opts);
				
				mImages.put(img, bmp);
				
				Log.w(TAG, "Loaded image '" + img + "'.");
			} catch (IOException ex) {
				Log.e(TAG, "Unable to load image 'assets/img/" + img + "'");
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException ex) {
						// do nothing
					}
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
		if (mImages != null) {
			mImages.clear();
		}
	}
}