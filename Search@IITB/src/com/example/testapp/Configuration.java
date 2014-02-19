package com.example.testapp;

public class Configuration {

	public static final String live_or_test = "Test";
	
	public static final String AndroidSearchWeb_URL_test = "http://10.99.99.14/AndroidSearch";
	public static final String AndroidTabOutput_URL_test = "http://10.99.99.14/AndroidSearch/output/abc<tabid>.html";

	public static final String AndroidSearchWeb_URL_live = "http://10.99.99.14/security/AndroidSearch";
	public static final String AndroidTabOutput_URL_live = "http://10.99.99.14/security/AndroidSearch/output/abc<tabid>.html";

	
	public static final String firstAdminLoginPasswd = "admin";		// previously it was "iitbs"
	
	public static final String securityAdminPasswd_live = "admin";
	public static final String securityAdminPasswd_test = "admin";
	
	public static final String securitySearchUser = "security";
	public static final String securitySearchUserPasswd = "search123";
	
	public static final String tablet_data_eraser_output = "http://10.99.99.14/securei.jsp";
	
	public static final long max_sync_interval = 7;
	
	public static final boolean Sync_check_reqd = false;
	
	public static final String default_longitude = "72.915302";
	public static final String default_latitude = "19.132787";

	
	public static final String appName = "testapp";
	
	public static final String appDATABASE_NAME = "HotOrNotdb";
	
	
	public static final String masterTablets = "01|02|03|04|05|";
	public static final String appBackupFolderName = "IITB_AndroidSearch_backup";
		
	public static final String usb_drive1 = "/mnt/usbhost1";
	public static final String usb_drive2 = "/mnt/usbhost2";
	public static final String sdcard = "/mnt/sdcard";
	
	
	
	public static final int maxTabId_allowed = 100;
	
	public static final int maxSearchRecordLimit = 200;
	
	public static final String packageName = "com.example.testapp";
	
}
