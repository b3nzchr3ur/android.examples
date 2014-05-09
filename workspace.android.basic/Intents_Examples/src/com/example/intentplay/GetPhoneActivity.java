package com.example.intentplay;

import com.example.intentplay.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetPhoneActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getphone);
        
        Intent sendIntent = getIntent();
        TextView helloView = (TextView)findViewById(R.id.textViewHello);
        String labelGreeting = "Hello ";
        if(sendIntent.hasExtra("name")) {
        	labelGreeting += sendIntent.getExtras().getString("name");
        } 
        else
        {
        	labelGreeting += "stranger...";
        }        
        helloView.setText(labelGreeting);
        
        Button button = (Button)findViewById(R.id.doneButton);
        button.setOnClickListener(new OnClickListener() {       	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),			
						IntentDemoActivity.class);
				EditText editText = (EditText)findViewById(R.id.editTextPhone);
				intent.putExtra("phoneNumber",editText.getText().toString());
				setResult(RESULT_OK,intent);
				finish();
			}
		});
    }
}
