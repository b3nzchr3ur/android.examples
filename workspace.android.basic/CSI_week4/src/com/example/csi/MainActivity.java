package com.example.csi;

import java.util.List;

import com.example.csi.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Some example code below on how to use CriminalProvider:
		CriminalProvider criminalProvider = new CriminalProvider(getApplicationContext() );
		List<Criminal> criminals = criminalProvider.GetCriminals();
		
		TextView header = (TextView) findViewById(R.id.textHeader);
		header.setText(String.format("Criminals [%d]:",criminals.size()));
		
		String boxText = "";
		for(Criminal criminal : criminals) {
			List<Crime> crimes = criminal.crimes;
			boxText += criminal.name + " has " + crimes.size() + " crimes\n";
		}
		
		EditText textCriminal = (EditText) findViewById(R.id.textCriminals);
		textCriminal.setText(boxText);
	}

}
