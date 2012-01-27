package com.synaptik.rotunda;

import android.graphics.Canvas;

public abstract class Actor {
	String name;
	boolean flaggedForRemoval;

	public abstract boolean update(double elapsed);
	public abstract void render(Canvas canvas);
	
	public Actor() {
		flaggedForRemoval = false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void remove() {
		this.flaggedForRemoval = true;
	}
}
