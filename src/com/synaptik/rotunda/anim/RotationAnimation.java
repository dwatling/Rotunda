package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class RotationAnimation extends Animation {
	float target;
	
	public RotationAnimation(float target, float totalElapsed) {
		super(totalElapsed);
		this.target = target;
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double elapsed, MovableActor actor) {
		if (!actor.angle.isAnimating()) {
			actor.angle.animateTo(this.target, this.totalTime);
		}
		double result = actor.angle.targetElapsed - elapsed;
		actor.angle.update(elapsed);
		return result;
	}
}
