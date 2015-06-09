package com.tous.application.database.table.user;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.moka.framework.database.BaseTable;
import com.moka.framework.util.UriBuilder;
import com.squareup.phrase.Phrase;
import com.tous.application.database.TousDB;
import com.tous.application.mvc.model.user.User;
import com.tous.application.util.DateUtil;


public class UserTable extends BaseTable<User> {

	public static final String TABLE_NAME = "user";
	public static final Uri CONTENT_URI = TousDB.BASE_CONTENT_URI.buildUpon().appendPath( TABLE_NAME ).build();

	public static final int CODE_USER = TousDB.BASE_CODE_USER + 1;
	public static final int CODE_USER_ID = TousDB.BASE_CODE_USER + 2;

	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String NAME = "name";
	public static final String AUTHENTICATION_TOKEN = "authentication_token";
	public static final String SYNC_AT = "sync_at";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	private static final String[] PROJECTION = { ID, EMAIL, NAME, AUTHENTICATION_TOKEN, SYNC_AT, CREATED_AT, UPDATED_AT };

	private Context context;

	private UserTable( Context context ) {

		super( TABLE_NAME, CONTENT_URI );
		this.context = context;
	}

	@Override
	public void createTable( SQLiteDatabase database ) {

		final String queryStringFormat = "CREATE TABLE {table_name} ( " +
				"{id} INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"{email} TEXT, {name} TEXT, " +
				"{authentication_token} TEXT, {sync_at} TEXT, " +
				"{created_at} INTEGER, {updated_at} INTEGER);";

		final String queryString = Phrase.from( queryStringFormat )
				.put( "table_name", TABLE_NAME )
				.put( "id", ID )
				.put( "email", EMAIL ).put( "name", NAME )
				.put( "authentication_token", AUTHENTICATION_TOKEN ).put( "sync_at", SYNC_AT )
				.put( "created_at", CREATED_AT ).put( "updated_at", UPDATED_AT )
				.format().toString();

		database.execSQL( queryString );
	}

	@Override
	public Uri insert( User user ) {

		user.setCreatedAt( DateUtil.getTimestamp() );
		user.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( EMAIL, user.getEmail() );
		values.put( NAME, user.getName() );
		values.put( AUTHENTICATION_TOKEN, user.getAuthenticationToken() );
		values.put( SYNC_AT, user.getSyncAt() );
		values.put( CREATED_AT, user.getCreatedAt() );
		values.put( UPDATED_AT, user.getUpdatedAt() );

		return context.getContentResolver().insert( CONTENT_URI, values );
	}

	@Override
	public User find( long id ) {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		Cursor cursor = context.getContentResolver()
				.query( UriBuilder.buildUriWithId( CONTENT_URI, id ), projection, selection, selectionArgs, sortOrder );

		User user = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() )
				user = parseFrom( cursor );

			cursor.close();
		}

		return user;
	}

	private static User parseFrom( Cursor cursor ) {

		User user = new User();
		user.setId( cursor.getLong( cursor.getColumnIndex( ID ) ) );
		user.setEmail( cursor.getString( cursor.getColumnIndex( EMAIL ) ) );
		user.setName( cursor.getString( cursor.getColumnIndex( NAME ) ) );
		user.setAuthenticationToken( cursor.getString( cursor.getColumnIndex( AUTHENTICATION_TOKEN ) ) );
		user.setSyncAt( cursor.getString( cursor.getColumnIndex( SYNC_AT ) ) );
		user.setCreatedAt( cursor.getLong( cursor.getColumnIndex( CREATED_AT ) ) );
		user.setUpdatedAt( cursor.getLong( cursor.getColumnIndex( UPDATED_AT ) ) );

		return user;
	}

	@Override
	public int update( User user ) {

		user.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( EMAIL, user.getEmail() );
		values.put( NAME, user.getName() );
		values.put( AUTHENTICATION_TOKEN, user.getAuthenticationToken() );
		values.put( SYNC_AT, user.getSyncAt() );
		values.put( UPDATED_AT, user.getUpdatedAt() );

		String where = null;
		String[] selectionArgs = null;

		return context.getContentResolver()
				.update( UriBuilder.buildUriWithId( CONTENT_URI, user.getId() ), values, where, selectionArgs );
	}

	@Override
	public int delete( User user ) {

		return context.getContentResolver().delete( UriBuilder.buildUriWithId( CONTENT_URI, user.getId() ), null, null );
	}

	public static UserTable from( Context context ) {

		return new UserTable( context );
	}

}
