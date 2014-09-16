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

	public static Button prefs, connect;
	public static TextView info;
	public static Button front;
	public static Button back;
	public static Button left;
	public static Button right;
	public static TextView Status, instructions;
	public static int gofront, goside, command;
	public static boolean running = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar
		setContentView(R.layout.activity_main);
		init();
		settext();
		OnClick();

	}

	private void settext() {
		connect.setText("Tap To Connect");
		instructions
				.setText("Tap the button abuve To Start sending instructions to the IP specifed. ");
		Status.setText("Not Connected");
	}

	private void init() {
		gofront = 1;
		goside = 1;

		command = 0;
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		// txtyour.setTypeface(type);

		prefs = (Button) findViewById(R.id.Main_prefs);
		connect = (Button) findViewById(R.id.Main_Connect);
		front = (Button) findViewById(R.id.main_Front);
		back = (Button) findViewById(R.id.Main_Back);
		left = (Button) findViewById(R.id.Main_Left);
		right = (Button) findViewById(R.id.Main_Right);

		front.setOnTouchListener(this);
		back.setOnTouchListener(this);
		left.setOnTouchListener(this);
		right.setOnTouchListener(this);

		Status = (TextView) findViewById(R.id.Main_Status);
		instructions = (TextView) findViewById(R.id.Main_Instructions);
		info = (TextView) findViewById(R.id.Main_InFo);
		
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return UpdateMovment.OnTouch(v, event);
	}

	private void OnClick() {
		prefs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				command = 0;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}// sleep So that the connection can be closed

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ShowPreference.class);
				startActivityForResult(intent, 0);

			}
		});

		connect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (running) {
					command = 0;
					settext();
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// sleep just to make sure that if there was a previous
						// connection it is closed
					final SharedPreferences mySharedPreferences = PreferenceManager
							.getDefaultSharedPreferences(MainActivity.this);
					String ip = mySharedPreferences.getString("ipserver",
							"192.168.1.105");

					AsyncTaskRunner runner = new AsyncTaskRunner();
					runner.execute(ip);
					running = true;
					command = 1;
				}

			}
		});

	}

}
