package com.synaptik.rotunda;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.synaptik.rotunda.managers.FontManager;
import com.synaptik.rotunda.values.FloatValue;

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
public class Label extends Actor {
	boolean dirty;
	
	String mText;
	
	FloatValue x;
	FloatValue y;
	
	Paint mPaint;
	
	public Label(String font, String text) {
		this.mPaint = new Paint();
		this.mPaint.setTypeface(FontManager.getFont(font));
		this.mPaint.setAntiAlias(true);
		this.mPaint.setColor(0xFFFFFFFF);
		this.mPaint.setTextSize(12.0f);
		x = new FloatValue(0.0f);
		y = new FloatValue(12.0f);
		this.dirty = true;
		this.mText = text;
	}
	
	public void setTextSize(float size) {
		this.mPaint.setTextSize(size);
		
	}
	
	protected boolean isDirty() {
		return this.dirty;
	}
	
	protected void dirty() {
		this.dirty = true;
	}
	
	public void setColor(int color) {
		mPaint.setColor(color);
	}
	
	@Override
	public boolean update(double elapsed) {
		return true;
	}
	
	public void setText(String text) {
		this.mText = text;
	}
	
	public void render(Canvas canvas) {
		canvas.save();
		canvas.translate(x.data, y.data);
		canvas.drawText(mText, this.x.data, this.y.data, mPaint);
		canvas.restore();
		this.dirty = false;
	}
}
