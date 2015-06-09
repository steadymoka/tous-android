package com.tous.application.test;


import com.tous.application.BuildConfig;


public class TestUtil {

	private static final boolean DEBUG_MODE = BuildConfig.DEBUG;

	public static boolean isDebugMode() {

		return DEBUG_MODE;
	}

	public static boolean isReleaseMode() {

		return !DEBUG_MODE;
	}

}
