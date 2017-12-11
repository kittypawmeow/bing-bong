package com.kittypawmeow.bingbong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
	private final int TIME_DELAY_MS = 3000;

	Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			Intent intent = new Intent(SplashActivity.this, GameActivity.class);
			startActivity(intent);
			finish();
			//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		new Handler().postDelayed(mRunnable, TIME_DELAY_MS);
	}
}
