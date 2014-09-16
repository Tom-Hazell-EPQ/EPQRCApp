package com.nutty.EPQ.app;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

/*
 * This is the class that deals with updating the values when buttons are pressed to is passed in from mainactivity
 * @author Tom Hazell
 * (C) 2014 Tom Hazell
 */
public class UpdateMovment {

	public static boolean OnTouch(View v, MotionEvent event) {
		
		//switch for when buttons are pressed 
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			switch (v.getId()) {
			case (R.id.main_Front):
				MainActivity.gofront = 0;
				break;
			case (R.id.Main_Back):
				MainActivity.gofront = 2;
				break;
			}
			switch (v.getId()) {
			case (R.id.Main_Left):
				MainActivity.goside = 2;
				break;
			case (R.id.Main_Right):
				MainActivity.goside = 0;
				break;
			}
			;
			return true;

			
			//switch and case for when they are unpressed
		case MotionEvent.ACTION_UP:
			// No longer down

			switch (v.getId()) {
			case (R.id.main_Front):
				MainActivity.gofront = 1;
				break;
			case (R.id.Main_Back):
				MainActivity.gofront = 1;
				break;
			}
			switch (v.getId()) {
			case (R.id.Main_Left):
				MainActivity.goside = 1;
				break;
			case (R.id.Main_Right):
				MainActivity.goside = 1;
				break;
			}
			;
			return true;
		}
		return false;

	}
}
