package com.synaptik.rotunda.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.synaptik.rotunda.managers.SceneManager;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = GameSurface.class.getSimpleName();
	
	SurfaceListener mListener;
			
	public GameSurface(Context context) {
		super(context);
		init();
	}
	public GameSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public GameSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	protected void init() {
		if (!isInEditMode()) {
			this.getHolder().addCallback(this);
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(TAG, "surfaceChanged(" + format + "," + width + "," + height + ")");
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated");
		SceneManager.init(getContext().getApplicationContext(), holder);
		if (this.mListener != null) {
			this.mListener.onSurfaceShown();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed");
		SceneManager.close();
	}
	
	public void setSurfaceListener(SurfaceListener listener) {
		this.mListener = listener;
	}
	
//	private class CheckerboardDrawable extends Drawable {
//		private Paint mPaint;
//		
//		public CheckerboardDrawable() {
//			final int SIZE = 16;
//			final int HALF_SIZE = SIZE / 2;
//			Bitmap checkerBoard = Bitmap.createBitmap(SIZE, SIZE, Config.ARGB_8888);
//			for (int y = 0; y < checkerBoard.getHeight(); y ++) {
//				for (int x = 0; x < checkerBoard.getWidth(); x ++) {
//					int color = 0;
//					if ((x < HALF_SIZE && y > HALF_SIZE) ||
//						(x > HALF_SIZE && y < HALF_SIZE)) {
//						color = 0xFF808080;
//					}
//					checkerBoard.setPixel(x, y, color);
//				}
//			}
//			BitmapShader shader = new BitmapShader(checkerBoard, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//			mPaint = new Paint();
//			mPaint.setShader(shader);
//		}
//		@Override
//		public void draw(Canvas canvas) {
//			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);			
//		}
//
//		@Override
//		public int getOpacity() {
//			return 0;
//		}
//
//		@Override
//		public void setAlpha(int alpha) {
//		}
//
//		@Override
//		public void setColorFilter(ColorFilter cf) {
//		}
//	}
}
