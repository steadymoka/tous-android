package com.tous.application.test;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class TestPreferenceManager {

	private static final String SHARED_PREFERENCE_NAME = "test_shared_preference";

	private static final String KEY_TEST_DATA_GENERATED = "key_test_data_generated";

	private static TestPreferenceManager instance;

	private TestPreferenceManager() {

	}

	public static TestPreferenceManager getInstance() {

		if ( null == instance )
			instance = new TestPreferenceManager();

		return instance;
	}

	public void setTestDataGenerated( Context context, boolean testDataGenerated ) {

		getEditor( context ).putBoolean( KEY_TEST_DATA_GENERATED, testDataGenerated ).commit();
	}

	public boolean isTestDataGenerated( Context context ) {

		return getSharedPreferences( context ).getBoolean( KEY_TEST_DATA_GENERATED, false );
	}

	private Editor getEditor( Context context ) {

		return getSharedPreferences( context ).edit();
	}

	private SharedPreferences getSharedPreferences( Context context ) {

		return context.getSharedPreferences( SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE );
	}

}
