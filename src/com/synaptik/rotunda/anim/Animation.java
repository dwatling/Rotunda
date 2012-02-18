package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;

public abstract class Animation {
	public float totalTime;
	
	public Animation(float totalElapsed) {
		this.totalTime = totalElapsed;
	}
	public abstract double update(double elapsed, MovableActor actor);
}
