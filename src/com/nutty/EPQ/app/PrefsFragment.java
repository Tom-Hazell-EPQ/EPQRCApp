package com.nutty.EPQ.app;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/*
 * This is required to show the prefrences as there own activity
 * @author Tom Hazell (C) 2014 Tom Hazell
 */
public class PrefsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from the XML file
		addPreferencesFromResource(R.xml.prefrences);
	}

}
