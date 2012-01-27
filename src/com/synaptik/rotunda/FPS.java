package com.synaptik.rotunda;


/**
 * Using this class WILL invoke the garbage collector over time.
 * 
 * @author dan
 *
 */
public class FPS extends Label {
	public FPS(String font, int color) {
		super(font, "0");
		setColor(color);
		setTextSize(36.0f);
	}
	
	@Override
	public boolean update(double elapsed) {
		setText(String.valueOf(1.0f/(float)elapsed));
		return super.update(elapsed);
	}
}
