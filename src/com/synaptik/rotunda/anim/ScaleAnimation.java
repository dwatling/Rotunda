package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class ScaleAnimation extends Animation {
	float target;
	
	public ScaleAnimation(float target, float totalElapsed) {
		super(totalElapsed);
		this.target = target;
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double elapsed, MovableActor actor) {
		if (!actor.scale.isAnimating()) {
			actor.scale.animateTo(this.target, this.totalTime);
		}
		double result = actor.scale.targetElapsed - elapsed;
		actor.scale.update(elapsed);
		return result;
	}
}
