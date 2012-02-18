package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class AlphaAnimation extends Animation {
	float target;
	
	public AlphaAnimation(float target, float totalElapsed) {
		super(totalElapsed);
		this.target = target;
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double elapsed, MovableActor actor) {
		if (!actor.alpha.isAnimating()) {
			actor.alpha.animateTo(this.target, this.totalTime);
		}
		double result = actor.alpha.targetElapsed - elapsed;
		actor.alpha.update(elapsed);
		return result;
	}
}
