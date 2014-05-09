package com.example.intentplay;

import com.example.intentplay.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ShowSendActivity extends Activity {
	 /** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sendshow);
	        
	        Intent intent = getIntent();
	        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
	        	EditText editText = (EditText)findViewById(R.id.EditTextDetails);
	        	editText.setText(intent.getExtras().getString(Intent.EXTRA_TEXT));
	        }
	        if(intent.hasExtra(Intent.EXTRA_SUBJECT)) {
	        	EditText editText = (EditText)findViewById(R.id.EditTextSubject);
	        	editText.setText(intent.getExtras().getString(Intent.EXTRA_SUBJECT));
	        }
		}
}
