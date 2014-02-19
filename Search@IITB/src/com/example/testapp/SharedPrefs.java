package com.example.testapp;



import android.app.Activity; 
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPrefs extends Activity implements OnClickListener{
	EditText sharedData;
	public static final String filename = "MySharedString";
	SharedPreferences someData;
	SharedPreferences settings ;
	String dataReturned;
	public static  String datatabid;
	String stringData ;
	TextView dataResults;
	public static final String PREFS_NAME = "MyPrefsFile";

	
	boolean isTabId_valid = true;
	int TabId_int = 0;
	boolean hasLoggedIn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		setupVariables();
		someData = getSharedPreferences(filename, 0);
		settings = getSharedPreferences(PREFS_NAME, 0);
		
	}

	private void setupVariables() {
		// TODO Auto-generated method stub
		Button save = (Button) findViewById(R.id.bSave);
		Button load = (Button) findViewById(R.id.bLoad);
		sharedData = (EditText) findViewById(R.id.etSharedPrefs);
		dataResults = (TextView) findViewById(R.id.tvLoadSharedPrefs);
		save.setOnClickListener(this);
		load.setOnClickListener(this);

		someData = getSharedPreferences(filename, 0);
		dataReturned = someData.getString("sharedString", "Couldn't Load Data");
		datatabid = dataReturned;
		
		settings = getSharedPreferences(PREFS_NAME, 0);
		hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
//		Dialog d = new Dialog(this);
//		 d.setTitle("tabid");
//		 TextView tv = new TextView(this);
//		 tv.setText(dataReturned);
//		 d.setContentView(tv);
//		 d.show();
		if(hasLoggedIn)
		{
		    //Go directly to main activity
			Intent intent = new Intent();
			intent.setClass(SharedPrefs.this, UserAuth.class);
			startActivity(intent);
			finish();
			intent = null;
		}
		hasLoggedIn = false;
		
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){
			case R.id.bSave:
				TabId_int = 0;
				someData = getSharedPreferences(filename, 0);
				dataReturned = someData.getString("sharedString", "Couldn't Load Data");
				try
				{
					TabId_int = Integer.parseInt(dataReturned);
				}
				catch(Exception e1)
				{
					TabId_int = 0;
				}				

				if(TabId_int > 0 )
				{
					Toast.makeText(SharedPrefs.this, "TAB-ID Already Saved!!!", Toast.LENGTH_LONG).show();
				}
				else
				{
				
					TabId_int = 0;
					isTabId_valid = true;
					stringData = sharedData.getText().toString().trim();
					
					try
					{
						//Toast.makeText(SharedPrefs.this, "-"+stringData+"-", Toast.LENGTH_LONG).show();
						
						TabId_int = Integer.parseInt(stringData);
						
						//Toast.makeText(SharedPrefs.this, "-"+TabId_int+"-", Toast.LENGTH_LONG).show();
						
						if(TabId_int<=0 || TabId_int > Configuration.maxTabId_allowed )	// 100 only allowed
							isTabId_valid = false;
					}
					catch(Exception e1)
					{
						isTabId_valid = false;
						//Toast.makeText(SharedPrefs.this, "-"+e1.toString(), Toast.LENGTH_LONG).show();
					}
					if(isTabId_valid)
					{
						SharedPreferences.Editor editor = someData.edit();
						editor.putString("sharedString", stringData.trim());
						
						SharedPreferences.Editor editor1 = settings.edit();
						editor1.putBoolean("hasLoggedIn", true);
					
						// Commit the edits!
						editor.commit();
						editor1.commit();
						
						Toast.makeText(SharedPrefs.this, "TAB-ID - "+stringData.trim()+" Saved Successfully!!!", Toast.LENGTH_LONG).show();
					
						dataResults.setText("TAB-ID - "+stringData.trim());
						
						datatabid = stringData.trim();
						
						Intent intent = new Intent();
						intent.setClass(SharedPrefs.this, UserAuth.class);
						startActivity(intent);
						intent = null;
						
					}
					else
						Toast.makeText(SharedPrefs.this, "Please Enter TAB-ID properly ( 01 to "+Configuration.maxTabId_allowed+" only) !!!", Toast.LENGTH_LONG).show();
				}
				someData = null;
				dataReturned = null;
				TabId_int = 0;
				break;
			
			case R.id.bLoad:
				someData = getSharedPreferences(filename, 0);
				dataReturned = someData.getString("sharedString", "Couldn't Load Data");
				datatabid = dataReturned;
				
				if(datatabid==null || datatabid.trim().length()<=0 || datatabid.equalsIgnoreCase("Couldn't Load Data"))
				{
					datatabid = null;
					Toast.makeText(SharedPrefs.this, "TAB-ID Not Found. Please Save TAB-ID First!!!", Toast.LENGTH_LONG).show();
				}
				else
				{
					dataResults.setText("TAB-ID - "+datatabid);
					
					Intent intent = new Intent();
					intent.setClass(SharedPrefs.this, UserAuth.class);
					startActivity(intent);
					intent = null;
				}
				
				break;
		}
	}
	
	public String sendTabId(){
		
		return datatabid;
		
	}
	

}
