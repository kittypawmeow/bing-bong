package com.kittypawmeow.bingbong;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Animated view of character which reacts to button presses
 */
public class GameView extends SurfaceView implements Runnable {
	SurfaceHolder myHolder;
	Canvas canvas;
	Paint paint;

	Bitmap characterBmp;

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
}
