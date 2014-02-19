package com.example.testapp;

import com.example.testapp.R.id;     

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserAuth extends Activity implements OnClickListener {

	EditText eTname,eTpass;
	Button check,admin;
	String name = null,pass = null,storedpass = null,tabid = null;
	TextView tab=null,tv=null;
	Dialog d = null;
	SharedPrefs sp1 = null;
	
	HotOrNot h1= null, h2 = null, hon = null;
	boolean addAdminUser = false;
	boolean adminUserFound = false;
	boolean isAdminUser_trying = false;
	long adminuser_added = 0,searchuser_added = 0;
	
	boolean tabid_found = true;
	
	Intent a = null, ia = null;
	MenuInflater blowUp = null;
	
	public static int count=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userauth);
		eTname = (EditText) findViewById(R.id.eTname);
		eTpass = (EditText) findViewById(R.id.eTpass);
		check = (Button) findViewById(R.id.bauth);
		//update = (Button) findViewById(R.id.bAddname);
		admin = (Button) findViewById(R.id.bAdminLogin);
		tab = (TextView) findViewById(R.id.tabId);
		
		sp1 = new SharedPrefs();
    	tabid = sp1.sendTabId();
    	tab.setText("Search App. Tab-Id : "+tabid + " ( "+Configuration.live_or_test+" )");
		//update.setOnClickListener(this);
		check.setOnClickListener(this);
		admin.setOnClickListener(this);
		
		if(tabid == null || tabid.trim().length()<=0 || tabid.trim().equalsIgnoreCase("null"))
			tabid_found = false;
		
		tabid = null;
		sp1 = null;
	
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){

			case R.id.bauth:
				h1= null;
				storedpass = null;
				a = null;
				
				if(!tabid_found)
				{
					Toast.makeText(UserAuth.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{
					try{
						name  = eTname.getText().toString();
						pass = eTpass.getText().toString();
						h1 = new HotOrNot(UserAuth.this);
						h1.open();
						
						storedpass = h1.getName1(name);
						if(storedpass != null && pass.equals(storedpass))
						{
							eTpass.setText("");
							Toast.makeText(UserAuth.this, "Congrats: Login Successfull for Searching.", Toast.LENGTH_LONG).show();
							a = new Intent(UserAuth.this,SQLiteExample.class);
							startActivity(a);
							a = null;
						}
						else
						{
							Toast.makeText(UserAuth.this, "Invalid User Name or Password!!!", Toast.LENGTH_LONG).show();
						}
					}catch(Exception e){
	
						d = new Dialog(this);
						d.setTitle("Error");
						tv = new TextView(this);
						tv.setText("Wrong username or password");
						d.setContentView(tv);
						d.show();
					}
				}
				d = null;
				tv = null;
				
				storedpass = null;
				h1 = null;
				a = null;
				pass = null;
				name = null;
				
				break;

			case R.id.bAdminLogin:
				h2 = null; 
				storedpass = null;
				ia = null;
				isAdminUser_trying = false;

				if(!tabid_found)
				{
					Toast.makeText(UserAuth.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{
					
					try{
					
						//name="admin";	// always admin user only allowed to login in Admin Interface.
						name  = eTname.getText().toString();
						
						if(name!=null && name.equals("admin"))
							isAdminUser_trying = true;
						
						if(!isAdminUser_trying)
						{
							Toast.makeText(UserAuth.this, "Admin Interface Access Restricted to Admin Users only!!!", Toast.LENGTH_LONG).show();						
						}
						else
						{
							pass = eTpass.getText().toString();
							
							h2= new HotOrNot(UserAuth.this);
							h2.open();
							
							try{
								storedpass = h2.getName1(name);
								if(storedpass!=null && storedpass.trim().length()>0)
									adminUserFound = true;
							}
							catch(Exception e){
								Toast.makeText(UserAuth.this, "Attempt", Toast.LENGTH_LONG).show();
							}
							if(count==1){
								//storedpass="iitbs";
								storedpass = Configuration.firstAdminLoginPasswd;
								count++;
								addAdminUser = true;
							}
							
							if(storedpass != null && pass.equals(storedpass))	// only for First time admin login
							{
								Toast.makeText(UserAuth.this, "Congrats: Admin Login Successfull", Toast.LENGTH_LONG).show();
		
								if(addAdminUser && !adminUserFound)
								{
									hon  = new HotOrNot(UserAuth.this);
									hon.open();
									
									//adminuser_added = hon.createEntry1("admin","admin");	// admin user added with 'admin' password.
									searchuser_added = hon.createEntry1(Configuration.securitySearchUser,Configuration.securitySearchUserPasswd);	// security user(for search) added
	
									if(Configuration.live_or_test.equalsIgnoreCase("live"))
										adminuser_added = hon.createEntry1("admin",Configuration.securityAdminPasswd_live);	// admin user added with 'admin' password.
									else
										adminuser_added = hon.createEntry1("admin",Configuration.securityAdminPasswd_test);	// admin user added with 'admin' password.
									
									hon.close();
									
									hon = null;
									
									if(adminuser_added>0)
										Toast.makeText(UserAuth.this, "User 'admin' added automatically.", Toast.LENGTH_LONG).show();
									else
										Toast.makeText(UserAuth.this, "User 'admin' Not added. Add it manually!!!", Toast.LENGTH_LONG).show();
									
									if(searchuser_added>0)
										Toast.makeText(UserAuth.this, "Search User 'security' added automatically.", Toast.LENGTH_LONG).show();
									else
										Toast.makeText(UserAuth.this, "Search User 'security' Not added. Add it manually!!!", Toast.LENGTH_LONG).show();							
									
									count++;
		
								}
								eTpass.setText("");
								
								ia = new Intent(UserAuth.this,AdminPage.class);
								startActivity(ia);
							}
							else
							{
								Toast.makeText(UserAuth.this, "Invalid User Name or Password!!!", Toast.LENGTH_LONG).show();
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
				}
				d= null;
				tv = null;
				ia = null;
				storedpass = null;
				h2 = null;
				pass = null;
				name = null;
				break;

		} // switch
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);
		blowUp = null;
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		
			case R.id.aboutUs:
				d = new Dialog(this);
				d.setTitle("Contact Us");
				tv = new TextView(this);
				tv.setText("Email : asc.help@iitb.ac.in");
				d.setContentView(tv);
				d.show();
			 
				d = null;
				tv = null;
			 
				break;
		}
		return false;
	}
}
