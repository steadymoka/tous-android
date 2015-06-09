package com.moka.framework.util;


import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class CrashlyticsUtil {

	private static boolean isStarted = false;

	private CrashlyticsUtil() {

	}

	public static void start( Context context ) {

		final Fabric fabric = new Fabric.Builder( context )
				.kits( new Crashlytics() )
				.debuggable( true )
				.build();

		Fabric.with( fabric );

		isStarted = true;
	}

	public static void forceCrash() {

		throw new RuntimeException( "This crash is for test" );
	}

	public static void stackOverFlow() {

		stackOverFlow();
	}

	public static void s( Throwable throwable ) {

		if ( !isStarted )
			return;

		Crashlytics.logException( throwable );
	}

	public static void log( String msg ) {

		if ( !isStarted )
			return;

		Crashlytics.log( msg );
	}

	public static void updateUserData( String userName, String userEmail, int userIdentifier ) {

		if ( !isStarted )
			return;

		Crashlytics.setUserName( userName );
		Crashlytics.setUserEmail( userEmail );
		Crashlytics.setUserIdentifier( String.valueOf( userIdentifier ) );
	}

	public static class Log {

		public static final int PRIORITY_DEBUG = 3;
		public static final int PRIORITY_ERROR = 6;

		public static void d( String tag, String msg ) {

			log( PRIORITY_DEBUG, tag, tag );
		}

		public static void e( String tag, String msg ) {

			log( PRIORITY_ERROR, tag, msg );
		}

		public static void e( String tag, String msg, Throwable throwable ) {

			log( PRIORITY_ERROR, tag, msg, throwable );
		}

		private static void log( int priority, String tag, String msg ) {

			if ( !CrashlyticsUtil.isStarted )
				return;

			Crashlytics.log( priority, tag, msg );
		}

		private static void log( int priority, String tag, String msg, Throwable throwable ) {

			if ( !CrashlyticsUtil.isStarted )
				return;

			Crashlytics.log( priority, tag, msg );
			Crashlytics.logException( throwable );
		}

	}

}
