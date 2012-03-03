package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class RotationAnimation extends BaseValueAnimation {
	
	public RotationAnimation(float target, float targetElapsed) {
		super(targetElapsed, target);
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double totalElapsed, double elapsed, MovableActor actor) {
		float startValue = determineStartValue(actor.angle, this.mTargetValue, (float)totalElapsed, this.mTargetElapsed);
		actor.angle = determineNewValue(actor.angle, startValue, totalElapsed + elapsed);
		return this.mTargetElapsed - totalElapsed;
	}
}
