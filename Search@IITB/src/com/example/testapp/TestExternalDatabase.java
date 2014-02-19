package com.example.testapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONObject;



import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class TestExternalDatabase extends Activity {
	
	int count;
	
	
	String url = null;
	String syncCounter = null;
	TextView resultView;
	Bundle gotBasket = null;
	
	String result = null;
	InputStream isr = null;
	HttpClient httpclient = null;
	HttpPost httppost = null;
	HttpResponse response =  null;
	HttpEntity entity = null;
	
	BufferedReader reader = null;
	StringBuilder sb = null;
	String line = null;
	Dialog d = null;
	TextView tv = null;
	
	String s = null;
    String base64image = null;

    JSONArray jArray = null;
    JSONObject json = null;
    
	String[] id=null, name=null, lname=null, PermanentAddress=null,Person_Type=null;
	String[] Gender=null, email=null, dob=null, validupto=null, telint=null, telext=null;
	String[] dept=null, bloodgrp=null, medstat=null, marstat=null, relation=null;
	String[] bldtype=null, bldno=null, bldroom=null, security=null, latitude=null, longitude=null;

	HotOrNot hn = null; 
	
	
	byte[] image = null;	
	
	boolean proceed = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testexternaldatabase);
		
		StrictMode.enableDefaults(); //STRICT MODE ENABLED
		
		resultView = (TextView) findViewById(R.id.result);
		
		gotBasket = getIntent().getExtras();
		url = gotBasket.getString("url_key");
		syncCounter = gotBasket.getString("syncCounter");
		getData();
		
		finish();
		
		resultView = null;
	}
	
	public void getData(){
	
		proceed = true;
		
		result = null;
		result = "";
		isr = null;
		try{
			
			httpclient = new DefaultHttpClient();
			HttpProtocolParams.setUserAgent(httpclient.getParams(), "Android Tablet Java HttpClient");
			
			httppost = new HttpPost(url); //YOUR PHP SCRIPT ADDRESS 
			response = httpclient.execute(httppost);
			
			entity = response.getEntity();
			isr = entity.getContent();
		}
		catch(Exception e){
			//Log.e("log_tag", "Error in http connection "+e.toString());
			d = new Dialog(this);
			d.setTitle("did it");
			tv = new TextView(this);
			tv.setText(e.toString());
			d.setContentView(tv);
			d.show();
			
			resultView.setText("Could not Connect to database");
			proceed = false;
		}

		if(proceed)
		{
			
		
			//convert response to string
			try{
				reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
				sb = new StringBuilder();
				line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				isr.close();
			
				result=sb.toString();
			}
			catch(Exception e){
				//Log.e("log_tag", "Error Converting result "+e.toString());
		
				d = new Dialog(this);
				d.setTitle("did it");
				tv = new TextView(this);
				tv.setText(e.toString());
				d.setContentView(tv);
				d.show();
				
				resultView.setText("Personal Information Not Obtained Properly!!!");
				
				proceed = false;
			}
		}
	
		if(proceed)
		{
			
			isr = null;
	    	httpclient = null;
	    	httppost = null;
	    	response = null;
	    	entity = null;
	    	reader = null;
	    	sb = null;
	    	line = null;
	    	d = null;
	    	tv = null;	
			
			//parse json data
			try {
				
				
						
				s = null;
	    		s = "";
			  
	    		jArray = new JSONArray(result);
	    		
	    		id= new String[jArray.length()];
	    		name= new String[jArray.length()];
	    		lname= new String[jArray.length()]; 
	    		PermanentAddress= new String[jArray.length()];
	    		Person_Type= new String[jArray.length()];
	    		Gender= new String[jArray.length()]; 
			   
				email= new String[jArray.length()];
				dob= new String[jArray.length()];
				validupto= new String[jArray.length()]; 
				telint= new String[jArray.length()];
				telext= new String[jArray.length()];
					   
				dept= new String[jArray.length()];
				bloodgrp= new String[jArray.length()];
				medstat= new String[jArray.length()]; 
				marstat= new String[jArray.length()];
				relation= new String[jArray.length()];
					   
				bldtype= new String[jArray.length()];
				bldno= new String[jArray.length()];
				bldroom= new String[jArray.length()]; 
				security= new String[jArray.length()];
				latitude= new String[jArray.length()]; 
				longitude= new String[jArray.length()];
	
				hn = new HotOrNot(this);
				hn.open();
				
				for(int i=0; i<jArray.length();i++)
				{
					
					json = jArray.getJSONObject(i);
				   
					id[i] = json.getString("Person_Code");
					name[i] = json.getString("Person_Name");
					lname[i] =  json.getString("Person_LastName");
					PermanentAddress[i] = json.getString("PermanentAddress");
					Person_Type[i] = json.getString("Person_Type");
					Gender[i] =  json.getString("Gender");
				   
					email[i] = json.getString("Email");
					dob[i] = json.getString("DOB");
					validupto[i] =  json.getString("Validupto");
					telint[i] = json.getString("Telephone_Internal");
					telext[i] = json.getString("Telephone_External");
					dept[i] =  json.getString("Department");
				   
					bloodgrp[i] = json.getString("BloodGroup");
					medstat[i] = json.getString("Medical_Status");
					marstat[i] =  json.getString("Marital_Status");
					relation[i] = json.getString("Relation");
					bldtype[i] = json.getString("Bldg_Type");
					bldno[i] =  json.getString("Bldg_No");
				   
					bldroom[i] = json.getString("Bldg_Room_No");
					security[i] =  json.getString("Security_Remarks");
				   
					latitude[i] = json.getString("latitude");
					longitude[i] =  json.getString("longitude");
				   
	
					
					base64image = json.getString("Photo");
					image = Base64.decode(base64image, Base64.DEFAULT);
				 
					s = s + i+"\n"+"Id : "+id[i]+"\n"+"Name : "+name[i]+"\n"+"Last Name: "+lname[i]+"\n"+"Image : " +image+"\n"+"\n\n";	
	
					//hn.open();
				   
				   
					try{
						count=1;
						hn.deleteEntry1(id[i]);
					   
						//d = new Dialog(this);
						//d.setTitle("did it");
						//tv = new TextView(this);
						//tv.setText("deleted"+count);
						//d.setContentView(tv);
						//d.show();
					   
					}catch(Exception e){
						count++;
					   
						//d = new Dialog(this);
						//d.setTitle("did it");
						//tv = new TextView(this);
						//tv.setText("exception"+count);
						//d.setContentView(tv);
						//d.show();
					   
					}finally{
					   
						if(count==1){
							hn.createEntry(id[i],name[i], lname[i],image,PermanentAddress[i],Person_Type[i],Gender[i],email[i],dob[i],validupto[i],telint[i],telext[i],dept[i],bloodgrp[i],medstat[i],marstat[i],relation[i],bldtype[i],bldno[i],bldroom[i],security[i],latitude[i],longitude[i]);
	
							//hn.close();
							//isr.close();
	
						}else{	// record Not Found hence Its was not deleted. Treated as First time INSERT.
							hn.createEntry(id[i],name[i], lname[i],image,PermanentAddress[i],Person_Type[i],Gender[i],email[i],dob[i],validupto[i],telint[i],telext[i],dept[i],bloodgrp[i],medstat[i],marstat[i],relation[i],bldtype[i],bldno[i],bldroom[i],security[i],latitude[i],longitude[i]);
							//hn.close();
							//isr.close();
						}
					}
	
					base64image = null;
					image = null;
					//s = null;
					json = null;
				   
				} // for loop
				hn.close();
				
				resultView.setText(s);
				
				Toast.makeText(TestExternalDatabase.this, "Loop "+syncCounter+" Completed. Please Wait...", Toast.LENGTH_LONG).show();				
			
			} catch (Exception e) {
			// TODO: handle exception
				//Log.e("log_tag", "Error Parsing Data "+e.toString());
				d = new Dialog(this);
				d.setTitle("did it");
				tv = new TextView(this);
				tv.setText(e.toString());
				d.setContentView(tv);
				d.show();
				
				resultView.setText("Error in Parsing Personal Informations!!!");

				Toast.makeText(TestExternalDatabase.this, "Loop "+syncCounter+" FAILED. Please Wait...", Toast.LENGTH_LONG).show();				
			}
			
			
			
		}	// proceed = true;
		
		d = null;
    	tv = null;
    	
    	
    	jArray = null;
		
    	base64image = null;
		image = null;
		s = null;
		json = null;
    	
		id= null;
		name= null;
		lname= null;
		PermanentAddress= null;
		Person_Type= null;
		Gender= null;
	   
		email= null;
		dob= null;
		validupto= null;
		telint= null;
		telext= null;
			   
		dept= null;
		bloodgrp= null;
		medstat= null;
		marstat= null;
		relation= null;
			   
		bldtype= null;
		bldno= null;
		bldroom= null;
		security= null;
		latitude= null;
		longitude= null;

		hn = null;  	
		result = null;
		
		url = null;
		syncCounter = null;
		
	}
	
	
	
}
