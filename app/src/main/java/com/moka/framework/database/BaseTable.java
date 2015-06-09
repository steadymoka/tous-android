package com.moka.framework.database;


import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.moka.framework.util.UriBuilder;


public abstract class BaseTable<DATA extends DataObject> {

	private String TABLE_NAME;
	private Uri CONTENT_URI;

	public BaseTable( String tableName, Uri contentUri ) {

		this.TABLE_NAME = tableName;
		this.CONTENT_URI = contentUri;
	}

	public Uri insert( SQLiteDatabase database, Uri uri, ContentValues values ) {

		long id = database.insert( TABLE_NAME, null, values );

		return UriBuilder.buildUriWithId( CONTENT_URI, id );
	}

	public Cursor find( SQLiteDatabase database, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

		if ( null != selection ) {

			long id = ContentUris.parseId( uri );
			selection = selection + " AND id=?";

			String[] newSelectionArgs = new String[selectionArgs.length + 1];
			System.arraycopy( selectionArgs, 0, newSelectionArgs, 0, selectionArgs.length );
			newSelectionArgs[newSelectionArgs.length - 1] = String.valueOf( id );

			return database.query( TABLE_NAME, projection, selection, newSelectionArgs, null, null, sortOrder );
		}
		else {

			long id = ContentUris.parseId( uri );
			selection = "id=?";
			selectionArgs = new String[]{ String.valueOf( id ) };

			return database.query( TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );
		}
	}

	public Cursor findAll( SQLiteDatabase database, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

		return database.query( TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder );
	}

	public int update( SQLiteDatabase database, Uri uri, ContentValues values, String selection, String[] selectionArgs ) {

		long id = ContentUris.parseId( uri );
		selection = "id=?";
		selectionArgs = new String[]{ String.valueOf( id ) };

		return database.update( TABLE_NAME, values, selection, selectionArgs );
	}

	public int updateAll( SQLiteDatabase database, Uri uri, ContentValues values, String selection, String[] selectionArgs ) {

		return database.update( TABLE_NAME, values, selection, selectionArgs );
	}

	public abstract void createTable( SQLiteDatabase database );

	public abstract Uri insert( DATA data );

	public abstract DATA find( long id );

	public abstract int update( DATA data );

	public abstract int delete( DATA data );

}
