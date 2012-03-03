package com.synaptik.rotunda;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.synaptik.rotunda.anim.AnimationSequence;

/**
 * @author dan
 */
public abstract class MovableActor extends Actor {
	boolean dirty;
	
	public float x;
	public float y;
	public float alpha;
	public float angle;
	public float scale;
	
	public Rect mBoundingBox;
	public float mWidth;
	public float mHeight;
	
	protected float mAnchorX;
	protected float mAnchorY;
	
	protected Paint mPaint;
	
	AnimationListener mListener;
	Map<String,AnimationInfo> mAnims;
	
	public MovableActor() {
		super();
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);		/** TODO - Does this slow anything down? **/
		this.x = 0.0f;
		this.y = 0.0f;
		this.alpha = 1.0f;
		this.angle = 0.0f;
		this.scale = 1.0f;
		this.mBoundingBox = new Rect();
		
		this.mAnchorX = 0.5f;
		this.mAnchorY = 0.5f;
		this.mAnims = new HashMap<String, AnimationInfo>();
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
			if (this.mAnims.size() > 0) {
				updateAnimations(elapsed);
				recalculateBoundingBox();
			}
		}
		return isDirty();
	}
	
	private void updateAnimations(double elapsed) {
		Iterator<AnimationInfo> iter = this.mAnims.values().iterator();
		while (iter.hasNext()) {
			AnimationInfo anim = iter.next();
			if (anim.mRemove) {
				Log.d(getName(), "Removing animation '" + anim.mSeq.mKey + "'");
				iter.remove();
			} else {
				if (!anim.mSeq.update(anim.mTotalElapsed, elapsed, this)) {
					if (anim.mListener != null) {
						anim.mListener.onAnimationEnd(this);
					}
					
					stopAnimation(anim.mSeq.mKey);
				}
				anim.mTotalElapsed += elapsed;
			}
		}
	}
	
	public void startAnimation(AnimationSequence seq) {
		startAnimation(seq, null);
	}
	
	public float getAnimationTimeLeft() {
		double result = 0.0f;
		for (AnimationInfo anim : this.mAnims.values()) {
			double currentTime = anim.mSeq.getAnimationTime() - anim.mTotalElapsed;
			if (currentTime > result) {
				result = currentTime;
			}
		}
		return (float)result;
	}
	
	public void startAnimation( AnimationSequence seq, AnimationListener listener) {
		Log.d(getName(), "Starting animation (total time: " + (seq.isInfinite() ? "infinite" : seq.getAnimationTime()) + ")");
		this.mAnims.put(seq.mKey, new AnimationInfo(seq, listener));
	}
	
	public void stopAnimation(String key) {
		if (this.mAnims.containsKey(key)) {
			this.mAnims.get(key).mRemove = true;
		} else {
			Log.d(getName(), "Animation '" + key + "' is not playing.");
		}
	}
	
	public boolean isAnimating() {
		return this.mAnims != null;
	}
	
	protected void recalculateBoundingBox() {
		this.mBoundingBox.left = (int)(this.x - (this.mWidth * this.mAnchorX * this.scale));
		this.mBoundingBox.top = (int)(this.y - (this.mHeight * this.mAnchorY * this.scale));
		this.mBoundingBox.right = (int)(this.mBoundingBox.left + this.mWidth * this.scale);
		this.mBoundingBox.bottom = (int)(this.mBoundingBox.top + this.mHeight * this.scale);
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
