package com.tous.application;


import com.moka.framework.controller.BaseApplication;
import com.moka.framework.database.DataAccessObject;
import com.moka.framework.util.CrashlyticsUtil;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.util.ScreenUtil;
import com.tous.application.test.TestDataGenerator;
import com.tous.application.test.TestPreferenceManager;
import com.tous.application.test.TestUtil;
import com.tous.application.util.DateUtil;


public class TousApplication extends BaseApplication {

	@Override
	public void onCreate() {

		super.onCreate();

		OttoUtil.start();
		ScreenUtil.setContext( this );
		DataAccessObject.setContext( this );
		DateUtil.setContext( this );

		if ( !TestPreferenceManager.getInstance().isTestDataGenerated( this ) ) {

//			TestDataGenerator.generateTestData( this );
//			TestPreferenceManager.getInstance().setTestDataGenerated( this, true );
		}

		if ( TestUtil.isReleaseMode() ) { // TODO:

			CrashlyticsUtil.start( getApplicationContext() );
		}
	}

}
