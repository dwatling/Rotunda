package com.synaptik.rotunda.values;


public class FloatValue {
	public float data;
	float startData;
	float targetData;
	double targetElapsed;
	double totalElapsed;
	boolean animating;
	AnimationListener mListener;
	
	public interface AnimationListener {
		public void onAnimationEnd();
	}
	
	public FloatValue(float newValue) {
		setValue(newValue);
	}
	
	public void add(float value) {
		this.data += value;
	}
	public void sub(float value) {
		this.data -= value;
	}
	
	public void setValue(float newValue) {
		data = newValue;
		startData = 0;
		totalElapsed = 0;
		targetData = 0;
		targetElapsed = 0;
		animating = false;
	}
	
	public boolean update(double elapsed) {
		boolean result = false;
		if (this.targetElapsed > 0) {
			this.totalElapsed += elapsed;
			double pct = (this.totalElapsed / this.targetElapsed);
			
			if (pct >= 1.0f) {
				this.targetElapsed = 0.0f;
				this.data = this.targetData;
				this.targetData = 0.0f;
				this.animating = false;
				if (this.mListener != null) {
					this.mListener.onAnimationEnd();
				}
			} else {
				data = (float)(((this.targetData - this.startData) * pct) + this.startData);
			}
			result = true;
		}
		return result;
	}
	
	public void animateTo(float target, float targetTime) {
		this.totalElapsed = 0.0f;
		this.startData = this.data;
		this.targetData = target;
		this.targetElapsed = targetTime;
		this.animating = true;
	}
	public boolean isAnimating() {
		return this.animating;
	}
	public void setAnimationListener(AnimationListener listener) {
		this.mListener = listener;
	}
}
