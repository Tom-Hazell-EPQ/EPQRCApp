package com.nutty.EPQ.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/*
 * This is the first class that is run when the app is. it shows a Simple graphic for 3s
 * @author Tom Hazell
 * (C) 2014 Tom Hazell
 */
public class SplashScreen extends Activity {

	private static int SPLASHSCREEN_TIMEOUT = 3000;// set timeout in ms

	// entery point
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar + title bar + nav bar and set xml view
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().getDecorView()
				.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		setContentView(R.layout.activity_splash);

		// set up the new thred to run the timer on
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// this makes a new intent to change the class to Main activity
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASHSCREEN_TIMEOUT);

	}

}
