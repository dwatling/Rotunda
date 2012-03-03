package com.synaptik.rotunda;

import com.synaptik.rotunda.anim.AnimationSequence;

public class AnimationInfo {
	public AnimationSequence mSeq;
	public float mTotalElapsed;
	public AnimationListener mListener;
	public boolean mRemove;
	
	public AnimationInfo(AnimationSequence mSeq, AnimationListener mListener) {
		this.mSeq = mSeq;
		this.mTotalElapsed = 0.0f;
		this.mListener = mListener;
		this.mRemove = false;
	}
}
