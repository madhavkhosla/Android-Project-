
package com.example.testapp;

import java.util.ArrayList;     
import java.util.Vector;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SQLView extends Activity {
	String[] a = null ;
	TextView tv = null;
	ListView lv = null;
	static String naam,sal,id,btype,bno,broom,setData;
	
	
	
	int index = 0;
	String cut = null;	
	
	byte[] image = null;
	Bundle basket = null;
	Intent ia = null;
	
	String returnedName = null,	returnedSalary	= null,	returnedId	= null,		returnedaddress =  null;
	String returnedtype = null,	returnedgender= null,	returnedEmail	= null,	returnedDob	= null;
	String returnedValid = null,returnedInt = null,		returnedExt = null,		returnedDept	= null;
	String returnedBlood = null,returnedMed = null,		returnedMar = null,		returnedRelation = null;
	
	String returnedBtype= null,returnedBno	= null,		returnedBroom = null,	returnedsecu = null;
	String returnedlat =  null,returnedlong= null;		
	
	Vector fullRecord_Vector = null;
	
	String full_values = null;
	
	Dialog d = null;
	TextView tv1 = null;
	
	String data = null;
	ArrayAdapter<String> adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		tv = (TextView) findViewById(R.id.tvSQLinfo);
		//ListView lv = (ListView) findViewById(R.id.lvarray);
		lv = (ListView) findViewById(R.id.lvarray);
		HotOrNot info = new HotOrNot(this);

		try{
			//naam="madhav";
			//sal="10";
			info.open();
			data = info.getData(id,naam,sal,btype,bno,broom,setData);
			info.close();
			
			a = data.split("\\\n");	
			data = null;
			info = null;
			
			//		String first = a[0];
			//		String sec = a[1];
			//		String[] b = {first,sec};
			//ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,a);
			adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,a);
			lv.setAdapter(adapter);
			adapter = null;
			
			final HotOrNot hon = new HotOrNot(this);
			
			lv.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> av, View v,int position, long id) 
				{

					index = a[position].indexOf(" ",2);
					cut = a[position].substring(0,index);
					tv.setText(cut);
					
					image = null;	basket = null;		ia = null;
					
					returnedName = null;	returnedSalary	= null;	returnedId	= null;		returnedaddress =  null;
					returnedtype = null;	returnedgender= null;	returnedEmail	= null;	returnedDob	= null;
					returnedValid = null;	returnedInt = null;		returnedExt = null;		returnedDept	= null;
					returnedBlood = null;	returnedMed = null;		returnedMar = null;		returnedRelation = null;
					
					returnedBtype= null;	returnedBno	= null;		returnedBroom = null;	returnedsecu = null;
					returnedlat =  null;	returnedlong= null;	
					
					fullRecord_Vector = null;
					full_values = null;
										
					try{
						// long l = Long.parseLong(cut);

						hon.open();
											

						
						fullRecord_Vector = hon.getFullRecord(cut);
						
						returnedId		= (String)fullRecord_Vector.elementAt(0);
						returnedName 	= (String)fullRecord_Vector.elementAt(1);
						returnedSalary	= (String)fullRecord_Vector.elementAt(2);
						returnedaddress = (String)fullRecord_Vector.elementAt(3);
						returnedtype	= (String)fullRecord_Vector.elementAt(4);	
						returnedgender	= (String)fullRecord_Vector.elementAt(5);
						returnedEmail	= (String)fullRecord_Vector.elementAt(6);	
						returnedDob		= (String)fullRecord_Vector.elementAt(7);
						returnedValid 	= (String)fullRecord_Vector.elementAt(8);	
						returnedInt		= (String)fullRecord_Vector.elementAt(9);
						returnedExt		= (String)fullRecord_Vector.elementAt(10);		
						returnedDept	= (String)fullRecord_Vector.elementAt(11);
						returnedBlood	= (String)fullRecord_Vector.elementAt(12);	
						returnedMed 	= (String)fullRecord_Vector.elementAt(13);
						returnedMar		= (String)fullRecord_Vector.elementAt(14);	
						returnedRelation= (String)fullRecord_Vector.elementAt(15);
						returnedBtype	= (String)fullRecord_Vector.elementAt(16);	
						returnedBno		= (String)fullRecord_Vector.elementAt(17);
						returnedBroom 	= (String)fullRecord_Vector.elementAt(18);	
						returnedsecu	= (String)fullRecord_Vector.elementAt(19);
						returnedlat 	= (String)fullRecord_Vector.elementAt(20);		
						returnedlong	= (String)fullRecord_Vector.elementAt(21);
						
						try{	image = (byte[])fullRecord_Vector.elementAt(22); } catch(Exception ie){}
					
						
						hon.close();
						
						basket = new Bundle();
						
						basket.putString("key", returnedName==null?"":returnedName);		
						basket.putString("key1", returnedSalary==null?"":returnedSalary);
						basket.putString("key3", returnedId==null?"":returnedId);		
						basket.putString("key4", returnedaddress==null?"":returnedaddress);
						basket.putString("key5", returnedtype==null?"":returnedtype);		
						basket.putString("key6", returnedgender==null?"":returnedgender);
						
						basket.putString("key7", returnedEmail==null?"":returnedEmail);	
						basket.putString("key8", returnedDob==null?"":returnedDob);
						basket.putString("key9", returnedValid==null?"":returnedValid);	
						basket.putString("key10", returnedInt==null?"":returnedInt);
						basket.putString("key11", returnedExt==null?"":returnedExt);		
						basket.putString("key12", returnedDept==null?"":returnedDept);
						basket.putString("key13", returnedBlood==null?"":returnedBlood);	
						basket.putString("key14", returnedMed==null?"":returnedMed);
						basket.putString("key15", returnedMar==null?"":returnedMar);		
						basket.putString("key16", returnedRelation==null?"":returnedRelation);
						
						basket.putString("key17", returnedBtype==null?"":returnedBtype);	
						basket.putString("key18", returnedBno==null?"":returnedBno);
						basket.putString("key19", returnedBroom==null?"":returnedBroom);	
						basket.putString("key20", returnedsecu==null?"":returnedsecu);
						
						basket.putString("key21", (returnedlat==null || returnedlat.trim().length()<=0 || returnedlat.equalsIgnoreCase("-") ) ?Configuration.default_latitude:returnedlat);		
						basket.putString("key22", (returnedlong==null || returnedlong.trim().length()<=0 || returnedlong.equalsIgnoreCase("-") )?Configuration.default_longitude:returnedlong);
						
						try{	basket.putByteArray("key2", image); } catch(Exception ie){}
						
						ia = new Intent(SQLView.this,Tabs.class);
						ia.putExtras(basket);
						startActivity(ia);
						
					}catch(Exception e){
						e.printStackTrace();
						Toast.makeText(getBaseContext(), "Click - "+e.toString(), Toast.LENGTH_LONG).show();
					}

					
					
					ia = null;
					basket = null;
					image = null;
					cut = null;
					index = 0;
					
					returnedName = null;	returnedSalary	= null;	returnedId	= null;		returnedaddress =  null;
					returnedtype = null;	returnedgender= null;	returnedEmail	= null;	returnedDob	= null;
					returnedValid = null;	returnedInt = null;		returnedExt = null;		returnedDept	= null;
					returnedBlood = null;	returnedMed = null;		returnedMar = null;		returnedRelation = null;
					
					returnedBtype= null;	returnedBno	= null;		returnedBroom = null;	returnedsecu = null;
					returnedlat =  null;	returnedlong= null;	
					
					fullRecord_Vector = null;
					full_values = null;
					
				}
			});
			//tv.setText(a[1]);

		}catch(Exception e){

			d = new Dialog(this);
			d.setTitle("did it once");
			tv1 = new TextView(this);
			tv1.setText(e.toString());
			d.setContentView(tv1);
			d.show();
			
			d = null;
			tv1 = null;
		}
		
		
		
	}
	
	public void send(String id2,String name2, String salary,String btype2, String bno2, String broom2,String setData2) {
		// TODO Auto-generated method stub
		id = null;
		naam= null;
		sal= null;
		btype=null;
		bno=null;
		broom=null;
		setData= null;
		
		id = id2;
		naam= name2;
		sal= salary;
		btype=btype2;
		bno=bno2;
		broom=broom2;
		setData= setData2;
	}
}
