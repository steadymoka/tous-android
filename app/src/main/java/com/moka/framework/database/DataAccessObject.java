package com.moka.framework.database;


import android.content.Context;


public class DataAccessObject<DATA extends DataObject> {

	public static final int NO_ARCHIVED = 0;
	public static final int ARCHIVED = 1;

	private static Context context;

	public static Context getContext() {

		return context;
	}

	public static void setContext( Context context ) {

		if ( null == context )
			throw new NullPointerException( "context is null" );

		DataAccessObject.context = context;
	}

	public synchronized void clearMemoryCache() {

	}

	// TODO add default CRUD method

}
