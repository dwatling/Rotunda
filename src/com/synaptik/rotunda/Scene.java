package com.synaptik.rotunda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.util.Log;

/**
 * TODO - Need to support multiple resolutions. Stretch, center, or clip.
 * 
 * @author dan
 */
public class Scene {
	private static final String TAG = Scene.class.getSimpleName();
	protected Map<String, Event> mEvents = null;
	protected List<Actor> mActors = null;
	
	private int index;
	private int findIndex;		// Separate thread from render / update
	private int mWidth;
	private int mHeight;
	private boolean mPaused;
	
	public Scene(int width, int height) {
		mEvents = new HashMap<String, Event>(32);
		mActors = new ArrayList<Actor>(32);
		
		this.mWidth = width;
		this.mHeight = height;
		this.mPaused = false;
	}
	
	public int getActorCount() {
		return mActors.size();
	}
	public int getWidth() {
		return mWidth;
	}
	public int getHeight() {
		return mHeight;
	}
	
	public void addActor(String name, Actor actor) {
		if (mActors.contains(name)) {
			Log.e("Scene", "Scene already contains actor '" + name + "'! Not added.");
		} else {
			actor.name = name;
			mActors.add(actor);
		}
	}
	
	public void addEvent(String name, Event event) {
		if (mEvents.containsKey(name)) {
			Log.e("Scene", "Scene already contains event '" + name + "'! Not added.");
		} else {
			mEvents.put(name,  event);
		}
	}
	
	public void triggerEvent(String name) {
		triggerEvent(name, null);
	}
	public void triggerEvent(String name, Object data) {
		Event evt = mEvents.get(name);
		if (evt != null) {
			evt.onEvent(data);
		}
	}
	
	protected void purgeStaleActors() {
		for (index = 0; index < mActors.size(); index ++) {
			if (mActors.get(index).flaggedForRemoval) {
				mActors.remove(index);
				index --;
			}
		}
	}
	public void update(double elapsed) {
		purgeStaleActors();
		
		for (index = 0; index < mActors.size(); index ++) {
			mActors.get(index).update(elapsed);
		}
	}

	public void render(Canvas canvas) {
		for (index = 0; index < mActors.size(); index ++) {
			mActors.get(index).render(canvas);
		}
	}
	
	public boolean isPaused() {
		return this.mPaused;
	}
	
	public void setPaused(boolean b) {
		this.mPaused = b;
	}
	
	public MovableActor findActor(float x, float y) {
		MovableActor result = null;
		for (findIndex = mActors.size()-1; findIndex >= 0; findIndex --) {
			Actor a = mActors.get(findIndex);
			if (a instanceof MovableActor) {
				if (((MovableActor)a).contains(x,y)) {
					result = (MovableActor)a;
					break;
				}
			}
		}
		return result;
	}
}
