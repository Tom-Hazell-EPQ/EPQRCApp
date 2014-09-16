package com.nutty.EPQ.app;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnTouchListener {

	// define global veriables so that they can be used from the aSyncTaskRunner
	// and other classes
	public static Button prefs, connect;
	public static TextView info;
	public static Button front;
	public static Button back;
	public static Button left;
	public static Button right;
	public static TextView Status, instructions;
	public static int gofront, goside, command;
	public static boolean running = false;

	// entery point
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title bar
		setContentView(R.layout.activity_main);// set the XML layout
		init();// This initilises all the veriables
		settext(0);// this sets the text views and buttons to the text that they
					// should have
		OnClick();// this sets up the onclickliseners for the 2 buttons

	}

	private void settext(int val) {
		// sets all teh text to the defult non coneced state
		if (val == 0) {
			connect.setText("Tap To Connect");
			instructions
					.setText("Tap the button abuve To Start sending instructions to the IP specifed. ");
		}

		Status.setText("Not Connected");

	}

	private void init() {
		// set the veriables to be sent to there defult value(not moving and
		// still conected)
		gofront = 1;
		goside = 1;
		command = 0;

		// initilise the typeface(font) for the buttons and textviews to use.
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");

		// initilise the buttons using finsViewById
		prefs = (Button) findViewById(R.id.Main_prefs);
		connect = (Button) findViewById(R.id.Main_Connect);
		front = (Button) findViewById(R.id.main_Front);
		back = (Button) findViewById(R.id.Main_Back);
		left = (Button) findViewById(R.id.Main_Left);
		right = (Button) findViewById(R.id.Main_Right);

		// set the OnTouchLisener by using "this" it means to use the implentend
		// method(implements OnTouchListener)
		front.setOnTouchListener(this);
		back.setOnTouchListener(this);
		left.setOnTouchListener(this);
		right.setOnTouchListener(this);

		// initilise the textviews using finsViewById
		Status = (TextView) findViewById(R.id.Main_Status);
		instructions = (TextView) findViewById(R.id.Main_Instructions);
		info = (TextView) findViewById(R.id.Main_InFo);

		// sets the font for the textviews and buttons
		prefs.setTypeface(typeface);
		connect.setTypeface(typeface);
		front.setTypeface(typeface);
		back.setTypeface(typeface);
		left.setTypeface(typeface);
		right.setTypeface(typeface);
		Status.setTypeface(typeface);
		instructions.setTypeface(typeface);
		info.setTypeface(typeface);

	}

	// this is the implemented method. to neten the code up and program object
	// orientedly, it is in a diffrent class so it returs the calue returned by
	// that class
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return UpdateMovment.OnTouch(v, event);
	}

	private void OnClick() {
		// set onclicklisener for "prefs" button
		prefs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// the Async runner will stop when command = 0
				command = 0;
				// sleep for 100ms so that it has defiantly closed
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// make and run an intent to move to the preferences class
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ShowPreference.class);
				startActivityForResult(intent, 0);

			}
		});
		// set new onclicklisener for the connect button
		connect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (running) {
					// if the AsyncTask is runnning then it will set the command
					// to 0 so it will Disconect and then set some of the text
					// back to defult
					command = 0;
					settext(1);
				} else {// if not running:

					// sleep just to make sure that if there was a previous
					// connection it is closed
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// get an instance of the shared prefrence so that i can
					// take the ip from it
					final SharedPreferences mySharedPreferences = PreferenceManager
							.getDefaultSharedPreferences(MainActivity.this);
					String ip = mySharedPreferences.getString("ipserver",
							"192.168.1.105");
					// make a new AsyncRunner and start it and passthru the ip
					AsyncTaskRunner runner = new AsyncTaskRunner();
					runner.execute(ip);
					running = true;
					command = 1;
				}

			}
		});

	}

}
