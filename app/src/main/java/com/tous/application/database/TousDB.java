package com.tous.application.database;


import android.net.Uri;


public class TousDB {

	public static final String DB_NAME = "tous.db";

	public static final String AUTHORITY = "com.tous.provider";
	public static final Uri BASE_CONTENT_URI = Uri.parse( "content://" + AUTHORITY );

	public static final int BASE_CODE_USER = 0x001000;
	public static final int BASE_CODE_PLAN = 0x002000;
	public static final int BASE_CODE_SPOT = 0x003000;
	public static final int BASE_CODE_TRANSPORT = 0x00400;

}
