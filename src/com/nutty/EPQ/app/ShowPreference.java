package com.nutty.EPQ.app;

import android.app.Activity;
import android.os.Bundle;
/*
 * this uses the preference fragment class to xml preferences
 * 
 */
public class ShowPreference extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//sets content to PrestFragment class
		getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
	}

}
