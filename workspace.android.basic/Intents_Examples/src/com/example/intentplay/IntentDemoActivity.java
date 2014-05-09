package com.example.intentplay;

import com.example.intentplay.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class IntentDemoActivity extends Activity {
	private static final int PHONE_REQUEST_CODE = 42;
	
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button getPhoneButton = (Button)findViewById(R.id.getPhoneNumberButton);
        getPhoneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						                   GetPhoneActivity.class);
				intent.putExtra("name", "Lucky Luke");
				startActivityForResult(intent, PHONE_REQUEST_CODE);
			}
		});
        
        Button webButton = (Button)findViewById(R.id.webButton);
        webButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://www.eindhoven.nl"));
				startActivity(intent);
			}
		});
        
        Button callButton = (Button)findViewById(R.id.callButton);
        callButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL,
						Uri.parse("tel:(+49)12345789"));
				startActivity(intent);
			}
		});
        
        Button geoButton = (Button)findViewById(R.id.geoButton);
        geoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("geo:51.453793,5.481491?z=19"));
				startActivity(intent);
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if(RESULT_OK == resultCode && requestCode == PHONE_REQUEST_CODE) {
    		String phoneNumber = data.getStringExtra("phoneNumber");
    		TextView phoneNumberView = (TextView)findViewById(R.id.textViewShowPhoneNumber);
    		phoneNumberView.setText("Phone = " + phoneNumber);
    	}
    }
}