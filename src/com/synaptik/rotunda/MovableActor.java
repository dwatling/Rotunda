package com.synaptik.rotunda;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.synaptik.rotunda.anim.AnimationSequence;
import com.synaptik.rotunda.values.FloatValue;

public abstract class MovableActor extends Actor implements FloatValue.AnimationListener {
	boolean dirty;
	
	public FloatValue x;
	public FloatValue y;
	public FloatValue alpha;
	public FloatValue angle;
	public FloatValue scale;
	
	public Rect mBoundingBox;
	public float mWidth;
	public float mHeight;
	
	protected float mAnchorX;
	protected float mAnchorY;
	
	protected Paint mPaint;
	
	AnimationListener mListener;
	AnimationSequence animSeq;
	
	double totalElapsed;
	
	/** TODO - I don't like that these are kept in here like this. Maybe have a new class called "Attributes" ? **/
	private float savedX;
	private float savedY;
	private float savedAlpha;
	private float savedAngle;
	private float savedScale;
	
	public MovableActor() {
		super();
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.x = new FloatValue(0.0f);
		this.y = new FloatValue(0.0f);
		this.alpha = new FloatValue(1.0f);
		this.angle = new FloatValue(0.0f);
		this.scale = new FloatValue(1.0f);
		this.x.setAnimationListener(this);
		this.y.setAnimationListener(this);
		this.alpha.setAnimationListener(this);
		this.angle.setAnimationListener(this);
		this.mBoundingBox = new Rect();
		
		this.mAnchorX = 0.5f;
		this.mAnchorY = 0.5f;
	}
	
	protected boolean isDirty() {
		return this.dirty;
	}
	
	protected void dirty() {
		this.dirty = true;
	}
	
	@Override
	public boolean update(double elapsed) {
		if (!paused) {
			if (this.animSeq != null) {
				if (!this.animSeq.update(totalElapsed, elapsed, this)) {
					// Did not animate the actor, it means the animation is done. Empty animSeq.
					if (this.mListener != null) {
						this.mListener.onAnimationEnd(this);
					}
					Log.d(getName(), "Animation is done!");
					resetAttributes();
					this.animSeq = null;
				}
				this.totalElapsed += elapsed;
				recalculateBoundingBox();
			}
		}
		return isDirty();
	}
	
	public void startAnimation(AnimationSequence seq) {
		startAnimation(seq, null);
	}
	
	public void startAnimation(AnimationSequence seq, AnimationListener listener) {
		if (this.animSeq != null) {
			resetAttributes();
		}
		this.mListener = listener;
		this.totalElapsed = 0.0f;
		this.animSeq = seq;
		saveAttributes();
	}
	
	/**
	 * This should stop the animation dead in its tracks and reset all of the attributes
	 */
	public void stopAnimation() {
		if (this.animSeq != null) {
			this.resetAttributes();
			this.animSeq = null;
		} else {
			Log.d(getName(), "Stopping animation when no animation is present.");
		}
	}
	
	private void saveAttributes() {
		this.savedX = this.x.data;
		this.savedY = this.y.data;
		this.savedAngle = this.angle.data;
		this.savedScale = this.scale.data;
		this.savedAlpha = this.alpha.data;
	}
	
	private void resetAttributes() {
		this.x.data = this.savedX;
		this.y.data = this.savedY;
		this.angle.data = this.savedAngle;
		this.scale.data = this.savedScale;
		this.alpha.data = this.savedAlpha;
	}
	
	public boolean isAnimating() {
		return this.animSeq != null;
	}
	
	protected void recalculateBoundingBox() {
		this.mBoundingBox.left = (int)(this.x.data - (this.mWidth * this.mAnchorX * this.scale.data));
		this.mBoundingBox.top = (int)(this.y.data - (this.mHeight * this.mAnchorY * this.scale.data));
		this.mBoundingBox.right = (int)(this.mBoundingBox.left + this.mWidth * this.scale.data);
		this.mBoundingBox.bottom = (int)(this.mBoundingBox.top + this.mHeight * this.scale.data);
	}
	
//	public void fadeTo(float alpha, float time) {
//		if (this.alpha.data != alpha) {
//			this.alpha.animateTo(alpha, time);
//		}
//	}
//	
//	public void rotateTo(float angle, float time) {
//		if (this.angle.data != angle) {
//			this.angle.animateTo(angle, time);
//		}
//	}
//	
//	public void moveTo(float x, float y, float time) {
//		if (this.x.data != x) {
//			this.x.animateTo(x, time);
//		}
//		if (this.y.data != y) {
//			this.y.animateTo(y, time);
//		}
//	}
//	
//	public void scaleTo(float scale, float time) {
//		if (this.scale.data != scale) {
//			this.scale.animateTo(scale, time);
//		}
//	}
	
	@Override
	public void onAnimationEnd() {
		if (!this.x.isAnimating() && !this.y.isAnimating() && !this.alpha.isAnimating() && !this.angle.isAnimating() && !this.scale.isAnimating()) {
			if (this.mListener != null) {
				this.mListener.onAnimationEnd(this);
			}
		}
	}
	
	public void setAnimationListener(AnimationListener listener) {
		this.mListener = listener;
	}
	
	/**
	 * TODO - Consider angle
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(float x, float y) {
		return this.mBoundingBox.contains((int)x, (int)y);
	}
}
