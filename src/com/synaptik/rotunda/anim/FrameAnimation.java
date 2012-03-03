package com.synaptik.rotunda.anim;

import com.synaptik.rotunda.MovableActor;
import com.synaptik.rotunda.Sprite;

/**
 * This class ONLY works with Sprite-types
 * @author dan
 */
public class FrameAnimation extends Animation {
	String mKey;
	
	public FrameAnimation(String newBitmapKey, float targetElapsed) {
		super(targetElapsed);
		this.mKey = newBitmapKey;
	}
	
	/**
	 * @return Time left over
	 */
	@Override
	public double update(double totalElapsed, double elapsed, MovableActor actor) {
		if ((totalElapsed + elapsed) >= this.mTargetElapsed) {
			((Sprite)actor).setBitmapKey(mKey);
		}
		
		return this.mTargetElapsed - totalElapsed;
	}
}
