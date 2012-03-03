package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

/**
 * No-op animation. Does nothing. Useful for delaying animations.
 * 
 * @author dan
 */
public class NoopAnimation extends Animation {
	public NoopAnimation(float targetElapsed) {
		super(targetElapsed);
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double totalElapsed, double elapsed, MovableActor actor) {
		return this.mTargetElapsed - totalElapsed;
	}
}
