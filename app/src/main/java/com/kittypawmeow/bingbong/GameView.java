package com.kittypawmeow.bingbong;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class GameView extends SurfaceView implements Runnable {
	Thread gameThread = null;
	SurfaceHolder myHolder;
	Canvas canvas;
	Paint paint;

	Bitmap characterBmp;
	private Context app_context;

	public GameView(Context context) {
		super(context);
		init(context);
	}

	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	void init(Context context) {
		app_context = context;
		myHolder = getHolder();
		paint = new Paint();
		characterBmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.mouth_closed);
	}

	@Override
	public void run() {
		draw();
	}

	public void draw() {
		if(myHolder.getSurface().isValid()) {
			canvas = myHolder.lockCanvas();
			canvas.drawColor(Color.rgb(157, 207, 206));
			paint.setColor(Color.rgb(26, 128, 182));
			DisplayMetrics displayMetrics = new DisplayMetrics();
			((Activity) getContext()).getWindowManager()
					.getDefaultDisplay()
					.getMetrics(displayMetrics);
			int screen_height = displayMetrics.heightPixels;
			int screen_width = displayMetrics.widthPixels;
			canvas.drawBitmap(characterBmp,
					//screen_height / 2 - characterBmp.getHeight() / 2,
					0,
					screen_width / 2 - characterBmp.getWidth() / 2, paint);
			myHolder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			Log.e("Error:", "joining thread");
		}
	}

	public void resume() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	/*
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		MediaPlayer mp = MediaPlayer.create(app_context, R.raw.bingbong);
		switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				// play "bing"
				// open mouth
				mp.start();
				break;
			case MotionEvent.ACTION_UP:
				// play "bong"
				// close mouth
				break;
		}

		return true;
	}
	*/
}
