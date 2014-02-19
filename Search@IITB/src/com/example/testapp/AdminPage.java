package com.example.testapp;

import android.app.Activity;      
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class AdminPage extends Activity implements android.view.View.OnClickListener {
	EditText eTname,eTpass;
	Button insert,update,delete,sync,exportData,importData,gotoSearch;
	String name,pass;
	
	Dialog d = null;
	TextView tv1 = null;
	
	HotOrNot hon  = null;
	HotOrNot ex = null;
	
	HotOrNot extUSB = null;
	Intent ourIntent = null;
	SharedPrefs sp1 = null;
	String tabid = null;
	
	long data_2_import = 0;
	String data_2_import_err = null;
	boolean tabid_found = true;
	
	ProgressDialog pd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminpage);
		
		sp1 = new SharedPrefs();
    	tabid = sp1.sendTabId();
    			
		eTname = (EditText) findViewById(R.id.eTname);
		eTpass = (EditText) findViewById(R.id.eTpass);
		
		insert = (Button) findViewById(R.id.bAddname);
		update = (Button) findViewById(R.id.bUpdate);
		delete = (Button) findViewById(R.id.bDelete);
		
		sync = (Button) findViewById(R.id.bS);
		
		insert.setOnClickListener(this);
		update.setOnClickListener(this);
		delete.setOnClickListener(this);
		sync.setOnClickListener(this);
		
		exportData = (Button) findViewById(R.id.bExportData);
		exportData.setOnClickListener(this);
		
		importData = (Button) findViewById(R.id.bImportData);
		importData.setOnClickListener(this);

		gotoSearch = (Button) findViewById(R.id.bGotoSearch);
		gotoSearch.setOnClickListener(this);

		if(tabid == null || tabid.trim().length()<=0 || tabid.trim().equalsIgnoreCase("null"))
			tabid_found = false;
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
			case R.id.bAddname:
				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
					try{
						name  = eTname.getText().toString();
						pass = eTpass.getText().toString();
						if(pass.trim().length()>0 && name.trim().length()>0){
							
							hon  = new HotOrNot(AdminPage.this);
							hon.open();
							
							hon.createEntry1(name,pass);
							hon.close();
							
							hon = null;
							
							d = new Dialog(this);
							d.setTitle("Add User");
							tv1 = new TextView(this);			
							tv1.setText("User '"+name+"' Added.");
							d.setContentView(tv1);
							d.show();
							
							tv1 = null;
							d = null;
						}
						else{
							d = new Dialog(this);
							d.setTitle("Add User Error");
							tv1 = new TextView(this);
							tv1.setText("UserName or Password cannot be empty");
							d.setContentView(tv1);
							d.show();
							tv1 = null;
							d = null;
	
						}
					}catch(Exception e){
						//String error = e.toString();
						d = new Dialog(this);
						d.setTitle("Error in Add User");
						tv1 = new TextView(this);
						tv1.setText(e.toString());
						d.setContentView(tv1);
						d.show();
						tv1 = null;
						d = null;
					}
				}
				name = null;
				pass = null;

				break;
				
			case R.id.bUpdate:
				
				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
	
					try{
						name  = eTname.getText().toString();
						pass = eTpass.getText().toString();
						if(pass.trim().length()>0 && name.trim().length()>0 ){

							ex = new HotOrNot(this);
							ex.open();
							ex.updateEntry(name,pass);
							ex.close();
							ex = null;
							
							d = new Dialog(this);
							d.setTitle("Update Password");
							tv1 = new TextView(this);			
							tv1.setText("'"+name+"' User's Password Updated.");
							d.setContentView(tv1);
							d.show();
							
							tv1 = null;
							d = null;
	
						}
						else
						{
							d = new Dialog(this);
							d.setTitle("Update Password Error");
							tv1 = new TextView(this);
							tv1.setText("UserName or Password cannot be empty");
							d.setContentView(tv1);
							d.show();
							
							tv1 = null;
							d = null;
							
						}
					}catch(Exception e){
						//String error = e.toString();
						d = new Dialog(this);
						d.setTitle("Error in Update Password");
						tv1 = new TextView(this);
						tv1.setText(e.toString());
						d.setContentView(tv1);
						d.show();
						
						tv1 = null;
						d = null;
					}
				}
				name = null;
				pass = null;
				break;
				
			case R.id.bDelete:

				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
				
					try{
						name  = eTname.getText().toString();
						if(name.trim().length()>0 ){
							
							ex = new HotOrNot(this);
							ex.open();
							ex.deleteEntry(name);
							ex.close();
							ex = null;
							
							d = new Dialog(this);
							d.setTitle("Delete User");
							tv1 = new TextView(this);			
							tv1.setText("User '"+name+"' Deleted.");
							d.setContentView(tv1);
							d.show();
						}
						else
						{
							d = new Dialog(this);
							d.setTitle("Delete User Error");
							tv1 = new TextView(this);
							tv1.setText("UserName cannot be empty");
							d.setContentView(tv1);
							d.show();
						}						
						
						tv1 = null;
						d = null;
						name = null;
						
					}catch(Exception e){
						//String error = e.toString();
						d = new Dialog(this);
						d.setTitle("Error in Delete User");
						tv1 = new TextView(this);
						tv1.setText(e.toString());
						d.setContentView(tv1);
						d.show();
	
						tv1 = null;
						d = null;
					}
				}
				tv1 = null;
				d = null;
				name = null;
				
				break;
				
			case R.id.bS:

				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
					
					if(tabid!=null && Configuration.masterTablets.indexOf(tabid+"|")>=0)
					{
						ourIntent = new Intent(AdminPage.this,SimpleBrowser.class); 
						startActivity(ourIntent);
					
						ourIntent = null;
					}
					else
					{
						Toast.makeText(AdminPage.this, "Data Sync allowed only from Master Tablets!!!", Toast.LENGTH_LONG).show();					
					}
				}
				break;

			case R.id.bGotoSearch:
				
				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
					data_2_import = 0;			
					data_2_import_err = null;	

					hon  = new HotOrNot(AdminPage.this);
					hon.open();
					
					try{
						data_2_import_err = hon.getDataCount();
						data_2_import = Long.parseLong(data_2_import_err); 
					} 
					catch(Exception inex){
						data_2_import = -1;
					}
					hon.close();
					hon = null;
					
					if(data_2_import>0)
					{
						Toast.makeText(AdminPage.this, "Total "+data_2_import+" Records for searching.", Toast.LENGTH_LONG).show();
						
						ourIntent = new Intent(AdminPage.this,SQLiteExample.class);
						startActivity(ourIntent);
						ourIntent = null;
					}
					else
					{
						Toast.makeText(AdminPage.this, "No Records found for searching.", Toast.LENGTH_LONG).show();						
					}
					data_2_import = 0;			
					data_2_import_err = null;	
					
				}
				break;

			case R.id.bExportData:	// option hidden now

				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
					data_2_import = 0;			// its actually data_2_export
					data_2_import_err = null;	// its actually data_2_export_err
					
					if(tabid!=null && Configuration.masterTablets.indexOf(tabid+"|")>=0)
					{
						hon  = new HotOrNot(AdminPage.this);
						hon.open();
						
						try{
							data_2_import_err = hon.getDataCount();
							data_2_import = Long.parseLong(data_2_import_err); 
						} 
						catch(Exception inex){
							data_2_import = -1;
						}
						hon.close();
						hon = null;
						
						if(data_2_import>0)
						{
							backup();	// it data is synced (ie. data exists) then only backup to be done.
						}
						else
						{
							if(data_2_import==0)
							{
								Toast.makeText(AdminPage.this, "No Data found for Export. Please Sync Data first!!!", (Toast.LENGTH_LONG)+1).show();
							}
							else
								Toast.makeText(AdminPage.this, "Export Error - "+data_2_import_err, (Toast.LENGTH_LONG)+1).show();								
						}
						
					}
					else
						Toast.makeText(AdminPage.this, "Data Export allowed only from Master Tablets!!!", Toast.LENGTH_LONG).show();

					data_2_import = 0;			// its actually data_2_export
					data_2_import_err = null;	// its actually data_2_export_err
				}
				
				break;
				
			case R.id.bImportData:

				if(!tabid_found)
				{
					Toast.makeText(AdminPage.this, "Tab-ID not found. Please Restart application!!!", Toast.LENGTH_LONG).show();					
				}
				else
				{				
					
				
					boolean proceed_to_Import = false;
					File usbfile=null,usbdummyFile = null,usbDBFile = null, sdcardDBFile = null;
					FileWriter fw = null;
					BufferedWriter bw = null;	
					FileOutputStream fos = null;
					data_2_import = 0;
					data_2_import_err = null;
					pd = null;
					

					proceed_to_Import = true;
					
					if(proceed_to_Import)
					{				
						
						try
						{

							usbDBFile = new File(Configuration.usb_drive1+"/"+Configuration.appBackupFolderName+"/"+Configuration.appDATABASE_NAME);
							
							if(!usbDBFile.exists())
							{
								usbDBFile = new File(Configuration.usb_drive2+"/"+Configuration.appBackupFolderName+"/"+Configuration.appDATABASE_NAME);								
							}
							
							if(usbDBFile.exists())
							{
								
								extUSB  = new HotOrNot(AdminPage.this);
								
								extUSB.extUSB_open(usbDBFile.getAbsolutePath());
								
								
								try{
									
									data_2_import_err = extUSB.extUSB_getDataCount();
									data_2_import = Long.parseLong(data_2_import_err); 
									
								} 
								catch(Exception inex){
									data_2_import = -1;
								}
								
								
								
								if(data_2_import>0)
								{
									extUSB.open();

									data_2_import_err = null;
									data_2_import = 0;
									
									try{
										try{
											sdcardDBFile = new File(Configuration.sdcard+"/"+Configuration.appDATABASE_NAME);
										}catch(Exception innerg){}
										

										copy_FromUSB_ToSDCard(usbDBFile,sdcardDBFile.getAbsolutePath());
										
										data_2_import_err = extUSB.importData(AdminPage.this,sdcardDBFile.getAbsolutePath(),usbDBFile.getAbsolutePath());
										data_2_import = Long.parseLong(data_2_import_err); 
										
									} 
									catch(Exception inex){
										data_2_import = -1;
									}
									if(data_2_import>0)
									{
										Toast.makeText(AdminPage.this, "Import SUCCESS ( "+data_2_import+" Records Imported )", (Toast.LENGTH_LONG)+3).show();
									}
									else
									{
										Toast.makeText(AdminPage.this, "Import FAILED - "+data_2_import_err, (Toast.LENGTH_LONG)+3).show();									
									}
									extUSB.close();
								}
								else
								{
									if(data_2_import==0)
									{
										Toast.makeText(AdminPage.this, "No Data found for Import!!!", (Toast.LENGTH_LONG)+1).show();
									}
									else
										Toast.makeText(AdminPage.this, "Import Error(No Data Found) - "+data_2_import_err, (Toast.LENGTH_LONG)+1).show();								
								}
								
								extUSB.extUSB_close();
								
								
							}
							else
							{
								Toast.makeText(AdminPage.this, "Exported Data not found at (USB Pendrive)/"+(Configuration.appBackupFolderName+"/"+Configuration.appDATABASE_NAME)+" !!!", (Toast.LENGTH_LONG)+1).show();							
							}
						}
						catch(Exception ex1)
						{
							Toast.makeText(AdminPage.this, "Import Error --> "+ex1.toString(), (Toast.LENGTH_LONG)+1).show();						
							
						}
						finally
						{
							try { extUSB.close(); } catch(Exception ex2){}
							try { extUSB.extUSB_close(); } catch(Exception ex3){}
						}
					
					}
					else
					{
						Toast.makeText(AdminPage.this, "Error - USB Drive not Found ( "+Configuration.usb_drive1+" or "+Configuration.usb_drive2+" )", (Toast.LENGTH_LONG)+1).show();
						Toast.makeText(AdminPage.this, "Please insert USB PenDrive first!!!", Toast.LENGTH_LONG).show();
					}
					
					usbfile = null;
					usbdummyFile = null;
					fw = null;
					bw = null;
					fos = null;
					extUSB = null;
					usbDBFile = null;
					sdcardDBFile = null;
					proceed_to_Import = false;
					data_2_import_err = null;
					data_2_import = 0;
					pd = null;
					tv1 = null;
					d = null;
					
					try{
						sdcardDBFile = new File(Configuration.sdcard+"/"+Configuration.appDATABASE_NAME);
						if(sdcardDBFile!=null && sdcardDBFile.exists())
							sdcardDBFile.delete();
					}catch(Exception innerg)
					{
					}
					sdcardDBFile = null;
				}

				break;				
		}	// switch
	}	// public void onClick(View v)
	
	
	public boolean copy_FromUSB_ToSDCard(File usbDBFile, String SDCardFilePath) {
		boolean rc = false;
		File fileBackup = null;

		boolean writeable = isSDCardWriteable();
		if (writeable) {
			
			try
			{
				if (usbDBFile.exists()) {
					fileBackup = new File(SDCardFilePath);
					try 
					{
						if(fileBackup.exists())
						{
							fileBackup.delete();
						}
						
						fileBackup.createNewFile();

						copyFile(usbDBFile, fileBackup);
						
						rc = true;
					}
					catch (Exception ex) {
					}
					
				}
			}
			catch (Exception ex) {
				rc = false;
			}
				
			fileBackup = null;	
				
		}
		return rc;
	}
	
	
	
	public boolean backup() {
		boolean rc = false;
		String packageName = (AdminPage.this).getPackageName();

		boolean proceed_to_Copy = false;
		File file=null,usbfile=null,usbdummyFile = null,fileBackupDir=null,fileBackup = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		FileOutputStream fos = null;
		
		boolean writeable = isSDCardWriteable();
		int err_line = 0;
		if (writeable) {
			
			try
			{
				err_line = 0;
				usbfile = new File(Configuration.usb_drive1);
				if(usbfile!=null && usbfile.exists())
				{
					usbdummyFile = new File(usbfile,"zzz_test.txt");
					if (!usbdummyFile.exists()) {
						fw = new FileWriter(usbdummyFile);
						fw.write("This is for testing.");
						fw.close();

						usbdummyFile.delete();
						proceed_to_Copy = true;
					}	
					else
					{
						fw = new FileWriter(usbdummyFile);
						fw.write("This is for testing.");
						fw.close();

						usbdummyFile.delete();
						proceed_to_Copy = true;
						
					}					
				}
				else
				{
					Toast.makeText(AdminPage.this, ""+Configuration.usb_drive1+" - Not found", Toast.LENGTH_LONG).show();
				}
			}
			catch (Exception ex) {
				proceed_to_Copy = false;
				Toast.makeText(AdminPage.this, "Error for "+Configuration.usb_drive1+" ("+err_line+") - "+ex.toString(), Toast.LENGTH_LONG).show();
			}
			
			if(!proceed_to_Copy)
			{
				try
				{
					usbfile = null;
				
					usbfile = new File(Configuration.usb_drive2);
					if(usbfile!=null && usbfile.exists())
					{
						usbdummyFile = new File(usbfile,"zzz_test.txt");
						if (!usbdummyFile.exists()) {

							fw = new FileWriter(usbdummyFile);
							fw.write("This is for testing.");
							fw.close();
							
							usbdummyFile.delete();
							proceed_to_Copy = true;

						}
						else
						{
							
							fw = new FileWriter(usbdummyFile);
							fw.write("This is for testing.");
							fw.close();

							usbdummyFile.delete();
							proceed_to_Copy = true;
							
						}
					}
					else
					{
						Toast.makeText(AdminPage.this, ""+Configuration.usb_drive2+" - Not found", Toast.LENGTH_LONG).show();
					}
				}
				catch (Exception ex) {
					proceed_to_Copy = false;
					Toast.makeText(AdminPage.this, "Error for "+Configuration.usb_drive2+" - "+ex.toString(), Toast.LENGTH_LONG).show();
				}
			}
						
			if(proceed_to_Copy)
			{
				
				file = new File(Environment.getDataDirectory() + "/data/" + packageName + "/databases/" + Configuration.appDATABASE_NAME);
	
				if(file.exists())
				{

					fileBackupDir = new File(usbfile,Configuration.appBackupFolderName);
					if (!fileBackupDir.exists()) {
						fileBackupDir.mkdirs();
					}
		
					if (file.exists() && fileBackupDir.exists()) {
						fileBackup = new File(fileBackupDir, Configuration.appDATABASE_NAME);
						try 
						{
							fileBackup.createNewFile();

							copyFile(file, fileBackup);
							rc = true;
						}
						catch (Exception ex) {

							Toast.makeText(AdminPage.this, "Error in Data Export - "+ex.toString(), Toast.LENGTH_LONG).show();
						}
						
					}
				}
				else
				{
					Toast.makeText(AdminPage.this, "Data file not generated yet!!!", Toast.LENGTH_LONG).show();				
				}
				
				if(rc)
				{

					Toast.makeText(AdminPage.this, "Data Exported successfully at "+usbfile+"/"+Configuration.appBackupFolderName, (Toast.LENGTH_LONG)+2).show();
				}
				else
					Toast.makeText(AdminPage.this, "Data Exported Not Done Properly", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(AdminPage.this, "Error - USB Drive not Found ( "+Configuration.usb_drive1+" or "+Configuration.usb_drive2+" )", (Toast.LENGTH_LONG)+1).show();
				
				Toast.makeText(AdminPage.this, "Please insert USB PenDrive first!!!", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			Toast.makeText(AdminPage.this, "Please insert SD Card / USB PenDrive first!!!", Toast.LENGTH_LONG).show();		
		}
		

		fileBackupDir=null;		file=null;	usbfile = null;	usbdummyFile=null;	fileBackup = null;	
		packageName = null;		
		fos = null;
		proceed_to_Copy = false;	fw = null;	bw = null;
		
		return rc;
	}
	
	private boolean isSDCardWriteable()
	{
		boolean rc = false;
		//String state = Environment.getExternalStorageState();
		String state = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equalsIgnoreCase(state))
		{
			rc = true;
		}
		state = null;
		return rc;
	}
					
	public boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        InputStream in = null;
        try 
        {
            in = new FileInputStream(srcFile);
            try 
            {
                result = copyToFile(in, destFile);
            }
            catch (Exception ex) 
            {
            	result = false;
            }
            finally  
            {
                in.close();
            }
        } catch (Exception e) {
            result = false;
        }
        in = null;
        
        return result;
    }

    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    public boolean copyToFile(InputStream inputStream, File destFile) {
    	FileOutputStream out = null;
    	byte[] buffer = null;
    	int bytesRead;
    	try 
    	{
            if (destFile.exists()) 
            {
                destFile.delete();
            }
            out = new FileOutputStream(destFile);
            try 
            {
                buffer = new byte[4096];
                bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) >= 0) 
                {
                    out.write(buffer, 0, bytesRead);
                }
            } 
            catch(Exception ex)
            {
            	
            }
            finally 
            {
            	if(out!=null)
            		out.flush();
                try 
                {
                    out.getFD().sync();
                } 
                catch (Exception e) {
                }
                if(out!=null)
                	out.close();
            }
            buffer = null;
            out = null;
            bytesRead = 0;
            
            return true;
        } 
    	catch (Exception e) {
            buffer = null;
            out = null;
            bytesRead = 0;

    		return false;
        }
    }
	
}
