package com.tous.application.database.table.spot;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.moka.framework.database.BaseTable;
import com.moka.framework.util.UriBuilder;
import com.squareup.phrase.Phrase;
import com.tous.application.database.TousDB;
import com.tous.application.mvc.model.transport.Spot;
import com.tous.application.util.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class SpotTable extends BaseTable<Spot> {

	public static final String TABLE_NAME = "spot";
	public static final Uri CONTENT_URI = TousDB.BASE_CONTENT_URI.buildUpon().appendPath( TABLE_NAME ).build();

	public static final int CODE_SPOT = TousDB.BASE_CODE_SPOT + 1;
	public static final int CODE_SPOT_ID = TousDB.BASE_CODE_SPOT + 2;

	public static final String ID = "id";
	public static final String PLAN_ID = "plan_id";
	public static final String SERVER_ID = "server_id";
	public static final String NAME = "name";
	public static final String CONTENT = "content";
	public static final String ADDRESS = "address";
	public static final String SITE = "site";
	public static final String START_AT = "start_at";
	public static final String END_AT = "end_at";
	public static final String TYPE = "type";
	public static final String DIRTY_FLAG = "dirty_flag";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	private static final String[] PROJECTION = { ID, PLAN_ID, SERVER_ID, NAME, CONTENT, START_AT, END_AT, ADDRESS, SITE, TYPE, DIRTY_FLAG, CREATED_AT, UPDATED_AT };

	private Context context;

	public SpotTable( Context context ) {

		super( TABLE_NAME, CONTENT_URI );
		this.context = context;
	}

	@Override
	public void createTable( SQLiteDatabase database ) {

		final String queryStringFormat = "CREATE TABLE {table_name} ( " +
				"{id} INTEGER PRIMARY KEY AUTOINCREMENT, {plan_id} TEXT, {server_id} INTEGER, " +
				"{name} TEXT, {content} TEXT, " +
				"{start_at} TEXT, {end_at} TEXT, {address} TEXT, {site} TEXT, {type} TEXT, " +
				"{dirty_flag} INTEGER DEFAULT 1, {created_at} INTEGER, {updated_at} INTEGER);";

		final String queryString = Phrase.from( queryStringFormat )
				.put( "table_name", TABLE_NAME )
				.put( "id", ID ).put( "plan_id", PLAN_ID ).put( "server_id", SERVER_ID )
				.put( "name", NAME ).put( "content", CONTENT )
				.put( "start_at", START_AT ).put( "end_at", END_AT ).put( "address", ADDRESS ).put( "site", SITE ).put( "type", TYPE )
				.put( "dirty_flag", DIRTY_FLAG ).put( "created_at", CREATED_AT ).put( "updated_at", UPDATED_AT )
				.format().toString();

		database.execSQL( queryString );
	}

	@Override
	public Uri insert( Spot spot ) {

		spot.setCreatedAt( DateUtil.getTimestamp() );
		spot.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( PLAN_ID, spot.getPlanId() );
		values.put( SERVER_ID, spot.getServerId() );
		values.put( NAME, spot.getName() );
		values.put( CONTENT, spot.getContent() );
		values.put( START_AT, spot.getStartAt() );
		values.put( END_AT, spot.getEndAt() );
		values.put( ADDRESS, spot.getAddress() );
		values.put( SITE, spot.getSite() );
		values.put( TYPE, spot.getType() );
		values.put( DIRTY_FLAG, spot.getDirtyFlag() );
		values.put( CREATED_AT, spot.getCreatedAt() );
		values.put( UPDATED_AT, spot.getUpdatedAt() );

		return context.getContentResolver().insert( CONTENT_URI, values );
	}

	@Override
	public Spot find( long id ) {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		Cursor cursor = context.getContentResolver()
				.query( UriBuilder.buildUriWithId( CONTENT_URI, id ), projection, selection, selectionArgs, sortOrder );

		Spot spot = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() )
				spot = parseFrom( cursor );

			cursor.close();
		}

		return spot;
	}

	public List<Spot> findAll() {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		ArrayList<Spot> spots = new ArrayList<>();
		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		if ( null != cursor ) {

			if ( 0 < cursor.getCount() ) {

				while ( cursor.moveToNext() )
					spots.add( parseFrom( cursor ) );
			}

			cursor.close();
		}

		return spots;
	}

	public List<Spot> findSpotListOfPlan( long planId ) {

		String[] projection = PROJECTION;
		String selection = PLAN_ID + "=?";
		String[] selectionArgs = { String.valueOf( planId ) };
		String sortOrder = null;

		ArrayList<Spot> spots = new ArrayList<>();
		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		if ( null != cursor ) {

			if ( 0 < cursor.getCount() ) {

				while ( cursor.moveToNext() )
					spots.add( parseFrom( cursor ) );
			}

			cursor.close();
		}

		return spots;
	}

	public List<Spot> findSpotListOfPlanAndType( long planId, String spotType ) {

		String[] projection = PROJECTION;
		String selection = PLAN_ID + "=? AND " + TYPE + "=?";
		String[] selectionArgs = { String.valueOf( planId ), spotType };
		String sortOrder = null;

		ArrayList<Spot> spots = new ArrayList<>();
		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		if ( null != cursor ) {

			if ( 0 < cursor.getCount() ) {

				while ( cursor.moveToNext() )
					spots.add( parseFrom( cursor ) );
			}

			cursor.close();
		}

		return spots;
	}

	private static Spot parseFrom( Cursor cursor ) {

		Spot spot = new Spot();
		spot.setId( cursor.getLong( cursor.getColumnIndex( ID ) ) );
		spot.setPlanId( cursor.getLong( cursor.getColumnIndex( PLAN_ID ) ) );
		spot.setServerId( cursor.getLong( cursor.getColumnIndex( SERVER_ID ) ) );
		spot.setName( cursor.getString( cursor.getColumnIndex( NAME ) ) );
		spot.setContent( cursor.getString( cursor.getColumnIndex( CONTENT ) ) );
		spot.setStartAt( cursor.getLong( cursor.getColumnIndex( START_AT ) ) );
		spot.setEndAt( cursor.getLong( cursor.getColumnIndex( END_AT ) ) );
		spot.setAddress( cursor.getString( cursor.getColumnIndex( ADDRESS ) ) );
		spot.setSite( cursor.getString( cursor.getColumnIndex( SITE ) ) );
		spot.setType( cursor.getString( cursor.getColumnIndex( TYPE ) ) );
		spot.setDirtyFlag( cursor.getInt( cursor.getColumnIndex( DIRTY_FLAG ) ) );
		spot.setCreatedAt( cursor.getLong( cursor.getColumnIndex( CREATED_AT ) ) );
		spot.setUpdatedAt( cursor.getLong( cursor.getColumnIndex( UPDATED_AT ) ) );

		return spot;
	}

	@Override
	public int update( Spot spot ) {

		spot.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( PLAN_ID, spot.getPlanId() );
		values.put( SERVER_ID, spot.getServerId() );
		values.put( NAME, spot.getName() );
		values.put( CONTENT, spot.getContent() );
		values.put( START_AT, spot.getStartAt() );
		values.put( END_AT, spot.getEndAt() );
		values.put( ADDRESS, spot.getAddress() );
		values.put( SITE, spot.getSite() );
		values.put( TYPE, spot.getType() );
		values.put( DIRTY_FLAG, spot.getDirtyFlag() );
		values.put( UPDATED_AT, spot.getUpdatedAt() );

		String where = null;
		String[] selectionArgs = null;

		return context.getContentResolver()
				.update( UriBuilder.buildUriWithId( CONTENT_URI, spot.getId() ), values, where, selectionArgs );
	}

	@Override
	public int delete( Spot spot ) {

		return context.getContentResolver().delete( UriBuilder.buildUriWithId( CONTENT_URI, spot.getId() ), null, null );
	}

	public static SpotTable from( Context context ) {

		return new SpotTable( context );
	}

}
