package com.tous.application.database.table.plan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.moka.framework.database.BaseTable;
import com.moka.framework.util.UriBuilder;
import com.squareup.phrase.Phrase;
import com.tous.application.database.TousDB;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.util.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class PlanTable extends BaseTable<Plan> {

	public static final String TABLE_NAME = "plan";
	public static final Uri CONTENT_URI = TousDB.BASE_CONTENT_URI.buildUpon().appendPath( TABLE_NAME ).build();

	public static final int CODE_PLAN = TousDB.BASE_CODE_PLAN + 1;
	public static final int CODE_PLAN_ID = TousDB.BASE_CODE_PLAN + 2;

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String SERVER_ID = "server_id";
	public static final String NAME = "name";
	public static final String CONTENT = "content";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";
	public static final String DIRTY_FLAG = "dirty_flag";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	private static final String[] PROJECTION = { ID, USER_ID, SERVER_ID, NAME, CONTENT, START_DATE, END_DATE, DIRTY_FLAG, CREATED_AT, UPDATED_AT };

	private Context context;

	private PlanTable( Context context ) {

		super( TABLE_NAME, CONTENT_URI );
		this.context = context;
	}

	@Override
	public void createTable( SQLiteDatabase database ) {

		final String queryStringFormat = "CREATE TABLE {table_name} ( " +
				"{id} INTEGER PRIMARY KEY AUTOINCREMENT, {user_id} INTEGER, {server_id} INTEGER, " +
				"{name} TEXT, {content} TEXT, {start_date} INTEGER, {end_date} INTEGER, " +
				"{dirty_flag} INTEGER DEFAULT 1, {created_at} INTEGER, {updated_at} INTEGER);";

		final String queryString = Phrase.from( queryStringFormat )
				.put( "table_name", TABLE_NAME )
				.put( "id", ID ).put( "user_id", USER_ID ).put( "server_id", SERVER_ID )
				.put( "name", NAME ).put( "content", CONTENT ).put( "start_date", START_DATE ).put( "end_date", END_DATE )
				.put( "dirty_flag", DIRTY_FLAG ).put( "created_at", CREATED_AT ).put( "updated_at", UPDATED_AT )
				.format().toString();

		database.execSQL( queryString );
	}

	@Override
	public Uri insert( Plan plan ) {

		plan.setCreatedAt( DateUtil.getTimestamp() );
		plan.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( USER_ID, plan.getUserId() );
		values.put( SERVER_ID, plan.getServerId() );
		values.put( NAME, plan.getName() );
		values.put( CONTENT, plan.getContent() );
		values.put( START_DATE, plan.getStartDate() );
		values.put( END_DATE, plan.getEndDate() );
		values.put( DIRTY_FLAG, plan.getDirtyFlag() );
		values.put( CREATED_AT, plan.getCreatedAt() );
		values.put( UPDATED_AT, plan.getUpdatedAt() );

		return context.getContentResolver().insert( CONTENT_URI, values );
	}

	@Override
	public Plan find( long id ) {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		Cursor cursor = context.getContentResolver()
				.query( UriBuilder.buildUriWithId( CONTENT_URI, id ), projection, selection, selectionArgs, sortOrder );

		Plan plan = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() )
				plan = parseFrom( cursor );

			cursor.close();
		}

		return plan;
	}

	public List<Plan> findAll() {

		String[] projection = PROJECTION;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;

		ArrayList<Plan> plans = new ArrayList<>();
		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		if ( null != cursor ) {

			if ( 0 < cursor.getCount() ) {

				while ( cursor.moveToNext() )
					plans.add( parseFrom( cursor ) );
			}

			cursor.close();
		}

		return plans;
	}

	public List<Plan> findAllOfUser( long userId ) {

		String[] projection = PROJECTION;
		String selection = USER_ID + "=?";
		String[] selectionArgs = { String.valueOf( userId ) };
		String sortOrder = null;

		ArrayList<Plan> plans = new ArrayList<>();
		Cursor cursor = context.getContentResolver()
				.query( CONTENT_URI, projection, selection, selectionArgs, sortOrder );

		if ( null != cursor ) {

			if ( 0 < cursor.getCount() ) {

				while ( cursor.moveToNext() )
					plans.add( parseFrom( cursor ) );
			}

			cursor.close();
		}

		return plans;
	}

	private static Plan parseFrom( Cursor cursor ) {

		Plan plan = new Plan();
		plan.setId( cursor.getLong( cursor.getColumnIndex( ID ) ) );
		plan.setUserId( cursor.getLong( cursor.getColumnIndex( USER_ID ) ) );
		plan.setServerId( cursor.getLong( cursor.getColumnIndex( SERVER_ID ) ) );
		plan.setName( cursor.getString( cursor.getColumnIndex( NAME ) ) );
		plan.setContent( cursor.getString( cursor.getColumnIndex( CONTENT ) ) );
		plan.setStartDate( cursor.getInt( cursor.getColumnIndex( START_DATE ) ) );
		plan.setEndDate( cursor.getInt( cursor.getColumnIndex( END_DATE ) ) );
		plan.setDirtyFlag( cursor.getInt( cursor.getColumnIndex( DIRTY_FLAG ) ) );
		plan.setCreatedAt( cursor.getLong( cursor.getColumnIndex( CREATED_AT ) ) );
		plan.setUpdatedAt( cursor.getLong( cursor.getColumnIndex( UPDATED_AT ) ) );

		return plan;
	}

	@Override
	public int update( Plan plan ) {

		plan.setUpdatedAt( DateUtil.getTimestamp() );

		ContentValues values = new ContentValues();
		values.put( USER_ID, plan.getUserId() );
		values.put( SERVER_ID, plan.getServerId() );
		values.put( NAME, plan.getName() );
		values.put( CONTENT, plan.getContent() );
		values.put( START_DATE, plan.getStartDate() );
		values.put( DIRTY_FLAG, plan.getDirtyFlag() );
		values.put( END_DATE, plan.getEndDate() );
		values.put( UPDATED_AT, plan.getUpdatedAt() );

		String where = null;
		String[] selectionArgs = null;

		return context.getContentResolver()
				.update( UriBuilder.buildUriWithId( CONTENT_URI, plan.getId() ), values, where, selectionArgs );
	}

	@Override
	public int delete( Plan plan ) {

		return context.getContentResolver().delete( UriBuilder.buildUriWithId( CONTENT_URI, plan.getId() ), null, null );
	}

	public static PlanTable from( Context context ) {

		return new PlanTable( context );
	}

}
