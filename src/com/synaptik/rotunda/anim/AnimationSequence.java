package com.synaptik.rotunda.anim;

import java.util.ArrayList;
import java.util.List;

import com.synaptik.rotunda.MovableActor;

public class AnimationSequence {
	List<Animation> animations;
	int timesToRepeat = -1;
	float totalAnimationTime = 0.0f;
	Animation currentAnimation;
	
	public AnimationSequence() {
		this.animations = new ArrayList<Animation>();
		timesToRepeat = -1;
	}
	
	/**
	 * @return True if it animated, false if it did not
	 */
	public boolean update(double totalElapsed, double elapsed, MovableActor actor) {
		currentAnimation = getCurrentAnimation(totalElapsed);
		if (currentAnimation != null) {
			double timeLeft = currentAnimation.update(elapsed, actor);
			if (timeLeft <= 0.0f) {
				// This means there is still timeLeft in the animation sequence that needs to be allocated to the next animation
				totalElapsed -= timeLeft;
				currentAnimation = getCurrentAnimation(totalElapsed);
				update(totalElapsed, Math.abs(timeLeft), actor);
			}
		}
		return currentAnimation != null;
	}
	
	protected Animation getCurrentAnimation(double elapsed) {
		Animation result = null;
		double normalizedElapsed = elapsed % totalAnimationTime;
		int timesRepeated = (int)(elapsed / totalAnimationTime);
		if (timesToRepeat < 0 || timesRepeated < timesToRepeat) {
			float currentFrameEnd = 0.0f;
			for (Animation a : animations) {
				currentFrameEnd += a.totalTime;
				if (normalizedElapsed < currentFrameEnd) {
					result = a;
					break;
				}
			}
		}
		return result;
	}
	
	public AnimationSequence add(Animation a) {
		this.animations.add(a);
		this.totalAnimationTime += a.totalTime;
		return this;
	}
	
	public AnimationSequence repeat(int times) {
		this.timesToRepeat = times;
		return this;
	}
}
