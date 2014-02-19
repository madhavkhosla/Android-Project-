package com.example.testapp;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.testapp.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadWebpageAsyncTask extends Activity{
	private TextView textView;
	private EditText edittext;
	public String response = null;
	public String[] b = null ;
	String url;
	public Date dt;
	java.util.Date d1;
	public String date;
	
	String tabid = null;
	SharedPrefs sp = null;
	
	DefaultHttpClient client = null;
	HttpGet httpGet = null;
	
	HttpResponse execute = null;
	InputStream content = null;
	
	BufferedReader buffer = null;
	String s = null;
	DownloadWebPageTask task = null;
	
	Bundle basket = null;
	
	Dialog d = null;
	TextView tv = null;
	HotOrNot entry  = null;
	HotOrNot hdate = null;
	Intent ia = null;
	
	boolean isDataBeingErased = false;
	boolean showSyncComplete = false;
	//static boolean showSyncComplete = false;
	
	//static int syncCounter = 0, syncSuccessCounter = 0, syncFailCounter = 0;
	int syncCounter = 0, syncSuccessCounter = 0, syncFailCounter = 0;
	
	int response_length = 0;
	
	public ReadWebpageAsyncTask()
	{
		
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView = (TextView) findViewById(R.id.TextView01);
		edittext = (EditText)findViewById(R.id.eTURL);
		sp = new SharedPrefs();
		tabid = sp.sendTabId();
		//edittext.setText("http://10.99.99.14:8080/AndroidSearch/output/abc"+tabid+".html");
		if(Configuration.live_or_test.equalsIgnoreCase("live"))
			edittext.setText(Configuration.AndroidTabOutput_URL_live.replaceAll("<tabid>", tabid));
		else
			edittext.setText(Configuration.AndroidTabOutput_URL_test.replaceAll("<tabid>", tabid));
		
		sp = null;
		tabid = null;
		
	}
	
	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			response = null;
			response = "";
			for (String url : urls) {
				
				try {
					client = new DefaultHttpClient();
					httpGet = new HttpGet(url);
	
					execute = client.execute(httpGet);
					content = execute.getEntity().getContent();
					
					buffer = new BufferedReader(new InputStreamReader(content));
					s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				client = null;
				httpGet = null;
				execute = null;
				content = null;
				buffer = null;
				s = null;
			}
			return response;
	
		}
	
		@Override
		protected void onPostExecute(String result) {
			textView.setText(result);
		}
	}	// class DownloadWebPageTask
	
	
	
	public void onClick(View view) {
		switch (view.getId()){
	
			case R.id.bGetUrl:
				url = null;
				//url = edittext.getText().toString();
				//  	SharedPrefs sp = new SharedPrefs();
				//	String tabid = sp.sendTabId();
				//    	Dialog d1 = new Dialog(this);
				//		 d1.setTitle("tabid");
				//		 TextView tv = new TextView(this);
				//		 tv.setText(tabid);
				//		 d1.setContentView(tv);
				//		 d1.show();
				//if(Configuration.live_or_test.equalsIgnoreCase("live"))
				//	edittext.setText(Configuration.AndroidTabOutput_URL_live.replaceAll("<tabid>", tabid));
				//else
				//	edittext.setText(Configuration.AndroidTabOutput_URL_test.replaceAll("<tabid>", tabid));
				
				url = edittext.getText().toString().trim();
				if(url == null || url.trim().length()<=0)
				{
					d = new Dialog(this);
					d.setTitle("Check...");
					tv = new TextView(this);
					tv.setText("Enter the URL first!!!");
					d.setContentView(tv);
					d.show();				
				}
				d = null;
				tv = null;
				
				break;	
				
			case R.id.readWebpage:
			
				if(url == null || url.trim().length()<=0)
				{
					d = new Dialog(this);
					d.setTitle("Check...");
					tv = new TextView(this);
					tv.setText("Get the URL first!!!");
					d.setContentView(tv);
					d.show();				
				}
				else
				{
					task = new DownloadWebPageTask();
					task.execute(new String[] { url});
					task = null;
				}				
				d = null;
				tv = null;
				
				break;
			
			case R.id.bSyn:
			
				if(response == null || response.trim().length()<=0)
				{
					d = new Dialog(this);
					d.setTitle("Check...");
					tv = new TextView(this);
					tv.setText("Get the URL and Read Webpage first!!!");
					d.setContentView(tv);
					d.show();				
				}
				else
				{
					isDataBeingErased = false;
					showSyncComplete = false;
					syncCounter = 0;
					syncSuccessCounter = 0;
					syncFailCounter = 0;
					try{
						basket = new Bundle();
						b = response.split("##");	
						response_length = b.length;
						//for(int i=0;i<b.length;i++){
						//for(int i=(b.length-1);i >= 0;i--){
						for(int i=(response_length-1);i >= 0;i--){
							//if(b[0].equals("http://asc.iitb.ac.in/securei.jsp")){
							if(b[0].equals(Configuration.tablet_data_eraser_output)){
								
								entry  = new HotOrNot(ReadWebpageAsyncTask.this);
								entry.open();
								entry.emergency();
								entry.close();
								entry = null;
								
								isDataBeingErased = true;
								break;
							}
							else
							{
								
								dt =  new Date(); 
								date = dt.toString();
		
								hdate = new HotOrNot(ReadWebpageAsyncTask.this);
								hdate.open();
								hdate.createEntryDate(date);
								hdate.close();
								hdate = null;
			 
								
								basket.putString("url_key", b[i]);
								
								
								syncCounter++;
								
								//basket.putString("syncCounter", syncCounter+"");
								basket.putString("syncCounter", (i+1)+"");

								//if(syncCounter>0 && syncCounter%5==0)
								//	Toast.makeText(ReadWebpageAsyncTask.this, "Loop "+syncCounter+" / "+response_length+" completed.", Toast.LENGTH_LONG).show();
								
								if(syncCounter == response_length)
									showSyncComplete = true;
								
								ia = new Intent(ReadWebpageAsyncTask.this,TestExternalDatabase.class);
								ia.putExtras(basket);
								startActivity(ia);
								
								
							}
							
							entry = null;
							hdate = null;
							ia = null;
							dt = null;
							date = null;
						}	// for loop
						
						
					}catch(Exception e){
					
						d = new Dialog(this);
						d.setTitle("Exception in Data - Sync");
						tv = new TextView(this);
						tv.setText(e.toString());
						d.setContentView(tv);
						d.show();
					}
					
					
					if(isDataBeingErased)
					{
						d = new Dialog(this);
						d.setTitle("Final Sync Status...");
						tv = new TextView(this);
						tv.setText("Sync Completed.");
						d.setContentView(tv);
						d.show();
					}
					else
					{

						if(showSyncComplete)
						{
							d = new Dialog(this);
							d.setTitle("Final Sync Status...");
							tv = new TextView(this);
							tv.setText("Sync Complete ( in total "+response_length+" loops )");
							d.setContentView(tv);
							d.show();
						}
						else
						{
							if(syncCounter > 0)
							{
								d = new Dialog(this);
								d.setTitle("Final Sync Status...");
								tv = new TextView(this);
								tv.setText("Sync In-Complete ( Done "+syncCounter+" of "+response_length+" loops )");
								d.setContentView(tv);
								d.show();
							}	
						}
						
					}	
					textView.setText("");
					
				}
				tv = null;
				d = null;
				
				entry = null;
				hdate = null;
				dt = null;
				date = null;
	
				ia = null;
				basket = null;
				b = null;
				response = null;
				
				break; 

			
		}	// switch
	}
	
/*	public static void increment_SyncSuccessCounter()
	{
		syncSuccessCounter++;
	}

	public static void increment_SyncFailCounter()
	{
		syncFailCounter++;
		//return syncCounter;
	}
	
	public static void increment_SyncCounter()
	{
		syncCounter++;
		
		//return syncCounter;
	}

	public void DisplaySyncCompleteMsg()
	{
		
		if(syncSuccessCounter == response_length && syncFailCounter == 0)
		{
			showSyncComplete = true;
		}
		
		if(syncCounter>0 && syncCounter%5==0)
		{
			Toast.makeText(ReadWebpageAsyncTask.this, "Loop "+syncCounter+" of "+response_length+" executed.", Toast.LENGTH_LONG).show();
		}

		if(showSyncComplete)
		{
			d = new Dialog(this);
			d.setTitle("Final Sync Status...");
			tv = new TextView(this);
			tv.setText("Sync Complete ( in total "+response_length+" loops )");
			d.setContentView(tv);
			d.show();
		}
		else
		{
			if(syncCounter > 0 && syncCounter == response_length )
			{
				d = new Dialog(this);
				d.setTitle("Final Sync Status...");
				tv = new TextView(this);
				tv.setText("Sync In-Complete ( Done "+(syncSuccessCounter - syncFailCounter)+" of "+response_length+" loops )");
				d.setContentView(tv);
				d.show();
			}	
		}

		d = null;
		tv = null;
	}
	*/
		
} 