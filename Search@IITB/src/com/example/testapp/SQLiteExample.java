package com.example.testapp;

import java.io.ByteArrayInputStream;  


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class SQLiteExample extends Activity implements OnClickListener,OnCheckedChangeListener {

	Button sqlUpdate,sqlView,sqlgetinfo,reset;
	EditText sqlName,sqlSal,sqlRow,sqlId,sqlBtype,sqlBno,sqlBroom;
	String name,id,btype,bno,broom ;
	String salary,setData="";
	RadioGroup selectionList;
	public String date1;

	Date d1 ;
	
	Dialog d = null;
	TextView tv = null;
	SQLView sv = null;
	Intent i = null;
	HotOrNot dget = null;
	SimpleDateFormat sdf = null;
	Date date2 = null;
	
	long diff = 0,seconds = 0, minutes = 0, hours = 0, days = 0;
	boolean diw = true, proceed_to_search = false;
	ByteArrayOutputStream baos = null;
	Bitmap bitmap = null;
	byte[] image = null;
	HotOrNot entry  = null;	
	
	String s = null;
	long l = 0;
	HotOrNot hon = null;
	Bundle basket = null;
	Intent a = null;
	
	long datacount = 0;
	String datacount_err = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqliteexample);

		sqlView = (Button) findViewById(R.id.bsqlopenview);
		reset = (Button) findViewById(R.id.breset);
		sqlUpdate =(Button) findViewById(R.id.bsqlupdate);		// this is never shown and never used.  ( sqliteexample.xml --> android:visibility="invisible" )
		sqlgetinfo = (Button) findViewById(R.id.bgetinfo);		// this is never shown and never used.
		
		
		sqlId = (EditText) findViewById(R.id.etsqlid);
		sqlName = (EditText) findViewById(R.id.etsqlname);
		sqlSal = (EditText) findViewById(R.id.etsqlsal);		// salutation

		sqlBtype = (EditText) findViewById(R.id.etsqlbldtype);
		sqlBno = (EditText) findViewById(R.id.etsqlno);
		sqlBroom = (EditText) findViewById(R.id.etsqlroom);
		
		sqlRow = (EditText) findViewById(R.id.etsqlrowinfo);	// this is never shown and never used.
		
		
		sqlView.setOnClickListener(this);
		reset.setOnClickListener(this);

		sqlUpdate.setOnClickListener(this);						// this is never shown and never used.
		sqlgetinfo.setOnClickListener(this);					// this is never shown and never used.
		
		selectionList = (RadioGroup) findViewById(R.id.rgAnswers);
		selectionList.setOnCheckedChangeListener(this);
		
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){
			case R.id.bsqlopenview:
				proceed_to_search = false;
				sv = null;
		 		i = null;
		 		dget = null;
		 		sdf = null;
		 		date2 = null;
		 		
		 		diff = 0;seconds = 0; minutes = 0; hours = 0; days = 0;
				 
				try{
					name  = sqlName.getText().toString();
					salary = sqlSal.getText().toString();
					id = sqlId.getText().toString();
					btype = sqlBtype.getText().toString();
					bno = sqlBno.getText().toString();
					broom = sqlBroom.getText().toString();
					
		
					if(name.trim().length()<=2 && salary.trim().length()<=2 && id.trim().length()<=2 && btype.trim().length()<=0 && bno.trim().length()<=0 && broom.trim().length()<=0){
						d = new Dialog(this);
						d.setTitle("Search Error");
						tv = new TextView(this);
						tv.setText("Min 3 Characters reqd in Any field!!!");
						d.setContentView(tv);
						d.show();
					}
					else
					{
						if(name.trim().length()>=3)
							proceed_to_search = true;
						if(salary.trim().length()>=3)
							proceed_to_search = true;
						if(id.trim().length()>=3)
							proceed_to_search = true;

						if(btype.trim().length()>=1 && ( bno.trim().length()>=1 || broom.trim().length()>=1 ) )
							proceed_to_search = true;
						else
						{
							if(btype.trim().length()>=3)
								proceed_to_search = true;
							if(bno.trim().length()>=3)
								proceed_to_search = true;
							if(broom.trim().length()>=3)
								proceed_to_search = true;
						}
						
						if(!proceed_to_search)
						{
							Toast.makeText(SQLiteExample.this, "Please Check Search String!!!", Toast.LENGTH_LONG).show();
						}
						else
						{
							
							dget = new HotOrNot(SQLiteExample.this);
							dget.open();
	
							
							try{
								datacount_err = dget.getDataCount();
								datacount = Long.parseLong(datacount_err); 
							} 
							catch(Exception inex){
								datacount = -1;
							}
							
							if(datacount <=0)
							{
								if(datacount==0)
								{
									Toast.makeText(SQLiteExample.this, "Blank Data. Please Sync / Import Data first!!!", (Toast.LENGTH_LONG)+1).show();
								}
								else
									Toast.makeText(SQLiteExample.this, "Blank Data Error - "+datacount_err, (Toast.LENGTH_LONG)+1).show();								
							}
							else
							{
								
								try
								{
									l=1;
									dget.deleteEntry2(1);	// deletes Row_ID=1 from peopleTable. --- here not record will be deleted from PeopleTable.
								}
								catch(Exception e)
								{
									l++;
								}
								finally
								{
									if(l==1)
									{
										date1= dget.getDate();
									}
									else
									{
										date1= dget.getDate();
									}
								}
				
								//	date1= dget.getDate();
								dget.close();
				
								try{
									sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy"); // here set the pattern as you date in string was containing like date/month/year
									d1 = sdf.parse(date1);
								//}catch(ParseException ex){
								}catch(Exception ex){
									//Dialog d = new Dialog(this);
									//d.setTitle("did it inside");
									//TextView tv = new TextView(this);
									//tv.setText(ex.toString());
									//d.setContentView(tv);
									//d.show();
									// handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
								}
		 			
								try
								{
									date2 = new Date();
							 
									diff = date2.getTime() - d1.getTime();
									seconds = diff / 1000;
									minutes = seconds / 60;
									hours = minutes / 60;
									days = hours / 24;
									
								}catch(Exception ex){
									days = 0;
								}
								
								//if( ( days>Configuration.max_sync_interval) && Configuration.Sync_check_reqd)
								if(Configuration.Sync_check_reqd  && (days > Configuration.max_sync_interval) ) {
		
										d = new Dialog(this);
										d.setTitle("Data Too OLD Error");
										tv = new TextView(this);
										tv.setText("Cannot proceed without Data Import!!!.");
										d.setContentView(tv);
										d.show();
										
								} else{
									
									sv = new SQLView();
									sv.send(id,name,salary,btype,bno,broom,setData);	// displays search result with Record-No in right side.

									i = new Intent("com.example.testapp.SQLVIEW");
									startActivity(i);	 
									sv = null;
									
								}
							
							}
						}
					} 
		 		}catch(Exception e){

		 			d = new Dialog(this);
		 			d.setTitle("did it");
		 			tv = new TextView(this);
		 			tv.setText(e.toString());
		 			d.setContentView(tv);
		 			d.show();
		 		}
		 		
		 		d = null;
		 		tv = null;
		 		sv = null;
		 		i = null;
		 		dget = null;
		 		sdf = null;
		 		date2 = null;
		 		
		 		diff = 0;seconds = 0; minutes = 0; hours = 0; days = 0;
		 		
		 		//name  = null;
				//salary = null;
				//id = null;
				//btype = null;
				//bno = null;
				//broom = null;
				//setData = null;
				
		 		break;
			 
			case R.id.bsqlupdate:	// this option is hidden ( from Layout XML file )
				diw= true;
				baos = null;
		 		bitmap = null;
		 		image = null;
		 		entry  = null;
		 		try{
		 			
		 			name  = sqlName.getText().toString();
		 			salary = sqlSal.getText().toString();
		 			id = sqlId.getText().toString();
					
		 			baos = new ByteArrayOutputStream();  
		 			bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		 			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
		 			image = baos.toByteArray();
		 
		 			entry  = new HotOrNot(SQLiteExample.this);
		 			entry.open();
		 			//	 entry.createEntry(id,name,salary,image);
		 			entry.close();
		 		
		 		}catch(Exception e){
		 			
		 			diw=false;
		 			d = new Dialog(this);
		 			d.setTitle("error");
		 			tv = new TextView(this);
		 			tv.setText(e.toString());
		 			d.setContentView(tv);
		 			d.show();
				 
		 		}finally{
		 			if(diw){
		 				
		 				d = new Dialog(this);
		 				d.setTitle("did it");
		 				tv = new TextView(this);
		 				tv.setText("success");
		 				d.setContentView(tv);
		 				d.show();
		 				
		 			}
		 		}
		 		diw = false;
		 		d = null;
		 		tv = null;
		 		baos = null;
		 		bitmap = null;
		 		image = null;
		 		entry  = null;
		 		
		 		//name  = null;
	 			//salary = null;
	 			//id = null;
		 		
		 		break;
		
		 	case R.id.bgetinfo:
		 		s = null;
		 		l = 0;
		 		hon = null;
		 		basket = null;
		 		a = null;
		 		try{
		 			s = sqlRow.getText().toString();
		 			l = Long.parseLong(s);
		 			name  = sqlName.getText().toString();
		 			
		 			hon = new HotOrNot(this);
		 			hon.open();
		 			//String returnedName = hon.getName(l);
		 			//String returnedSalary	= hon.getSalary(l);
		 			//byte[] image = hon.getImage(l);
		 			//String returnedName = hon.getName(name);
		 			//String returnedSalary	= hon.getSalary(name);
		 			hon.close();
			 
		 			basket = new Bundle();
		 			//basket.putString("key", returnedName);
		 			//basket.putString("key1", returnedSalary);
		 			//basket.putByteArray("key2", image);
		 			a = new Intent(SQLiteExample.this,Tabs.class);
		 			a.putExtras(basket);
		 			startActivity(a);
		 			
		 		}catch(Exception e){
		 			
		 			d = new Dialog(this);
		 			d.setTitle("errr in eg");
		 			tv = new TextView(this);
		 			tv.setText(e.toString());
		 			d.setContentView(tv);
		 			d.show();
		 			
		 		}
		 		
		 		
		 		s = null;
		 		l = 0;
		 		hon = null;
		 		basket = null;
		 		a = null;
		 		d = null;
		 		tv = null;
		 		//name = null;
		 		
		 		break;
		 		
		 	case R.id.breset:
		 		
		 		sqlId.setText("");
		 		sqlName.setText("");
		 		sqlSal.setText("");
		 		sqlBtype.setText("");
		 		sqlBno.setText("");
		 		sqlBroom.setText("");
		 		
		 		selectionList.clearCheck();
		 		
		 		setData="";
		 		
		 		break;
		 		
		}	// switch
	} // onClick()

//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		date1 = savedInstanceState.getString("date");
//	}
//
//	@Override
//	protected void onSaveInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(savedInstanceState);
//		
//		savedInstanceState.putString("date", date1);
//	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
			case R.id.rFac:
				setData = "Faculty";
				break;
			case R.id.rStaff:
				setData = "Staff";
				break;
			case R.id.rStud:
				setData = "Student";
				break;
			case R.id.rFamily:
				setData = "Family";
				break;				
		}
		
	}

//	public void passDate(String date) {
//		// TODO Auto-generated method stub
//		date1=date;
//	}

	
}