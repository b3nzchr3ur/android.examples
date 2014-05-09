package com.example.style;

import com.example.style.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		
		Button button = (Button) findViewById(R.id.buttonSubmit);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FirstActivity.this,SecondActivity.class);

				EditText editName = (EditText) findViewById(R.id.editName);
				EditText editCity = (EditText) findViewById(R.id.editCity);

				intent.putExtra("name", editName.getText().toString());
				intent.putExtra("city", editCity.getText().toString());
				
				startActivity(intent);
			}
		});
	}
}