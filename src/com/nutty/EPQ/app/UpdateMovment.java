package com.nutty.EPQ.app;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class UpdateMovment {

	public static boolean OnTouch(View v, MotionEvent event) {
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
