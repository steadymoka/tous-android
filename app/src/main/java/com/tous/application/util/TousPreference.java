package com.tous.application.util;


import android.content.Context;

import com.moka.framework.util.SharedPreferenceManager;


public class TousPreference extends SharedPreferenceManager {

	private static final String PREFERENCE_NAME = "tous_shared_preference";

	private static final String KEY_FIRST_RUN = "key_first_run";
	private static final String KEY_PLAN_IMAGE = "key_plan_image";

	private static TousPreference instance;

	private Context context;

	private TousPreference( Context context ) {

		super( PREFERENCE_NAME );
		this.context = context;
	}

	public boolean isFirstRun() {

		return getSharedPreferences( context ).getBoolean( KEY_FIRST_RUN, true );
	}

	public void setFirstRun( boolean firstRun ) {

		getEditor( context ).putBoolean( KEY_FIRST_RUN, firstRun ).commit();
	}

	public String getPlanImageName() {

		return getSharedPreferences( context ).getString( KEY_PLAN_IMAGE, null );
	}

	public void setPlanImageName( String imageName ) {

		getEditor( context ).putString( KEY_PLAN_IMAGE, imageName ).commit();
	}

	public static TousPreference getInstance( Context context ) {

		if ( null == instance )
			instance = new TousPreference( context );

		return instance;
	}

}
