package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public abstract class Animation {
	protected float mTargetElapsed;
	float mTimeOffset;
	
	public Animation(float targetElapsed) {
		this.mTargetElapsed = targetElapsed;
	}
	protected float getTargetElapsed() {
		return this.mTargetElapsed;
	}
	void setTimeOffset(float offset) {
		this.mTimeOffset = offset;
	}
	public abstract double update(double totalElapsed, double elapsed, MovableActor actor);
}
