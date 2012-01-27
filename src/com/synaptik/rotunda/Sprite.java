package com.synaptik.rotunda;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.synaptik.rotunda.managers.ImageManager;
import com.synaptik.rotunda.values.FloatValue;

public class Sprite extends Actor implements FloatValue.AnimationListener {
	boolean dirty;
	
	public FloatValue x;
	public FloatValue y;
	public int width;
	public int height;
	
	protected Paint mPaint;
	protected Bitmap bitmap;
	protected String bitmapKey;
	
	AnimationListener mListener;
	
	public Sprite(String bitmapKey) {
		super();
		this.mPaint = new Paint();
		this.x = new FloatValue(0.0f);
		this.y = new FloatValue(0.0f);
		this.x.setAnimationListener(this);
		this.y.setAnimationListener(this);
		this.bitmap = ImageManager.getBitmap(bitmapKey);
		this.bitmapKey = bitmapKey;
		assert bitmap != null;
		if (bitmap != null) {
			this.dirty = true;
			this.width = bitmap.getWidth();
			this.height = bitmap.getHeight();
		}
	}
	
	protected boolean isDirty() {
		return this.dirty;
	}
	
	protected void dirty() {
		this.dirty = true;
	}
	
	public String getBitmapKey() {
		return this.bitmapKey;
	}
	
	@Override
	public boolean update(double elapsed) {
		this.dirty = this.x.update(elapsed) | this.y.update(elapsed);
		return isDirty();
	}
	
	public void render(Canvas canvas) {
//		if (isDirty()) {
			canvas.save();
			canvas.translate(x.data, y.data);
			canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
			canvas.restore();
			this.dirty = false;
//		}
	}
	
	@Override
	public void onAnimationEnd() {
		if (!this.x.isAnimating() && !this.y.isAnimating()) {
			this.mListener.onAnimationEnd(this);
		}
	}
	
	public void setAnimationListener(AnimationListener listener) {
		this.mListener = listener;
	}
	
	public boolean contains(float x, float y) {
		return x >= this.x.data && y >= this.y.data && x <= (this.x.data+width) && y <= (this.y.data+height);
	}
	
	public void onTouch(MotionEvent event) {
	}
}
