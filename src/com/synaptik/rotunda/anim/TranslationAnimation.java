package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public class TranslationAnimation extends Animation {
	boolean xAxis;
	float target;
	
	public TranslationAnimation(String type, float target,float totalElapsed) {
		super(totalElapsed);
		this.target = target;
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
	public double update(double elapsed, MovableActor actor) {
		double result = 0.0f;
		if (xAxis) {
			if (!actor.x.isAnimating()) {
				actor.x.animateTo(this.target, this.totalTime);
			}
			result = actor.x.targetElapsed - elapsed;
			actor.x.update(elapsed);
		} else {
			if (!actor.y.isAnimating()) {
				actor.y.animateTo(this.target, this.totalTime);
			}
			result = actor.y.targetElapsed - elapsed;
			actor.y.update(elapsed);
		}
		return result;
	}
}
