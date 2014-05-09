package com.example.layouts;

import com.example.layouts.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SMTE4_layoutsActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int[] buttonIds = new int[] { R.id.linearButton, 
				R.id.relativeButton, R.id.scrollButton, 
				R.id.tableButton, R.id.frameButton };

		for(int buttonId : buttonIds) {
			Button button = (Button) findViewById(buttonId);
			button.setOnClickListener(this);
		}
	}

	public void onClick(View view) {
		Intent intent = null;

		switch(view.getId()) 
		{
		case R.id.linearButton:
			intent = new Intent(getApplicationContext(),LinearLayoutActivity.class); 
			break;
		case R.id.relativeButton:
			intent = new Intent(getApplicationContext(),RelativeLayoutActivity.class); 
			break;
		case R.id.tableButton:
			intent = new Intent(getApplicationContext(),TableLayoutActivity.class); 
			break;
		case R.id.scrollButton:
			intent = new Intent(getApplicationContext(),ScrollViewActivity.class); 
			break;
		case R.id.frameButton:
			intent = new Intent(getApplicationContext(),FrameLayoutActivity.class); 
			break;
		}

		if(intent != null) {
			startActivity(intent);
		}
	}
}