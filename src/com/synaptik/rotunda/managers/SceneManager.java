package com.synaptik.rotunda.managers;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import com.synaptik.rotunda.Scene;

/**
 * TODO - Move rendering code into SurfaceView classes
 * 
 * @author dan
 */
public class SceneManager {
	static Thread mRenderThread;
	static UpdateAndRender mRenderRunnable;
	static Scene sCurrentScene;
	
	public static void init(Context ctx, SurfaceHolder holder) {
		mRenderRunnable = new UpdateAndRender(holder);
		mRenderThread = new Thread(mRenderRunnable, "Rotunda-Renderer");
		mRenderThread.setPriority(Thread.MAX_PRIORITY);
		mRenderThread.start();
	}
	
	public static void close() {
		mRenderRunnable.running = false;
		try {
			mRenderThread.join();
		} catch (InterruptedException e) {
			// It's fine
		}
	}

	public static void setCurrentScene(Scene s) {
		sCurrentScene = s;
	}
	public static Scene currentScene() {
		return sCurrentScene;
	}
	
	static class UpdateAndRender implements Runnable {
		boolean running;
		SurfaceHolder mSurface;
		
		public UpdateAndRender(SurfaceHolder holder) {
			this.running = true;
			this.mSurface = holder;
		}
		
		public UpdateAndRender() {
			this.running = true;
		}
		
		@Override
		public void run() {
			long startFrame = SystemClock.elapsedRealtime();
			long now = startFrame;
			float elapsed = (now - startFrame) / 1000.0f;
			while (this.running) {
				if (sCurrentScene != null) {
					// UPDATE the scene
					now = SystemClock.elapsedRealtime();
					elapsed = (now - startFrame) / 1000.0f;
					sCurrentScene.update(elapsed);
					startFrame = now;
					
					// RENDER the scene
					if (!sCurrentScene.isPaused()) {
						Canvas c = this.mSurface.lockCanvas();
						sCurrentScene.render(c);
						this.mSurface.unlockCanvasAndPost(c);
					} else {
						SystemClock.sleep(100);
					}
				} else {
					SystemClock.sleep(100);
				}
			}
		}
	}
}
