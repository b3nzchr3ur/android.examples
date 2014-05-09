package com.example.layouts;

import com.example.layouts.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LinearLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linear);
		
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Start clicked!",Toast.LENGTH_SHORT).show();
			}
		};
		
		Button buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(listener);
		
	}
	
	public void callClicked(View v) {
		Toast.makeText(this, "android:onClick=\"callClicked\"!",Toast.LENGTH_SHORT).show();
	}
}
