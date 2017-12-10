package com.kittypawmeow.bingbong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by cvalenza on 12/10/17.
 */

public class GameView extends SurfaceView implements Runnable {
	Thread gameThread = null;
	SurfaceHolder myHolder;
	volatile boolean playing;
	Canvas canvas;
	Paint paint;

	long fps;
	private long frameTime;
	Bitmap characterBmp;
	boolean isMoving = false;
	float walkSpeedPerSecond = 150;
	float characterXPosition = 10;

	public GameView(Context context) {
		super(context);
		myHolder = getHolder();
		paint = new Paint();
		characterBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.character);
	}

	@Override
	public void run() {
		while (playing) {
			long start = System.currentTimeMillis();
			update();
			draw();
			frameTime = System.currentTimeMillis() - start;

			if (frameTime > 0) {
				fps = 1000/ frameTime;
			}
		}
	}

	public void update() {
		if (isMoving) {
			characterXPosition += walkSpeedPerSecond / fps;
		}
	}

	public void draw() {
		if (myHolder.getSurface().isValid()) {
			canvas = myHolder.lockCanvas();
			canvas.drawColor(Color.rgb(157, 207, 206));
			paint.setColor(Color.rgb(26, 128, 182));
			paint.setTextSize(45);
			canvas.drawText("" + fps, 20, 40, paint);
			canvas.drawBitmap(characterBmp, characterXPosition, 200, paint);
			myHolder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		playing = false;

		try {
			gameThread.join();
		} catch (InterruptedException e) {
			Log.e("Error:", "joining thread");
		}
	}

	public void resume() {
		playing = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				isMoving = true;
				break;
			case MotionEvent.ACTION_UP:
				isMoving = false;
				break;
		}

		return true;
	}
}
