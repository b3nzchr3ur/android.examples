package com.example.grade;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.grade.R;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GradeActivity extends Activity {

	private static final String baseUrl = "http://192.168.1.4:8080/webecho/";
	private static final String getGradeUrl = baseUrl + "grade/get";
	private static final String setGradeUrl = baseUrl + "grade/set";
	private static final String TAG = "FHICT";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button button = (Button)findViewById(R.id.buttonNetworkStatus);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showNetworkStatus();
			}
		});
		
		button = (Button)findViewById(R.id.getGrade);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GetGradeTask task = new GetGradeTask();
				task.execute();
			}
		});

		button = (Button)findViewById(R.id.setGrade);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SetGradeTask task = new SetGradeTask();
				task.execute();
			}
		});
	}

	private void showNetworkStatus() {
		ConnectivityManager connectivityManager= 
				(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null) {
			displayOnUserInterface(networkInfo.getState() + " to " + 
					networkInfo.getTypeName());	
		} else {
			displayOnUserInterface("No network available.");
		}
	}

	private String postHttpEntityAndReturnResult(String url, HttpEntity httpEntity){
		String result = postToAndGetResult(url, httpEntity);
		return result;
	}

	private String postToAndGetResult(String url, HttpEntity httpEntity){
		String result = "None";
		
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			post.setEntity(httpEntity);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			result = client.execute(post,responseHandler);

		} catch (ClientProtocolException e) {
			appendExceptionToLog("ClientProtocalException",e);
		} catch (IOException e) {
			appendExceptionToLog("IOException",e);
		}

		return result;
	}

	private void displayOnUserInterface(String message) {
		EditText editView = (EditText)findViewById(R.id.editTextReply);
		editView.setText(message);
	}

	private void appendExceptionToLog(String message, Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		Log.d("SMTE4", sw.toString());
	}
	
	class GetGradeTask extends AsyncTask<Void,Void,String> {

		private String studentNumberText;

		@Override
		protected void onPreExecute() {
			EditText editTextStudentNumber = (EditText) findViewById(R.id.editTextStudentNumber);
			studentNumberText = editTextStudentNumber.getText().toString();
		}

		@Override
		protected String doInBackground(Void... params) {
			String gradeAsString = "?";
		
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("studentNumber", studentNumberText));
		
			UrlEncodedFormEntity urlEncodedFormEntity = null;
			try {
				urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
				postHttpEntityAndReturnResult(getGradeUrl, urlEncodedFormEntity);
				
				String jsonString = postToAndGetResult(getGradeUrl, urlEncodedFormEntity);
				
				JSONObject jsonObject = new JSONObject(jsonString);
				int grade = jsonObject.getInt("grade");
				String result = jsonObject.getString("result");
				gradeAsString = String.format("Result: %s, grade %d, json: %s", result,grade,jsonObject);
				Log.d(TAG, gradeAsString);
				
			} catch (UnsupportedEncodingException e) {
				appendExceptionToLog("UnsupportedEncodingException",e);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return gradeAsString;
		}

		@Override
		protected void onPostExecute(String result) {
			displayOnUserInterface(result);
		}
	}
	
	class SetGradeTask extends AsyncTask<Void,Void,String> {
		private String studentNumberText;
		private String gradeText;

		@Override
		protected void onPreExecute() {
			EditText editTextStudentNumber = (EditText) findViewById(R.id.editTextStudentNumber);
			studentNumberText = editTextStudentNumber.getText().toString();

			EditText editTextGrade = (EditText) findViewById(R.id.editTextGrade);
			gradeText = editTextGrade.getText().toString();
		}

		@Override
		protected String doInBackground(Void... params) {
			String result = "";
			
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("studentNumber", studentNumberText));
				nameValuePairs.add(new BasicNameValuePair("grade", gradeText));
		
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
		
				result = postHttpEntityAndReturnResult(setGradeUrl, urlEncodedFormEntity);
			} catch (UnsupportedEncodingException e) {
				appendExceptionToLog("UnsupportedEncodingException",e);
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			displayOnUserInterface(result);
		}
	}
}