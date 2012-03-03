package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class AlphaAnimation extends BaseValueAnimation {
	public AlphaAnimation(float target, float targetElapsed) {
		super(targetElapsed, target);
	}
	
	@Override
	public double update(double totalElapsed, double elapsed, MovableActor actor) {
		float startValue = determineStartValue(actor.alpha, this.mTargetValue, (float)totalElapsed, this.mTargetElapsed);
		actor.alpha = determineNewValue(actor.alpha, startValue, totalElapsed + elapsed);
		return this.mTargetElapsed - totalElapsed;
	}
}
