package com.example.geonamesdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.geonamesdemo.R;

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

public class NetappDemoActivity extends Activity {	
	//private static final String JSONurl = "http://api.geonames.org/findNearbyWikipediaJSON?formatted=true&lat=51.511&lng=5.616&username=xxxx&password=xxxx";
	private static final String JSONurl = "https://raw.githubusercontent.com/b3nzchr3ur/android.examples/master/workspace.android.basic/Network_GeonamesDemo/findNearbyWikipediaJSON.json";
	
	private static final String TAG="FHICT";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button button = (Button)findViewById(R.id.retrieveJSON);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RetrieveFromTwitterTask task = new RetrieveFromTwitterTask();
				task.execute();
			}
		});

		button = (Button)findViewById(R.id.buttonNetworkStatus);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showNetworkStatus();
			}
		});
	}

	private void showNetworkStatus() {
		ConnectivityManager connectivityManager= 
				(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
	
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null) {
			displayOnScreen(networkInfo.getState() + " to " + 
					networkInfo.getTypeName());	
		} else {
			displayOnScreen("No network available.");
		}
	}

	private String readTwitterFeedAsJsonStringUsingURL() {
		StringBuilder stringBuilder = new StringBuilder();
	
		try {
			InputStream inputStream = new URL(JSONurl).openStream();
	
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(inputStream));
	
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
	
		} catch (MalformedURLException e) {
			String mes = "MalformedURLException";
			appendExceptionToLog(mes, e);
			return null;
		} catch (IOException e) {
			String mes = "MalformedURLException";
			appendExceptionToLog(mes, e);
		}
	
		return stringBuilder.toString();
	}

	//Note: This code will give more information on http errors:
	private String retrieveTwitterFeedAsJsonStringUsingHttpClient() {
		StringBuilder builder = new StringBuilder();

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(JSONurl);

			HttpResponse response = client.execute(httpGet);

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d(TAG,"HTTP: StatusCode = " + statusCode);
			}
		} catch (ClientProtocolException e) {
			String mes = "ClientProtocolException";
			appendExceptionToLog(mes,e);
		} catch (IOException e) {
			String mes = "IOException"; 
			appendExceptionToLog(mes,e);
		}

		return builder.toString();
	}

	private String parseJsonStringToMessage(String twitterJsonString ) {
		String twitterMessage = "";
		try {
			JSONObject jsonObject = new JSONObject(twitterJsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("geonames");
			
			int numberOfPosts = jsonArray.length();
			for(int messageIndex = 0; (messageIndex < 3) && (messageIndex < numberOfPosts); messageIndex++) {
				JSONObject twitterPostAsJsonObject = jsonArray.getJSONObject(messageIndex);
				
				twitterMessage += String.format("[%d] - %s\n\n", messageIndex, twitterPostAsJsonObject.getString("summary")); 
			}
		} catch (JSONException e) {
			twitterMessage = "JSONException";
			appendExceptionToLog(twitterMessage,e);
		}
		
		return twitterMessage;
	}

	private void appendExceptionToLog(String message, Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		Log.d(TAG, sw.toString());
	}

	private void displayOnScreen(String message) {
		EditText editView = (EditText)findViewById(R.id.editTextReply);
		editView.setText(message);
	}
	
	/*----------------------------------------------------------------------------*/
	class RetrieveFromTwitterTask extends AsyncTask<Void,Void,String> {
		/* Note when doInBackGround is finished this method will 
		 * run on the user interface Thread */
		@Override
		protected void onPostExecute(String twitterMessage) {
			displayOnScreen(twitterMessage);
		}

		/*Note: this method will run on a new Thread so all the 
		 * hard work will not freeze up the user interface
		 */
		protected String doInBackground(Void... arg0) {
			String twitterJsonString = retrieveTwitterFeedAsJsonStringUsingHttpClient();
			//String twitterJsonString = readTwitterFeedAsJsonStringUsingURL();
			
			if((twitterJsonString == null) || (twitterJsonString.trim().length() == 0)){
				return "No twitterFeed!";
			}
			
			String message = parseJsonStringToMessage(twitterJsonString);
			return message;
		}
	}
}