package com.example.testapp;

import java.util.Date;   
import java.util.Vector; 

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class HotOrNot {

	public static final String KEY_ROWID = "row_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_LNAME = "persons_lastname";
	public static final String KEY_ADDRESS = "persons_address";
	public static final String KEY_TYPE = "persons_type";
	public static final String KEY_GENDER = "persons_gender";
	
	public static final String KEY_EMAIL = "persons_email";
	public static final String KEY_DOB = "persons_dob";
	public static final String KEY_VALID = "persons_valid";
	public static final String KEY_TELINT = "persons_telint";
	public static final String KEY_TELEXT = "persons_telext";

	public static final String KEY_DEPT = "persons_dept";
	public static final String KEY_BLOOD = "persons_blood";
	public static final String KEY_MED = "persons_med";
	public static final String KEY_MAR = "persons_mar";
	public static final String KEY_RELATION = "persons_relation";

	public static final String KEY_BLDTYPE = "persons_bldtype";
	public static final String KEY_BLDNO = "persons_bldno";
	public static final String KEY_BLDROOM = "persons_bldroom";
	public static final String KEY_SECU = "persons_secu";
	
	public static final String LATITUDE = "persons_lat";
	public static final String LONGITUDE = "persons_long";

	public static final String DATE = "sync_date";
	
	public static final String KEY_IMAGE = "image";
	public static final String KEY_PASSWORD = "persons_password";
	
	//private static final String DATABASE_NAME = "HotOrNotdb";
	private static final String DATABASE_NAME = Configuration.appDATABASE_NAME;
	private static final String DATABASE_TABLE = "peopleTable";
	private static final String DATABASE_TABLE_AUTH = "userTable";
	private static final String DATABASE_TABLE_DATE = "syncdate";
	
	ContentValues cv = null;
	ContentValues cvUpdate = null;
	String[] columns = null;
	Cursor c = null;
	
	String result = null;
	int result_count = 0,SearchRecord_counter = 0;
	
	

	// iSal --> Surname (Salutation) Column Index.
	
	int iRow = 0,iName = 0,iSal = 0,blob = 0,iType = 0,iNo = 0,iRoom = 0;
	String	name = null,password = null, sal = null;	
	

	byte[] img = null;
	String id = null,add = null, type = null, gender = null, email = null, dob = null;
	String valid = null, tint = null, text = null, dept = null, blood = null, med = null;
	String mar = null, rel = null, btype = null, bno = null, broom = null, secu = null;
	String date = null, latitude_1 = null, longitude_1=null;	
	Vector fullRecord_Vector = null;
	
	String datacount = null, importDate = null;
	boolean proceed_to_import = true, try_second_import_method = false, try_third_import_method = false;
	long insertRecCount = 0,totalInsertRecCount = 0;
	
	String searchCondition = null,searchValues_params;
	String[] searchValues = null;
	
	
	SQLiteStatement stmt = null;
	String sql = null;
	int j = 1;
	
	private static final int DATABASE_VERSION = 34;
	
	private DbHelper ourHelper;
	//private Ext_USBDbHelper extUSB_ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase; 
	private SQLiteDatabase extUSB_ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{
	
		public DbHelper(Context context) {
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
			db.execSQL( "CREATE TABLE " + DATABASE_TABLE +"(" +
				KEY_ROWID + " TEXT PRIMARY KEY , " +
				KEY_NAME + " TEXT NOT NULL, " +
				KEY_LNAME + " TEXT NOT NULL, " +
				KEY_ADDRESS + " TEXT NOT NULL, " +
				KEY_TYPE + " TEXT NOT NULL, " +
				KEY_GENDER + " TEXT NOT NULL, " +
				KEY_IMAGE + " BLOB NOT NULL, " +
				KEY_EMAIL + " TEXT NOT NULL, " +
				KEY_DOB + " TEXT NOT NULL, " +
				KEY_VALID + " TEXT NOT NULL, " +
				KEY_TELINT + " TEXT NOT NULL, " +
				KEY_TELEXT + " TEXT NOT NULL, " +
				KEY_DEPT + " TEXT NOT NULL, " +
				KEY_BLOOD + " TEXT NOT NULL, " +
				KEY_MED + " TEXT NOT NULL, " +
				KEY_MAR + " TEXT NOT NULL, " +
				KEY_RELATION + " TEXT NOT NULL, " +
				KEY_BLDTYPE + " TEXT NOT NULL, " +
				KEY_BLDNO + " TEXT NOT NULL, " +
				KEY_BLDROOM + " TEXT NOT NULL, " +
				KEY_SECU + " TEXT NOT NULL, " +
				LATITUDE + " TEXT NOT NULL, " +
				LONGITUDE + " TEXT NOT NULL);"
			);

			db.execSQL( "CREATE TABLE " + DATABASE_TABLE_AUTH  +"(" +
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_NAME + " TEXT NOT NULL, " +
				KEY_PASSWORD  + " TEXT NOT NULL);"
			);

			db.execSQL( "CREATE TABLE " + DATABASE_TABLE_DATE  +"(" +
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				DATE + " TEXT NOT NULL);"
			);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_AUTH);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_DATE);
			onCreate(db);
		}

	}	//private static class DbHelper 
	
	


	public HotOrNot(Context c){
		ourContext = c;
	}

	public HotOrNot open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	
	public void close(){
		if(ourDatabase!=null)
			ourDatabase.close();
		if(ourHelper!=null)
			ourHelper.close();
		ourHelper = null;
		ourDatabase = null;
	}
	
	

	/*private static class Ext_USBDbHelper extends SQLiteOpenHelper{
		
		public Ext_USBDbHelper(Context context,String extUSB_db_path) {
			//super(context,DATABASE_NAME,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			super(context,extUSB_db_path,null,DATABASE_VERSION);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		}

	}	//private static class Ext_USBDbHelper 	
	*/
	
	public HotOrNot extUSB_open(String extUSB_db_full_path) throws SQLException{
		//extUSB_ourHelper = new Ext_USBDbHelper(ourContext,extUSB_db_path);
		//extUSB_ourDatabase = extUSB_ourHelper.getReadableDatabase();
		//extUSB_ourDatabase = SQLiteDatabase.openDatabase(extUSB_db_full_path, null, SQLiteDatabase.OPEN_READONLY);
		extUSB_ourDatabase = SQLiteDatabase.openDatabase(extUSB_db_full_path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS );

		return this;
	}	

	public void extUSB_close(){
		//extUSB_ourHelper.close();
		//extUSB_ourHelper = null;
		if(extUSB_ourDatabase!=null)
			extUSB_ourDatabase.close();
		extUSB_ourDatabase = null;
	}

	public String extUSB_getDataCount() {		// get Total Datacount  from DB copied onto USB
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		datacount = null;
		try
		{
		
			//columns = new String[]{ KEY_ROWID,KEY_NAME};
			columns = new String[]{ KEY_ROWID};
			//c = 	extUSB_ourDatabase.query(DATABASE_TABLE, columns, null,null , null, null,null);
			c = 	extUSB_ourDatabase.rawQuery("select count(*) from "+DATABASE_TABLE, null); 
	      	if(c!=null){
			
	      		//datacount = ""+c.getCount();
	      		
	      		c.moveToFirst();
	      		datacount = ""+c.getLong(0);
	      		
				c.close();
			}
		}catch(Exception e){
			datacount = "-1 -- "+e.toString();
		}
		c = null;
		columns = null;		
		
		return datacount;
	}
	
	
	//public String importData() {			// get Total Datacount from Application DB.
	public String importData(Context ctx,String sdcard_db_full_path,String extUSB_db_full_path) {			// get Total Datacount from Application DB.
		
		c = null;
		result = null;
		columns = null;		
		datacount = null;	// imported Data count.
		proceed_to_import = true;
		try_second_import_method = false;
		try_third_import_method = false;
		
		try
		{
			ourDatabase.execSQL("delete from "+DATABASE_TABLE);	// peopleTable.
			
			ourDatabase.execSQL("delete from "+DATABASE_TABLE_DATE);	// syncdate table

			columns = new String[]{ KEY_ROWID};	

			c = ourDatabase.rawQuery("select count(*) from "+DATABASE_TABLE, null); 
	      	if(c!=null){

	      		c.moveToFirst();
	      		if(c.getLong(0)>0)
	      		{
	      			proceed_to_import = false;
	      			datacount = " Personal Info Records Not CLEARED properly!!!";
	      		}
	      		
	      		c.close();
			}
	      	if(proceed_to_import)
	      	{
	      		c = null;
	      		columns = null;
	      		columns = new String[]{ KEY_ROWID,DATE};		

	      		c = ourDatabase.rawQuery("select count(*) from "+DATABASE_TABLE_DATE, null); 
		      	if(c!=null){

		      		c.moveToFirst();
		      		if(c.getLong(0)>0)
		      		{
		      			proceed_to_import = false;
		      			datacount = " Sync History Not CLEARED properly!!!"; 
		      		}
		      		
		      		c.close();
				}
	      	}
	      	
	      	if(proceed_to_import)
	      	{
	      		importDate = null;
	      	
	      		importDate = (new Date()).toString();
	      		insertRecCount = 0;
	      		insertRecCount = createEntryDate(importDate);
      			if(insertRecCount<=0)
      			{
      				proceed_to_import = false;
      				datacount = " Sync Date Not Added properly!!!"; 
      			}	
      			importDate = null;
      			insertRecCount = 0;
	      	}
	      	
	      	
	      	if(proceed_to_import)
	      	{
	      		columns = null;

	      		
	      		try			// copy from Internal SDCard
	      		{
	      			ourDatabase.execSQL("ATTACH database '"+sdcard_db_full_path+"' AS remoteDB ");
	      			
	      			ourDatabase.execSQL("insert into main."+DATABASE_TABLE+" select * from remoteDB."+DATABASE_TABLE+" ");
	      			ourDatabase.execSQL("DETACH database remoteDB ");

	      			try_second_import_method  = false;
	      			try_third_import_method = false;
	      		}
	      		catch(Exception sgsg)
	      		{
	      			try
	      			{
	      				ourDatabase.execSQL("DETACH database remoteDB ");
	      			}catch(Exception egeg){}
	      			
	      			try_second_import_method = true;
	      			try_third_import_method = false;
	      		}
	      		

	      		if(try_second_import_method)	// copy from USB
	      		{
	
		      		try
		      		{
		      			ourDatabase.execSQL("ATTACH database '"+extUSB_db_full_path+"' AS remoteDB ");
		      			
		      			ourDatabase.execSQL("insert into main."+DATABASE_TABLE+" select * from remoteDB."+DATABASE_TABLE+" ");
		      			ourDatabase.execSQL("DETACH database remoteDB ");
		      			
		      			try_third_import_method = false;
		      		}
		      		catch(Exception sgsg)
		      		{
		      			try
		      			{
		      				ourDatabase.execSQL("DETACH database remoteDB ");
		      			}catch(Exception egeg){}
		      			
		      			try_third_import_method = true;
		      		}	      		
	      		}
		      		
	      		if(try_third_import_method )
	      		{
		      		// Now import peopleTable table records.
					columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
					c = extUSB_ourDatabase.query(DATABASE_TABLE, columns, null,null,null,null,null);
			
					if(c!=null && c.moveToFirst()){
						sql = "insert into "+DATABASE_TABLE+" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
						
						stmt = ourDatabase.compileStatement(sql);
						ourDatabase.beginTransaction();
						
						j = 1;
						totalInsertRecCount = 0;
						insertRecCount = 0;
						try
						{
	
							do{
								
								id = c.getString(0);		name = c.getString(1);		sal = c.getString(2);			
								add = c.getString(3);		type = c.getString(4);		gender = c.getString(5);		
								img = c.getBlob(6);			email = c.getString(7);		dob = c.getString(8);			
								valid = c.getString(9);		tint = c.getString(10);		text = c.getString(11);			
								dept = c.getString(12);		blood = c.getString(13);	med = c.getString(14);			
								mar = c.getString(15);		rel = c.getString(16);		btype = c.getString(17);		
								bno = c.getString(18);		broom = c.getString(19);	secu = c.getString(20);			
								latitude_1 = c.getString(21); 	longitude_1 = c.getString(22);	
								
								j = 1;
								stmt.clearBindings();
								
								stmt.bindString(j++, id);		stmt.bindString(j++, name);		stmt.bindString(j++, sal);
								stmt.bindString(j++, add);		stmt.bindString(j++, type);		stmt.bindString(j++, gender);
								stmt.bindBlob(j++, img);		stmt.bindString(j++, email);	stmt.bindString(j++, dob);
								stmt.bindString(j++, valid);	stmt.bindString(j++, tint);		stmt.bindString(j++, text);
								stmt.bindString(j++, dept);		stmt.bindString(j++, blood);	stmt.bindString(j++, med);
								stmt.bindString(j++, mar);		stmt.bindString(j++, rel);		stmt.bindString(j++, btype);
								stmt.bindString(j++, bno);		stmt.bindString(j++, broom);	stmt.bindString(j++, secu);
								stmt.bindString(j++, latitude_1);		stmt.bindString(j++, longitude_1);	
								
								insertRecCount = stmt.executeInsert();
								
				      			id = null;		name = null;	sal = null;		add = null; 		type = null; 	gender = null; 
				      			email = null; 	img = null;		dob = null;		valid = null; 		tint = null; 	text = null; 
				      			dept = null; 	blood = null;	med = null;		mar = null; 		rel = null; 	btype = null; 
				      			bno = null; 	broom = null; 	secu = null;	latitude_1 = null; 	longitude_1=null;			
				      			
								if(insertRecCount<=0)
				      			{
				      				proceed_to_import = false;
				      				//datacount = " Person's Records Not IMPORTED properly!!!"; 
				      				break;
				      			}
								else
								{
									//totalInsertRecCount += insertRecCount;
									totalInsertRecCount += 1;
									if(totalInsertRecCount%1000==0)
										Toast.makeText(ctx, ""+totalInsertRecCount+" Records copied", Toast.LENGTH_LONG).show();
								}
								insertRecCount = 0;
		      			
							} while(c.moveToNext());
							
							if(proceed_to_import)
							{
								ourDatabase.setTransactionSuccessful();	// Inserts are committed now.
							}
							else
							{
								// No Committing of Inserts. Hence No Record is inserted.
							}
						}
						catch(Exception innerex)
						{
							proceed_to_import = false;
							datacount = "-2 - at "+totalInsertRecCount+" - "+innerex.toString();
						}
						ourDatabase.endTransaction();
						stmt.close();
						
						if(proceed_to_import)
						{
							datacount = ""+totalInsertRecCount;
						}
						else
						{
							datacount = " Person's Records Not IMPORTED properly ("+totalInsertRecCount+")!!!"; 
						}
					}
			      	else
			      	{
			      		datacount = " No Personal Records Found for IMPORTED!!!"; 
			      	}
	      		}
	      		else	// first or second method got successfull.( attach database method)
	      		{
	      			datacount = ""+getDataCount();
	      		}
	      		
	      		
				if(c!=null)	c.close();
	      		
	      	}

	      	
		}catch(Exception e){
			datacount = "-1 -- at --> " +e.toString();
		}
		
		return datacount;
	}				
	
	public String getDataCount() {			// get Total Datacount from Application DB peopleTable.
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		datacount = null;
		try
		{
		
			//columns = new String[]{ KEY_ROWID,KEY_NAME};
			columns = new String[]{ KEY_ROWID};
			//c = 	ourDatabase.query(DATABASE_TABLE, columns, null,null , null, null,null);	
			c = 	ourDatabase.rawQuery("select count(*) from "+DATABASE_TABLE, null); 
	      	if(c!=null){
				
	      		//datacount = ""+c.getCount();
	      		
	      		c.moveToFirst();
	      		datacount = ""+c.getLong(0);
	      		
				c.close();
			}
		}catch(Exception e){
			datacount = "-1 -- "+e.toString();
		}
		c = null;
		columns = null;		
		
		return datacount;
	}
				
	
	public long createEntry(String id,String name, String lname ,byte[] image,String address,String type,String gender,String email,String dob,String validupto,String telint,String telext,String dept,String bloodgrp,String medstat,String marstat,String relation,String bldtype,String bldno,String bldroom,String security,String latitude,String longitude) {
		// TODO Auto-generated method stub
		cv = null;
		cv = new ContentValues();

		cv.put(KEY_ROWID,id);
		cv.put(KEY_NAME,name);
		cv.put(KEY_LNAME, lname);
		cv.put(KEY_IMAGE, image);		// image in byte array format
		cv.put(KEY_ADDRESS, address);
		cv.put(KEY_TYPE, type);
		cv.put(KEY_GENDER, gender);
		
		cv.put(KEY_EMAIL,email);
		cv.put(KEY_DOB, dob);
		cv.put(KEY_VALID, validupto);
		cv.put(KEY_TELINT, telint);
		cv.put(KEY_TELEXT, telext);
		
		cv.put(KEY_DEPT,dept);
		cv.put(KEY_BLOOD, bloodgrp);
		cv.put(KEY_MED, medstat);
		cv.put(KEY_MAR, marstat);
		cv.put(KEY_RELATION, relation);
		
		cv.put(KEY_BLDTYPE,bldtype);
		cv.put(KEY_BLDNO, bldno);
		cv.put(KEY_BLDROOM, bldroom);
		cv.put(KEY_SECU, security);
		cv.put(LATITUDE, latitude);
		cv.put(LONGITUDE, longitude);
		
		
		return ourDatabase.insert(DATABASE_TABLE, null,cv);		// write record in peopleTable
	}


	public long createEntry1(String name, String pass) {		
	// TODO Auto-generated method stub
		cv = null;
		cv = new ContentValues();
		
		cv.put(KEY_NAME,name);
		cv.put(KEY_PASSWORD,pass);
		
		return ourDatabase.insert(DATABASE_TABLE_AUTH, null,cv);	// write record in userTable
	
	}


	public long createEntryDate(String date) {	// keeps on adding SyncDate for every Loop of Inserts in PeopleTable.
	// TODO Auto-generated method stub
		cv = null;	
		cv = new ContentValues();

		cv.put(DATE,date);

		return ourDatabase.insert(DATABASE_TABLE_DATE, null,cv);	// write record in syncdate table

	}

////////////////////////////////////////////////////////

	public String getData(String id ,String n,String s,String btype,String bno,String broom,String setData ) {
		// TODO Auto-generated method stub
		
		c = null;
		result = null;
		result_count = 0;
		iRow = 0;iName = 0;iSal = 0;blob = 0;iType = 0;iNo = 0;iRoom = 0;
		columns = null;
		SearchRecord_counter = 0;
		
		searchCondition = null;
		searchValues = null;

		//maxSearchRecordLimit
		try
		{
			//columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};

			// fetching records without BLOB column so search becomes faster.
			//columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			
			//columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_TYPE,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM};
			//c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID +" LIKE ?"+" and "+ KEY_NAME +" LIKE ?"  + " and "  +KEY_LNAME +" LIKE ?" + " and "+ KEY_TYPE +" LIKE ?" + " and "  + KEY_BLDTYPE +" LIKE ?" + " and "  +KEY_BLDNO +" LIKE ?" + " and "  +KEY_BLDROOM +" LIKE ?" ,  new String[] {"%"+id+"%","%"+n+"%","%"+s+"%","%"+setData+"%","%"+btype+"%","%"+bno+"%","%"+broom+"%"}, null, null, null);
			
			if(id!=null && id.trim().length()>=3)
				searchCondition = KEY_ROWID +" LIKE '%"+id+"%'";
				
			if(n!=null && n.trim().length()>=3)
			{
				if(searchCondition!=null && searchCondition.trim().length()>0)
					searchCondition += " and "+ KEY_NAME +" LIKE '%"+n+"%'";
				else
					searchCondition = KEY_NAME +" LIKE '%"+n+"%'";
			}
			if(s!=null && s.trim().length()>=3)
			{
				if(searchCondition!=null && searchCondition.trim().length()>0)
					searchCondition += " and "+ KEY_LNAME +" LIKE '%"+s+"%'";
				else
					searchCondition = KEY_LNAME +" LIKE '%"+s+"%'";
			}

			if(setData!=null && setData.trim().length()>=3)
			{
				if(searchCondition!=null && searchCondition.trim().length()>0)
					searchCondition += " and "+ KEY_TYPE +" LIKE '%"+setData+"%'";
				else
					searchCondition = KEY_TYPE +" LIKE '%"+setData+"%'";
			}
			
			
			if(btype!=null && btype.trim().length()>=1 && ( (bno!=null && bno.trim().length()>=1) || (broom!=null && broom.trim().length()>=1 ) ) )
			{
				if(searchCondition!=null && searchCondition.trim().length()>0)
					searchCondition += " and "+ KEY_BLDTYPE +" LIKE '%"+btype+"%'";
				else
					searchCondition = KEY_BLDTYPE +" LIKE '%"+btype+"%'";
				
				if(bno!=null && bno.trim().length()>=1)
					searchCondition += " and "+ KEY_BLDNO +" LIKE '%"+bno+"%'";
				
				if(broom!=null && broom.trim().length()>=1)
					searchCondition += " and "+ KEY_BLDROOM +" LIKE '%"+broom+"%'";
			}
			else
			{
				if(btype!=null && btype.trim().length()>=3)
				{
					if(searchCondition!=null && searchCondition.trim().length()>0)
						searchCondition += " and "+ KEY_BLDTYPE +" LIKE '%"+btype+"%'";
					else
						searchCondition = KEY_BLDTYPE +" LIKE '%"+btype+"%'";
				}
				if(bno!=null && bno.trim().length()>=3)
				{
					if(searchCondition!=null && searchCondition.trim().length()>0)
						searchCondition += " and "+ KEY_BLDNO +" LIKE '%"+bno+"%'";
					else
						searchCondition = KEY_BLDNO +" LIKE '%"+bno+"%'";
				}
				if(broom!=null && broom.trim().length()>=3)
				{
					if(searchCondition!=null && searchCondition.trim().length()>0)
						searchCondition += " and "+ KEY_BLDROOM +" LIKE '%"+broom+"%'";
					else
						searchCondition = KEY_BLDROOM +" LIKE '%"+broom+"%'";
				}					
			}
				
			if(searchCondition!=null && searchCondition.trim().length()>0)
			{
				//columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_TYPE,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM};
				//c = ourDatabase.query(DATABASE_TABLE, columns, searchCondition , null, null, null, null);
				
				//c = ourDatabase.rawQuery("select "+KEY_ROWID+","+KEY_NAME+","+KEY_LNAME+","+KEY_TYPE+","+KEY_BLDTYPE+","+KEY_BLDNO+","+KEY_BLDROOM+" from "+DATABASE_TABLE+" where "+searchCondition, null); 

				c = ourDatabase.rawQuery("select "+KEY_ROWID+","+KEY_NAME+","+KEY_LNAME+" from "+DATABASE_TABLE+" where "+searchCondition, null);
				
				result = "";
				
				iRow = c.getColumnIndex(KEY_ROWID);
				iName = c.getColumnIndex(KEY_NAME);
				iSal = c.getColumnIndex(KEY_LNAME);
			    //blob = c.getColumnIndex(KEY_IMAGE);
			    //iType = c.getColumnIndex(KEY_BLDTYPE);
				//iNo = c.getColumnIndex(KEY_BLDNO);
				//iRoom = c.getColumnIndex(KEY_BLDROOM);
				
				for(c.moveToFirst() ; !c.isAfterLast(); c.moveToNext()){
					//result= result + c.getString(iRow) + " " + c.getString(iName) + " " + c.getString(iSal)  + "\n";
					//result = result + "" + (++result_count) +") "+ c.getString(iRow) + " " + c.getString(iName) + " " + c.getString(iSal)  + "\n";
					result= result + c.getString(iRow) + " " + c.getString(iName) + " " + c.getString(iSal)  + "  <-- "+(++result_count)+" \n";
					
					SearchRecord_counter++;
					
					if(SearchRecord_counter == Configuration.maxSearchRecordLimit)
						break;
				}
				
				c.close();
			}
			else
				result = "";	
			
		}catch(Exception e){
			result = "";
		}
		c = null;
		columns = null;
		iRow = 0;iName = 0;iSal = 0;blob = 0;iType = 0;iNo = 0;iRoom = 0;
		result_count = 0;
		searchCondition = null;
		searchValues = null;

		return result;
	}

	
	
	
	
	
	
////////////////////////////////////////////////////////

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getName1(String name) {	// getting password from userTable.
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		password = null;
		try
		{
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_PASSWORD};
			c = 	ourDatabase.query(DATABASE_TABLE_AUTH, columns, KEY_NAME + "=?",  new String[]{name}, null, null,null);
	
			if(c!=null){
				c.moveToFirst();
				password = c.getString(2);
				c.close();
			}
			
		}catch(Exception e){
			password = null;
		}
		c = null;
		columns = null;					
		return password;
	}

	public String getDate() {		// get the LastSyncDate from  syncDate table. for sync_interval_check
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		date = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID,DATE};
			c = 	ourDatabase.query(DATABASE_TABLE_DATE, columns, null,null , null, null,null);	
	      	if(c!=null){
				//c.moveToFirst();
				c.moveToPosition(c.getCount() - 1);
				date = c.getString(1);
				
				c.close();
			}
		}catch(Exception e){
			date = null;
		}
		c = null;
		columns = null;		
		
		return date;
	}
		

	public void updateEntry(String name, String pass) {	// update user's password in userTable
		// TODO Auto-generated method stub
		cvUpdate = null;
		cvUpdate = new ContentValues();
		
		cvUpdate.put(KEY_PASSWORD,pass);
		ourDatabase.update(DATABASE_TABLE_AUTH, cvUpdate, KEY_NAME + "=?",  new String[]{name});
		
		cvUpdate = null;
	}

	public void deleteEntry(String name) {	// delete user from  userTable
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE_AUTH, KEY_NAME + "=?",  new String[]{name});
	}

	public void deleteEntry1(String id) {	// delete record from peopleTable  ( ID should be the empcode / rollno )
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=?",  new String[]{id});
	}
	
	public void deleteEntry2(int id) {		// delete record from peopleTable  ( ID should be the empcode / rollno )
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null );
	}


	public void emergency() {
		// TODO Auto-generated method stub
		//ourDatabase.delete(DATABASE_TABLE_AUTH, null, null);	// userTable cleared -- disabled So that Application can be logged-in
		ourDatabase.delete(DATABASE_TABLE, null, null);			// peopleTable cleared.
	}


	
	
	

////////////////////////////////////////////////////////

	public Vector getFullRecord(String l) {
	
		c = null;
		result = null;
		columns = null;		
		fullRecord_Vector = null;
		
		try
		{
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
	
			if(c!=null){
				c.moveToFirst();
				
				fullRecord_Vector = new Vector();
				
				try{id = c.getString(0);	} catch(Exception ex) { }		fullRecord_Vector.add(id);//fullRecord_Vector.add(id==null?"":id);
				try{name = c.getString(1);	} catch(Exception ex) { }		fullRecord_Vector.add(name);//fullRecord_Vector.add(name==null?"":name);
				try{sal = c.getString(2);	} catch(Exception ex) { }		fullRecord_Vector.add(sal);//fullRecord_Vector.add(sal==null?"":sal);
				try{add = c.getString(3);	} catch(Exception ex) { }		fullRecord_Vector.add(add);//fullRecord_Vector.add(add==null?"":add);
				try{type = c.getString(4);	} catch(Exception ex) { }		fullRecord_Vector.add(type);//fullRecord_Vector.add(type==null?"":type);
				try{gender = c.getString(5);} catch(Exception ex) { }		fullRecord_Vector.add(gender);//fullRecord_Vector.add(gender==null?"":gender);
				try{email = c.getString(7);	} catch(Exception ex) { }		fullRecord_Vector.add(email);//fullRecord_Vector.add(email==null?"":email);
				try{dob = c.getString(8);	} catch(Exception ex) { }		fullRecord_Vector.add(dob);//fullRecord_Vector.add(dob==null?"":dob);
				try{valid = c.getString(9);	} catch(Exception ex) { }		fullRecord_Vector.add(valid);//fullRecord_Vector.add(valid==null?"":valid);
				try{tint = c.getString(10);	} catch(Exception ex) { }		fullRecord_Vector.add(tint);//fullRecord_Vector.add(tint==null?"":tint);
				try{text = c.getString(11);	} catch(Exception ex) { }		fullRecord_Vector.add(text);//fullRecord_Vector.add(text==null?"":text);
				try{dept = c.getString(12);	} catch(Exception ex) { }		fullRecord_Vector.add(dept);//fullRecord_Vector.add(dept==null?"":dept);
				try{blood = c.getString(13);} catch(Exception ex) { }		fullRecord_Vector.add(blood);//fullRecord_Vector.add(blood==null?"":blood);
				try{med = c.getString(14);	} catch(Exception ex) { }		fullRecord_Vector.add(med);//fullRecord_Vector.add(med==null?"":med);
				try{mar = c.getString(15);	} catch(Exception ex) { }		fullRecord_Vector.add(mar);//fullRecord_Vector.add(mar==null?"":mar);
				try{rel = c.getString(16);	} catch(Exception ex) { }		fullRecord_Vector.add(rel);//fullRecord_Vector.add(rel==null?"":rel);
				try{btype = c.getString(17);} catch(Exception ex) { }		fullRecord_Vector.add(btype);//fullRecord_Vector.add(btype==null?"":btype);
				try{bno = c.getString(18);	} catch(Exception ex) { }		fullRecord_Vector.add(bno);//fullRecord_Vector.add(bno==null?"":bno);
				try{broom = c.getString(19);} catch(Exception ex) { }		fullRecord_Vector.add(broom);//fullRecord_Vector.add(broom==null?"":broom);
				try{secu = c.getString(20);	} catch(Exception ex) { }		fullRecord_Vector.add(secu);//fullRecord_Vector.add(secu==null?"":secu);
				try{latitude_1 = c.getString(21); } catch(Exception ex) { }	fullRecord_Vector.add(latitude_1);//fullRecord_Vector.add(latitude_1==null?Configuration.default_latitude:latitude_1);
				try{longitude_1 = c.getString(22);} catch(Exception ex) { }	fullRecord_Vector.add(longitude_1);//fullRecord_Vector.add(longitude_1==null?Configuration.default_longitude:longitude_1);
				
				try{img = c.getBlob(6);		} 	catch(Exception ex) { }		fullRecord_Vector.add(img);
				
				c.close();
			}
			
		}catch(Exception e){
			fullRecord_Vector = null;
		}

		id = null;		name = null;	sal = null;		add = null; 	type = null; 	gender = null; 
		email = null; 	dob = null;		valid = null; 	tint = null; 	text = null; 	dept = null; 
		blood = null; 	med = null;		mar = null; 	rel = null; 	btype = null; 	bno = null; 
		broom = null; 	secu = null;	latitude_1 = null; longitude_1=null;	img = null;			
		
		c = null;
		columns = null;					
		
		return fullRecord_Vector;
	}



	
	public String getId(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		id = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				id = c.getString(0);
				
				c.close();
			}
			
		}catch(Exception e){
			id = null;
		}
		c = null;
		columns = null;		
			
		return id;
	
	}

	public String getName(String l) {
		// TODO Auto-generated method stub
		
		c = null;
		result = null;
		columns = null;		
		name = null;
		try
		{
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
	
			if(c!=null){
				c.moveToFirst();
				name = c.getString(1);
				
				c.close();
			}
			
		}catch(Exception e){
			name = null;
		}
		c = null;
		columns = null;					
		return name;
	}
	

	public String getSalary(String l) {	// Getting the Person's surname/salutation here not salary.
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		sal = null;
		try
		{
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				sal = c.getString(2);
				
				c.close();
			}
		}catch(Exception e){
			sal = null;
		}
		c = null;
		columns = null;					
		return sal;
	}


	public String getAddress(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		add = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				add = c.getString(3);
				
				c.close();
			}
			
		}catch(Exception e){
			add = null;
		}
		c = null;
		columns = null;		
			

		return add;
	}
	

	public String getType(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		type = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				type = c.getString(4);
				
				c.close();
			}
			
		}catch(Exception e){
			type = null;
		}
		c = null;
		columns = null;		
			
			
		return type;
	}

	public String getGender(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		gender = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				gender = c.getString(5);
				
				c.close();
			}
			
		}catch(Exception e){
			gender = null;
		}
		c = null;
		columns = null;		
			
		return gender;
	}
	
	public String getEmail(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		email = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				email = c.getString(7);
				
				c.close();
			}
		}catch(Exception e){
			email = null;
		}
		c = null;
		columns = null;		
			
		return email;
	}

	public String getDob(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		dob = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				dob = c.getString(8);
				
				c.close();
			}
		}catch(Exception e){
			dob = null;
		}
		c = null;
		columns = null;		
			
		return dob;
	}
	


	public String getValid(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		valid = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				valid = c.getString(9);
				
				c.close();
			}
		}catch(Exception e){
			valid = null;
		}
		c = null;
		columns = null;		
			
		return valid;
	}

	public String getInt(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		tint = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				tint = c.getString(10);
				
				c.close();
			}
		}catch(Exception e){
			tint = null;
		}
		c = null;
		columns = null;		
			
		return tint;
	}

	public String getExt(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		text = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				text = c.getString(11);
				
				c.close();
			}
		}catch(Exception e){
			text = null;
		}
		c = null;
		columns = null;		
			
		return text;
	}

	public String getDept(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		dept = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				dept = c.getString(12);
				
				c.close();
			}
		}catch(Exception e){
			dept = null;
		}
		c = null;
		columns = null;		
			
		return dept;
	}
	
	
	public String getBlood(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		blood = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				blood = c.getString(13);
				
				c.close();
			}
		}catch(Exception e){
			blood = null;
		}
		c = null;
		columns = null;		
			
		return blood;
	}

	public String getMed(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		med = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				med = c.getString(14);
				
				c.close();
			}
		}catch(Exception e){
			med = null;
		}
		c = null;
		columns = null;		
			
		return med;
	}


	public String getMar(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		mar = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				mar = c.getString(15);
				
				c.close();
			}
		}catch(Exception e){
			mar = null;
		}
		c = null;
		columns = null;		
			
		return mar;
	}


	

	public String getRelation(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		rel = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				rel = c.getString(16);
				
				c.close();
			}
		}catch(Exception e){
			rel = null;
		}
		c = null;
		columns = null;		
			
		return rel;
	}

	public String getBtype(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		btype = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				btype = c.getString(17);
				
				c.close();
			}
		}catch(Exception e){
			btype = null;
		}
		c = null;
		columns = null;		
			
		return btype;
	}

	public String getBno(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		bno = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				bno = c.getString(18);
				
				c.close();
			}
		}catch(Exception e){
			bno = null;
		}
		c = null;
		columns = null;		
			
		return bno;
	}

	public String getBroom(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		broom = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				broom = c.getString(19);
				
				c.close();
			}
		}catch(Exception e){
			broom = null;
		}
		c = null;
		columns = null;		
			
		return broom;
	}


	public String getSecu(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		secu = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				secu = c.getString(20);
				
				c.close();
			}
		}catch(Exception e){
			secu = null;
		}
		c = null;
		columns = null;		
			
		return secu;
	}
	
	public String getlat(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		latitude_1 = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				latitude_1 = c.getString(21);
				c.close();
			}
		}catch(Exception e){
			latitude_1 = null;
		}
		c = null;
		columns = null;		
			
		return latitude_1;
	}
	
	
	public String getlong(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		longitude_1 = null;
		try
		{
		
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				longitude_1 = c.getString(22);
				
				c.close();
			}
		}catch(Exception e){
			longitude_1 = null;
		}
		c = null;
		columns = null;		
			
		return longitude_1;
	}

	public byte[] getImage(String l) {
		// TODO Auto-generated method stub
		c = null;
		result = null;
		columns = null;		
		img = null;
		try
		{
			columns = new String[]{ KEY_ROWID , KEY_NAME ,KEY_LNAME,KEY_ADDRESS,KEY_TYPE,KEY_GENDER ,KEY_IMAGE,KEY_EMAIL,KEY_DOB,KEY_VALID,KEY_TELINT,KEY_TELEXT,KEY_DEPT,KEY_BLOOD,KEY_MED,KEY_MAR,KEY_RELATION,KEY_BLDTYPE,KEY_BLDNO,KEY_BLDROOM,KEY_SECU,LATITUDE,LONGITUDE};
			c = 	ourDatabase.query(DATABASE_TABLE, columns,  KEY_ROWID  + "=?",  new String[]{l}, null, null,null);
			if(c!=null){
				c.moveToFirst();
				img = c.getBlob(6);
				
				c.close();
			}
		
		}catch(Exception e){
			img = null;
		}
		c = null;
		columns = null;		
		return img;
		
	}

}

