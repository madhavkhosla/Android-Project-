package com.example.testapp;

import android.app.Activity; 
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity{
	MediaPlayer oursong = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		
		
		//oursong = MediaPlayer.create(Splash.this, R.raw.splashsound);
		//oursong.start();
		
		
		
		
			Thread timer = new Thread(){
			
			public void run(){
				
				try{
				sleep(5000);
				}
				
				catch(InterruptedException e){
				e.printStackTrace();
				
				} 
				finally{
					Intent openStartingPoint = new Intent("com.example.testapp.SHAREDPREFS");
					startActivity(openStartingPoint);
					
				}
				}
			};
			
			timer.start();
		}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//oursong.release();
		finish();
	}
	
}



