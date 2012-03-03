package com.synaptik.rotunda.anim;



/**
 * The name isn't very clear here, but the intention of this class is to provide a common method to
 * determine new values for actor attributes such as position, alpha, angles, etc. Currently, only
 * linear interpolation is supported.
 * 
 * TODO - Optimize, optimize, optimize
 *   
 * @author dan
 */
public abstract class BaseValueAnimation extends Animation {
	float mTargetValue;

	public BaseValueAnimation(float targetElapsed, float targetValue) {
		super(targetElapsed);
		this.mTargetValue = targetValue;
	}
	
	protected float determineStartValue(float current, float target, float elapsed, float targetElapsed) {
//		Log.d("DSV", current + ", " + target + ", " + elapsed + ", " + targetElapsed + " (" + (elapsed / targetElapsed) + ")");
		return (current - target * (elapsed / targetElapsed)) / (1 - (elapsed / targetElapsed));
	}
	
	protected float determineNewValue(float currentValue, float startValue, double totalElapsed) {
		float result = 0.0f;
		double pct = (totalElapsed / this.mTargetElapsed);
		
		if (pct >= 1.0f) {
			result = this.mTargetValue;
		} else {
			result = (float)(((this.mTargetValue - startValue) * pct) + startValue);
		}
//		Log.d("Value Anim", startValue + " / " + result + " / " + mTargetValue + " (" + pct + ")");
		
		return result;
	}
}
