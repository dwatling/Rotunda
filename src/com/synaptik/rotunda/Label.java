package com.synaptik.rotunda;

import android.graphics.Canvas;

import com.synaptik.rotunda.managers.FontManager;

/**
 * Text label. Use Font name loaded in FontManager (file name). If you
 * need to update the text every frame, implement your own subclass and override the 'update'
 * method.
 * 
 * Keep in mind that Labels are rendered to the TOP RIGHT of its X/Y coords. So if Y is 0 you
 * will not see anything.
 * @author Edward
 *
 */
public class Label extends MovableActor {
	protected String mText;
	protected float mPivotX;
	protected float mPivotY;
	
	public Label(String font, String text) {
		super();
		this.mPaint.setTypeface(FontManager.getFont(font));
		this.mPaint.setAntiAlias(true);
		this.mPaint.setColor(0xFFFFFFFF);
		this.mPaint.setTextSize(12.0f);
		setText(text);
	}
	
	protected void updatePivotPoint() {
		this.mPaint.getTextBounds(this.mText, 0, this.mText.length(), this.mBoundingBox);
		this.mWidth = this.mBoundingBox.width();
		this.mHeight = this.mBoundingBox.height();
		this.mPivotX = this.mWidth * this.mAnchorX;
		this.mPivotY = -this.mHeight * this.mAnchorY;
	}
	
	public Label setTextSize(float size) {
		this.mPaint.setTextSize(size);
		updatePivotPoint();
		return this;
	}
	
	public Label setColor(int color) {
		this.mPaint.setColor(color);
		return this;
	}
	
	public Label setAnchor(float x, float y) {
		this.mAnchorX = x;
		this.mAnchorY = y;
		updatePivotPoint();
		return this;
	}
	
	/**
	 * In degrees
	 * @param angle
	 * @return
	 */
	public Label setAngle(float angle) {
		this.angle.data = angle;
		return this;
	}
	
	public Label setPosition(float x, float y) {
		this.x.data = x;
		this.y.data = y;
		return this;
	}
	
	public Label setText(String text) {
		this.mText = text;
		updatePivotPoint();
		return this;
	}
	
	public void render(Canvas canvas) {
		canvas.save();
		canvas.translate(this.x.data, this.y.data);
		canvas.translate(-this.mBoundingBox.width() * this.mAnchorX, -this.mBoundingBox.height() * this.mAnchorY);
		canvas.rotate(this.angle.data, this.mPivotX, this.mPivotY + this.mBoundingBox.height());
		canvas.drawText(this.mText, 0.0f, this.mBoundingBox.height(), this.mPaint);
		canvas.restore();
		this.dirty = false;
	}
}
