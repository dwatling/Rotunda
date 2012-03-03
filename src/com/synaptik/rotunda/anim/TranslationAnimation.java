package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class TranslationAnimation extends BaseValueAnimation {
	boolean xAxis;
	
	public TranslationAnimation(String type, float target,float targetElapsed) {
		super(targetElapsed, target);
		if ("x".equalsIgnoreCase(type)) {
			xAxis = true;
		} else {
			xAxis = false;
		}
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double totalElapsed, double elapsed, MovableActor actor) {
		if (xAxis) {
			float startValue = determineStartValue(actor.x, this.mTargetValue, (float)totalElapsed, this.mTargetElapsed);
			actor.x = determineNewValue(actor.x, startValue, totalElapsed + elapsed);
		} else {
			float startValue = determineStartValue(actor.y, this.mTargetValue, (float)totalElapsed, this.mTargetElapsed);
			actor.y = determineNewValue(actor.y, startValue, totalElapsed + elapsed);
		}
		return this.mTargetElapsed - totalElapsed;
	}
}
