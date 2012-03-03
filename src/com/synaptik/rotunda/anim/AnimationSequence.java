package com.synaptik.rotunda.anim;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.synaptik.rotunda.MovableActor;

public class AnimationSequence {
	List<Animation> animations;
	int mTimesToRepeat = 1;
	float mTotalAnimationTime = 0.0f;
	Animation currentAnimation;
	public String mKey;
	
	public AnimationSequence(String key) {
		this.animations = new ArrayList<Animation>();
		this.mKey = key;
		mTimesToRepeat = 1;
	}
	
	/**
	 * @return True if it animated, false if it did not
	 */
	public boolean update(double totalElapsed, double elapsed, MovableActor actor) {
		double normalizedElapsed = totalElapsed % this.mTotalAnimationTime;
//		Log.d(mKey, "" + totalElapsed + " / " + normalizedElapsed + ", " + elapsed + ", " + actor.getName());
		currentAnimation = getCurrentAnimation(normalizedElapsed, totalElapsed);
		if (currentAnimation != null) {
			double currentAnimationTotalElapsed = normalizedElapsed - currentAnimation.mTimeOffset;
			double timeLeft = currentAnimation.update(currentAnimationTotalElapsed, elapsed, actor);
//			Log.d(actor.getName() + " - " + currentAnimation.getClass().getSimpleName(), "Animation timeLeft: " + timeLeft);
			if (timeLeft <= 0.0f) {
				// This means there is still timeLeft in the animation sequence that needs to be allocated to the next animation
				totalElapsed += elapsed + timeLeft;
				return update(totalElapsed, -timeLeft, actor);
			}
		}
		return currentAnimation != null;
	}
	
	protected Animation getCurrentAnimation(double normalizedElapsed, double totalElapsed) {
		Animation result = null;
		int timesRepeated = (int)(totalElapsed / this.mTotalAnimationTime);
		if (this.mTimesToRepeat < 0 || timesRepeated < this.mTimesToRepeat) {
			float currentFrameEnd = 0.0f;
			for (Animation a : animations) {
				currentFrameEnd += a.getTargetElapsed();
				if (normalizedElapsed < currentFrameEnd) {
					result = a;
					break;
				}
			}
		}
		return result;
	}
	
	public AnimationSequence add(Animation a) {
		a.setTimeOffset(this.mTotalAnimationTime);
		this.animations.add(a);
		this.mTotalAnimationTime += a.getTargetElapsed();
		return this;
	}
	
	public AnimationSequence repeat(int times) {
		this.mTimesToRepeat = times;
		return this;
	}
	
	public AnimationSequence infiniteRepeat() {
		this.mTimesToRepeat = -1;
		return this;
	}
	
	public boolean isInfinite() {
		return this.mTimesToRepeat == -1;
	}
	
	public float getAnimationTime() {
		return this.mTotalAnimationTime;
	}
}
