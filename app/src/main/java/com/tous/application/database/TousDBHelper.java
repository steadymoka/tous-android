package com.tous.application.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tous.application.database.table.image.ImageTable;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.database.table.transport.TransportTable;
import com.tous.application.database.table.user.UserTable;


public class TousDBHelper extends SQLiteOpenHelper {

	private Context context;

	public TousDBHelper( Context context ) {

		super( context, TousDB.DB_NAME, null, TousDBSheme.CURRENT_DB_VERSION );
		this.context = context;
	}

	@Override
	public void onCreate( SQLiteDatabase database ) {

		UserTable.from( context ).createTable( database );
		PlanTable.from( context ).createTable( database );
		SpotTable.from( context ).createTable( database );
		TransportTable.from( context ).createTable( database );
		ImageTable.from( context ).createTable( database );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

	}

}
