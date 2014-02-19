package com.example.testapp;

import android.app.Activity;  
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleBrowser extends Activity  implements OnClickListener {


	EditText url = null;
	WebView ourBrow = null;
	String theWebsite = null;
	InputMethodManager imm = null;
	Intent ia = null;
	TextView tv = null;
	Dialog d = null;
	
	Button go = null,back = null,refresh = null,forward = null,synch = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);
		
			
		ourBrow = (WebView) findViewById(R.id.wvBrowser);
		
		ourBrow.getSettings().setJavaScriptEnabled(true);
		
		ourBrow.getSettings().setLoadWithOverviewMode(true);
		ourBrow.getSettings().setUseWideViewPort(true);
		
		ourBrow.setWebViewClient(new ourViewClient());
		try{
			//ourBrow.loadUrl("http://10.99.99.14:8080/AndroidSearch");
			if(Configuration.live_or_test.equalsIgnoreCase("live"))
				ourBrow.loadUrl(Configuration.AndroidSearchWeb_URL_live);
			else
				ourBrow.loadUrl(Configuration.AndroidSearchWeb_URL_test);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	
		
		go = (Button) findViewById(R.id.bGo);
		back = (Button) findViewById(R.id.bBack);
		refresh = (Button) findViewById(R.id.bRefresh);
		forward = (Button) findViewById(R.id.bForward);
		synch = (Button) findViewById(R.id.bSynch);
		
		url = (EditText) findViewById(R.id.eTurl);
		
		go.setOnClickListener(this);
		back.setOnClickListener(this);
		refresh.setOnClickListener(this);
		forward.setOnClickListener(this);
		synch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		switch (v.getId()){
			case R.id.bGo:
				
				theWebsite = url.getText().toString();
				ourBrow.loadUrl(theWebsite);
				//hiding the Keyboard after using an EditText
				imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
				
				imm = null;
				break;
				
			case R.id.bBack:
				if (ourBrow.canGoBack())
					ourBrow.goBack();
				break;
			case R.id.bForward:
				if (ourBrow.canGoForward())
					ourBrow.goForward();
				break;
			case R.id.bRefresh:
				ourBrow.reload();
				break;
			case R.id.bSynch:
				try{
					ia = new Intent(SimpleBrowser.this,ReadWebpageAsyncTask.class);
					startActivity(ia);
				}catch(Exception e){
					d = new Dialog(this);
					d.setTitle("did it");
					tv = new TextView(this);
					tv.setText(e.toString());
					d.setContentView(tv);
					d.show();
				}
				ia = null;
				d = null;
				tv = null;
				
				break;
		}
	}
}
