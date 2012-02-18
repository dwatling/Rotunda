package com.synaptik.rotunda;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.synaptik.rotunda.managers.ImageManager;

public class Sprite extends MovableActor {
	protected Bitmap bitmap;
	protected String bitmapKey;
	
	AnimationListener mListener;
	
	public Sprite(String bitmapKey) {
		super();
		this.bitmap = ImageManager.getBitmap(bitmapKey);
		this.bitmapKey = bitmapKey;
		assert bitmap != null;
		if (bitmap != null) {
			this.dirty = true;
			this.mWidth = bitmap.getWidth();
			this.mHeight = bitmap.getHeight();
		}
	}
	
	public String getBitmapKey() {
		return this.bitmapKey;
	}

	public Sprite setAnchor(float x, float y) {
		this.mAnchorX = x;
		this.mAnchorY = y;
		recalculateBoundingBox();
		return this;
	}
	
	public Sprite setPosition(float x, float y) {
		this.x.data = x;
		this.y.data = y;
		recalculateBoundingBox();
		
		return this;
	}
	
	public void render(Canvas canvas) {
//		if (isDirty()) {
			canvas.save();
			float x = this.x.data - (this.mWidth * this.mAnchorX * this.scale.data);
			float y = this.y.data - (this.mHeight * this.mAnchorY * this.scale.data);
			canvas.translate(x, y);
			canvas.rotate(this.angle.data, this.mWidth * this.mAnchorX * this.scale.data, this.mHeight * this.mAnchorY * this.scale.data);
			canvas.scale(this.scale.data, this.scale.data);
			canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, this.mPaint);
			canvas.restore();
			this.dirty = false;
//		}
	}
	
	public void onTouch(MotionEvent event) {
	}
}
