package com.example.style;

import com.example.style.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class SecondActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String city = intent.getStringExtra("city");

		String output = "Name: " + name + "\nCity: " + city + "\n";

		EditText editSubmitDetails = (EditText) findViewById(R.id.editSubmitDetails);
		editSubmitDetails.setText(output);
	}
}
