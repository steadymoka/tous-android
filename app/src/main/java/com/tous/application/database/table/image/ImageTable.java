package com.tous.application.database.table.image;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.moka.framework.database.BaseTable;
import com.moka.framework.util.UriBuilder;
import com.squareup.phrase.Phrase;
import com.tous.application.database.TousDB;
import com.tous.application.mvc.model.image.Image;
import com.tous.application.util.DateUtil;


public class ImageTable extends BaseTable<Image> {

	public static final String TABLE_NAME = "image";
	public static final Uri CONTENT_URI = TousDB.BASE_CONTENT_URI.buildUpon().appendPath( TABLE_NAME ).build();

	public static final int CODE_IMAGE = TousDB.BASE_CODE_IMAGE + 1;
	public static final int CODE_IMAGE_ID = TousDB.BASE_CODE_IMAGE + 2;

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String SERVER_ID = "server_id";
	public static final String IMAGE_TYPE = "image_type";
	public static final String IMAGE_ID = "image_id";
	public static final String IMAGE_NAME = "image_name";
	public static final String DIRTY_FLAG = "dirty_flag";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	private static final String[] PROJECTION = { ID, USER_ID, SERVER_ID, IMAGE_TYPE, IMAGE_ID, IMAGE_NAME, DIRTY_FLAG, CREATED_AT, UPDATED_AT };

	private Context context;

	private ImageTable( Context context ) {

		super( TABLE_NAME, CONTENT_URI );
		this.context = context;
	}

	@Override
	public void createTable( SQLiteDatabase database ) {

		final String queryStringFormat = "CREATE TABLE {table_name} ( " +
				"{id} INTEGER PRIMARY KEY AUTOINCREMENT, {user_id} INTEGER, {server_id} INTEGER, " +
				"{image_type} TEXT, {image_id} INTEGER, {image_name} TEXT, " +
				"{dirty_flag} INTEGER DEFAULT 1, {created_at} INTEGER, {updated_at} INTEGER);";

		final String queryString = Phrase.from( queryStringFormat )
				.put( "table_name", TABLE_NAME )
				.put( "id", ID ).put( "user_id", USER_ID ).put( "server_id", SERVER_ID )
				.put( "image_type", IMAGE_TYPE ).put( "image_id", IMAGE_ID ).put( "image_name", IMAGE_NAME )
				.put( "dirty_flag", DIRTY_FLAG ).put( "created_at", CREATED_AT ).put( "updated_at", UPDATED_AT )
				.format().toString();

		database.execSQL( queryString );
	}

	@Override
	public Uri insert( Image image ) {

		image.setCreatedAt( DateUtil.getTimestamp() );
		image.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( USER_ID, image.getUserId() );
		values.put( SERVER_ID, image.getServerId() );
		values.put( IMAGE_TYPE, image.getImageType() );
		values.put( IMAGE_ID, image.getImageId() );
		values.put( IMAGE_NAME, image.getImageName() );
		values.put( DIRTY_FLAG, image.getDirtyFlag() );
		values.put( CREATED_AT, image.getCreatedAt() );
		values.put( UPDATED_AT, image.getUpdatedAt() );

		return context.getContentResolver().insert( CONTENT_URI, values );
	}

	@Override
	public Image find( long id ) {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		Cursor cursor = context.getContentResolver()
				.query( UriBuilder.buildUriWithId( CONTENT_URI, id ), projection, selection, selectionArgs, sortOrder );

		Image image = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() )
				image = parseFrom( cursor );

			cursor.close();
		}

		return image;
	}

	public Image findImageFrom( String imageType, long imageId ) {

		String[] projection = PROJECTION;
		String selection = IMAGE_TYPE + "=? AND " + IMAGE_ID + "=?";
		String[] selectionArgs = { imageType, String.valueOf( imageId ) };
		String sortOrder = null;

		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		Image image = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() )
				image = parseFrom( cursor );

			cursor.close();
		}

		return image;
	}

	private Image parseFrom( Cursor cursor ) {

		Image image = new Image();
		image.setId( cursor.getLong( cursor.getColumnIndex( ID ) ) );
		image.setUserId( cursor.getLong( cursor.getColumnIndex( USER_ID ) ) );
		image.setServerId( cursor.getLong( cursor.getColumnIndex( SERVER_ID ) ) );
		image.setImageType( cursor.getString( cursor.getColumnIndex( IMAGE_TYPE ) ) );
		image.setImageId( cursor.getLong( cursor.getColumnIndex( IMAGE_ID ) ) );
		image.setImageName( cursor.getString( cursor.getColumnIndex( IMAGE_NAME ) ) );
		image.setDirtyFlag( cursor.getInt( cursor.getColumnIndex( DIRTY_FLAG ) ) );
		image.setCreatedAt( cursor.getLong( cursor.getColumnIndex( CREATED_AT ) ) );
		image.setUpdatedAt( cursor.getLong( cursor.getColumnIndex( UPDATED_AT ) ) );

		return image;
	}

	@Override
	public int update( Image image ) {

		image.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( USER_ID, image.getUserId() );
		values.put( SERVER_ID, image.getServerId() );
		values.put( IMAGE_TYPE, image.getImageType() );
		values.put( IMAGE_ID, image.getImageId() );
		values.put( IMAGE_NAME, image.getImageName() );
		values.put( DIRTY_FLAG, image.getDirtyFlag() );
		values.put( UPDATED_AT, image.getUpdatedAt() );

		String where = null;
		String[] selectionArgs = null;

		return context.getContentResolver()
				.update( UriBuilder.buildUriWithId( CONTENT_URI, image.getId() ), values, where, selectionArgs );
	}

	@Override
	public int delete( Image image ) {

		return context.getContentResolver().delete( UriBuilder.buildUriWithId( CONTENT_URI, image.getId() ), null, null );
	}

	public static ImageTable from( Context context ) {

		return new ImageTable( context );
	}

}
