package com.synaptik.rotunda;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.synaptik.rotunda.Actor;
import com.synaptik.rotunda.MovableActor;
import com.synaptik.rotunda.values.FloatValue;

/**
 * TODO - Integrate into Rotunda
 * @author dan
 */
public class DropArea extends Actor {
	
	FloatValue x;
	FloatValue y;
	float width;
	float height;
	Rect mBounds;
	Paint mPaint;
	OnDropListener mListener;

	public DropArea(float x, float y, float width, float height) {
		this.x = new FloatValue(x);
		this.y = new FloatValue(y);
		this.width = width;
		this.height = height;
		
		this.mBounds = new Rect((int)x,(int)y,(int)(x+width),(int)(y+height));
		
	}
	
	/** Use this to help you position the DropArea. Don't call this once it's properly positioned. **/
	public DropArea show(int color, float alpha) {
		this.mPaint = new Paint();
		mPaint.setColor(color);
		mPaint.setAlpha((int)(255.0f * alpha));
		return this;
	}
	
	public DropArea setDropListener(OnDropListener listener) {
		this.mListener = listener;
		return this;
	}
	
	public boolean contains(MovableActor a) {
		return Rect.intersects(this.mBounds, a.mBoundingBox);
	}
	
	public void drop(MovableActor a) {
		this.mListener.onDrop(this, a);
	}
	
	@Override
	public boolean update(double elapsed) {
		return false;
	}

	@Override
	public void render(Canvas canvas) {
		if (mPaint != null) {
			canvas.drawRect(mBounds, mPaint);
		}
	}

	public interface OnDropListener {
		public void onDrop(DropArea area, MovableActor a);
	}
}
