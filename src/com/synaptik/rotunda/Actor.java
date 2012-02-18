package com.synaptik.rotunda;

import android.graphics.Canvas;

public abstract class Actor {
	protected String name;
	protected boolean flaggedForRemoval;
	protected boolean paused;

	public abstract boolean update(double elapsed);
	public abstract void render(Canvas canvas);
	
	public Actor() {
		this.flaggedForRemoval = false;
		this.paused = false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void remove() {
		this.flaggedForRemoval = true;
	}
	
	public void pause() {
		this.paused = true;
	}
	public void unpause() {
		this.paused = false;
	}
}
