package com.example.testapp;

import java.io.ByteArrayInputStream; 

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tabs extends Activity implements OnClickListener {

	TextView names,sal,id1,address,type,gender,email,dob,valid,dept,tint,text,blood,med,mar,rel,btype,bno,broom,secu;
	EditText name,salary,id,address1,type1,gender1,email1,dob1,valid1,dept1,tint1,text1,blood1,med1,mar1,rel1,btype1,bno1,broom1,secu1;
	String gotName,gotSal,gotId,gotAdd,gotType,gotGender,gotEmail,gotDob,gotValid,gotDept,gotInt,gotExt,gotBlood,gotMed,gotMar,gotRel,gotBtype,gotBno,gotBroom,gotSecu,gotlat,gotlong;
	byte[] gotImg;
	ImageView image;
	Button next;
	
	private GoogleMap map;
	TabHost th;
	InputMethodManager imm = null;
	Bundle gotBasket = null;
	//double lat1 = 0.0;
	//double long1 = 0.0;
	ByteArrayInputStream imageStream = null;
	Bitmap theImage = null;
	Dialog d = null;
	TextView tv = null;
	Marker hamburg = null;
	
	Intent ia = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
	
		Initialize();
	
		th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
	
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Search");
		th.addTab(specs);
	
	
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Photo");
		th.addTab(specs);
	
	
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Address Map");
		th.addTab(specs);
	
		
	
	
	
		th.setOnTabChangedListener(new OnTabChangeListener(){
	
				@Override
				public void onTabChanged(String arg0) {
					// TODO Auto-generated method stub
					imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(th.getApplicationWindowToken(), 0);
					imm = null;
				}
		});
	
	}

	private void Initialize() {
		// TODO Auto-generated method stub
		
		names= (TextView)findViewById(R.id.tnName);
		name=	(EditText) findViewById(R.id.etName);
		
		address= (TextView)findViewById(R.id.tnAddress);
		address1=	(EditText) findViewById(R.id.etAddress);
		type= (TextView)findViewById(R.id.tnType);
		type1=	(EditText) findViewById(R.id.etType);
		gender= (TextView)findViewById(R.id.tnGender);
		gender1=	(EditText) findViewById(R.id.etGender);
		
		email1=	(EditText) findViewById(R.id.etEmail);
		dob1=	(EditText) findViewById(R.id.etDob);
		valid1=	(EditText) findViewById(R.id.etValid);
		dept1=	(EditText) findViewById(R.id.etDept);
		tint1=	(EditText) findViewById(R.id.etInt);
		text1=	(EditText) findViewById(R.id.etExt);
		blood1=	(EditText) findViewById(R.id.etBlood);
		med1=	(EditText) findViewById(R.id.etMed);
		mar1=	(EditText) findViewById(R.id.etMar);
		rel1=	(EditText) findViewById(R.id.etRel);
		
		secu1=	(EditText) findViewById(R.id.etSecu);
		
		email= (TextView)findViewById(R.id.tnEmail);
		dob= (TextView)findViewById(R.id.tnDob);
		valid= (TextView)findViewById(R.id.tnValid);
		dept= (TextView)findViewById(R.id.tnDept);
		tint= (TextView)findViewById(R.id.tnInt);
		text= (TextView)findViewById(R.id.tnExt);
		blood= (TextView)findViewById(R.id.tnBlood);
		med= (TextView)findViewById(R.id.tnMed);
		mar= (TextView)findViewById(R.id.tnMar);
		rel= (TextView)findViewById(R.id.tnRel);
		secu= (TextView)findViewById(R.id.tnSecu);


		next = (Button) findViewById(R.id.bnext);
		next.setOnClickListener(this);

		sal= (TextView) findViewById(R.id.tnSal);
		salary =(EditText) findViewById(R.id.etSal);
		id1= (TextView) findViewById(R.id.tnID);
		id =(EditText) findViewById(R.id.etID);

		image = (ImageView) findViewById(R.id.ivImage);

		try{

			gotBasket = getIntent().getExtras();
			
			gotName = gotBasket.getString("key");			gotSal = gotBasket.getString("key1");
			gotId = gotBasket.getString("key3");			gotAdd = gotBasket.getString("key4");
			gotType = gotBasket.getString("key5");			gotGender = gotBasket.getString("key6");
			gotEmail = gotBasket.getString("key7");			gotDob = gotBasket.getString("key8");
			gotValid = gotBasket.getString("key9");			gotInt = gotBasket.getString("key10");
			gotExt = gotBasket.getString("key11");			gotDept = gotBasket.getString("key12");
			gotBlood = gotBasket.getString("key13");		gotMed = gotBasket.getString("key14");
			gotMar = gotBasket.getString("key15");			gotRel = gotBasket.getString("key16");
			gotBtype = gotBasket.getString("key17");		gotBno = gotBasket.getString("key18");
			gotBroom = gotBasket.getString("key19");		gotSecu = gotBasket.getString("key20");
			gotlat = gotBasket.getString("key21");			gotlong = gotBasket.getString("key22");

			gotlat = (gotlat==null || gotlat.trim().length()<=0 || gotlat.equalsIgnoreCase("-") ) ?Configuration.default_latitude:gotlat;
			gotlong = (gotlong==null || gotlong.trim().length()<=0 || gotlong.equalsIgnoreCase("-") ) ?Configuration.default_longitude:gotlong;			
			

			name.setText(gotName==null?"":gotName);			salary.setText(gotSal==null?"":gotSal);			
			id.setText(gotId==null?"":gotId);				
			
			if(gotBtype==null || gotBtype.trim().length()<=0 || gotBtype.equalsIgnoreCase("-") || gotBno==null || gotBno.trim().length()<=0 || gotBno.equalsIgnoreCase("-") )
				address1.setText(gotAdd==null?"":gotAdd);
			else
			{
				if(gotBtype.toUpperCase().indexOf("HOSTEL")>=0)
					address1.setText(gotBtype+" - "+gotBno+", RoomNo - "+gotBroom+".");
				else
					address1.setText(gotBtype+",  BldgNo -"+gotBno+",  RoomNo - "+gotBroom+".");
			}
			
			type1.setText(gotType==null?"":gotType);		gender1.setText(gotGender==null?"":gotGender);
			email1.setText(gotEmail==null?"":gotEmail);		dob1.setText(gotDob==null?"":gotDob);			
			valid1.setText(gotValid==null?"":gotValid);		tint1.setText(gotInt==null?"":gotInt);			
			text1.setText(gotExt==null?"":gotExt);			dept1.setText(gotDept==null?"":gotDept);
			blood1.setText(gotBlood==null?"":gotBlood);		med1.setText(gotMed==null?"":gotMed);			
			mar1.setText(gotMar==null?"":gotMar);			rel1.setText(gotRel==null?"":gotRel);			
			secu1.setText(gotSecu==null?"":gotSecu);


			try
			{
				gotImg = gotBasket.getByteArray("key2");
				
				imageStream = new ByteArrayInputStream(gotImg);
				theImage= BitmapFactory.decodeStream(imageStream);
				
				image.setImageBitmap(theImage);
			}
			catch(Exception e1)
			{
				d = new Dialog(this);
				d.setTitle("image-exception in Tabs.initialize() ");
				tv = new TextView(this);
				tv.setText(e1.toString());
				d.setContentView(tv);
				d.show();
				d = null;
				tv = null;
			}		

			try
			{
				// 	lat1 = Double.parseDouble(gotlat);
				// 	long1 = Double.parseDouble(gotlong);
				
				// 	long lat = Long.parseLong(gotlat);
				// 	long lon = Long.parseLong(gotlong);
				
				//	final LatLng HAMBURG = new LatLng(lat1, long1);
				
				//  final LatLng KIEL = new LatLng(53.551, 9.993);
				
				final LatLng HAMBURG = new LatLng(Double.parseDouble(gotlat), Double.parseDouble(gotlong));
				
				map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

				if(gotBtype==null || gotBtype.trim().length()<=0 || gotBtype.equalsIgnoreCase("-") || gotBno==null || gotBno.trim().length()<=0 || gotBno.equalsIgnoreCase("-") )
					hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title(gotName).snippet(gotAdd));
				else
					hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title(gotName).snippet(gotBtype+" / "+gotBno+" / "+gotBroom));
				
				//Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
	
				// Move the camera instantly to hamburg with a zoom of 15.
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 17));
				
				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
			}
			catch(Exception e2)
			{
				//Toast.makeText(Tabs.this, "Unable to set Location on Google Map!!! "+e2.toString(), Toast.LENGTH_LONG).show();
				Toast.makeText(Tabs.this, "Unable to set Map Location. Check or Install Google Play Service!!!", Toast.LENGTH_LONG).show();
				
				//th.getTabWidget().getChildTabViewAt(th.getTabWidget().getChildCount()-1).setEnabled(false);
			}
		}
		catch(Exception e){
			//String error = e.toString();
			d = new Dialog(this);
			d.setTitle("Did it (Tabs.Initialize()) ");
			tv = new TextView(this);
			tv.setText(e.toString());
			d.setContentView(tv);
			d.show();
			d = null;
			tv = null;
		}
		
		theImage = null;
		imageStream = null;

		gotBasket = null;
		hamburg = null;
		
		
		gotBasket = null;
			
		gotName = null;			gotSal = null;
		gotId = null;			gotAdd = null;
		gotType = null;			gotGender = null;
		gotEmail = null;		gotDob = null;
		gotValid = null;		gotInt = null;
		gotExt = null;			gotDept = null;
		gotBlood = null;		gotMed = null;
		gotMar = null;			gotRel = null;
		gotBtype = null;		gotBno = null;
		gotBroom = null;		gotSecu = null;
		gotlat = null;			gotlong = null;

		gotImg = null;
					
		
	}	//private void Initialize() {


	@Override
	public void onClick(View arg0) {	// Next Search Button Clicked
	// TODO Auto-generated method stub
	
		ia = new Intent(Tabs.this,SQLiteExample.class);
		startActivity(ia);
		
		ia = null;
	
	}

}