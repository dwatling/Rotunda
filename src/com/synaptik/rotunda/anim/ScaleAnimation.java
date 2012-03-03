package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class ScaleAnimation extends BaseValueAnimation {
	public ScaleAnimation(float target, float targetElapsed) {
		super(targetElapsed, target);
	}
	
	@Override
	public double update(double totalElapsed, double elapsed, MovableActor actor) {
		float startValue = determineStartValue(actor.scale, this.mTargetValue, (float)totalElapsed, this.mTargetElapsed);
		actor.scale = determineNewValue(actor.scale, startValue, totalElapsed + elapsed);
		return this.mTargetElapsed - totalElapsed;
	}
}
