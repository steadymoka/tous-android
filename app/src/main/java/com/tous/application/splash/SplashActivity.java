package com.tous.application.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.MainActivity;
import com.tous.application.util.TousPreference;


public class SplashActivity extends BaseActivity implements Callback {

	private static final int DELAY_MILLIS = 1000;
	private static final int TO_NEXT_ACTIVITY = 0;

	private Handler handler;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_splash );

		handler = new Handler( this );
	}

	@Override
	protected void onResume() {

		super.onResume();
		handler.sendEmptyMessageDelayed( TO_NEXT_ACTIVITY, DELAY_MILLIS );
	}

	@Override
	public boolean handleMessage( Message message ) {

		switch ( message.what ) {

			case TO_NEXT_ACTIVITY:

				navigateNextActivity();
				return true;
		}

		return false;
	}

	private void navigateNextActivity() {

		if ( TousPreference.getInstance( this ).isFirstRun() )
			startTutorialActivity();
		else
			startMainActivity();
	}

	private void startTutorialActivity() {

		// TODO :
		startMainActivity();
	}

	private void startMainActivity() {

		startActivity( new Intent( this, MainActivity.class ) );
		overridePendingTransition( R.anim.fade_in, R.anim.linear );
		finish();
	}

	@Override
	public void onBackPressed() {

		handler.removeMessages( TO_NEXT_ACTIVITY );
		super.onBackPressed();
	}

}
