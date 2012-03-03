package com.synaptik.rotunda;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.view.MotionEvent;

import com.synaptik.rotunda.managers.ImageManager;

public class Sprite extends MovableActor {
	protected Bitmap mBitmap;
	protected String mBitmapKey;
	
	AnimationListener mListener;
	
	public Sprite(String bitmapKey) {
		super();
		this.setBitmapKey(bitmapKey);
	}
	
	public String getBitmapKey() {
		return this.mBitmapKey;
	}
	
	public void setBitmapKey(String key) {
		this.mBitmapKey = key;
		this.mBitmap = ImageManager.getBitmap(this.mBitmapKey);
		assert this.mBitmap != null;
		this.dirty = true;
		this.mWidth = mBitmap.getWidth();
		this.mHeight = mBitmap.getHeight();
	}

	public Sprite setAnchor(float x, float y) {
		this.mAnchorX = x;
		this.mAnchorY = y;
		recalculateBoundingBox();
		return this;
	}
	
	public Sprite setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		recalculateBoundingBox();
		
		return this;
	}
	
	public void setColorFilter(ColorFilter filter) {
		this.mPaint.setColorFilter(filter);
	}
	
	public void render(Canvas canvas) {
//		if (isDirty()) {
			canvas.save();
			float x = this.x - (this.mWidth * this.mAnchorX * this.scale);
			float y = this.y - (this.mHeight * this.mAnchorY * this.scale);
			canvas.translate(x, y);
			canvas.rotate(this.angle, this.mWidth * this.mAnchorX * this.scale, this.mHeight * this.mAnchorY * this.scale);
			canvas.scale(this.scale, this.scale);
			this.mPaint.setAlpha((int)(255.0f * this.alpha));
			canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
			canvas.restore();
			this.dirty = false;
//		}
	}
	
	public void onTouch(MotionEvent event) {
	}
}
