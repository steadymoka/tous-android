

package com.moka.framework.util;


import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;


public class GooglePlusUtil {
	
	public static Uri isGooglePlusUri( Context context, String communityUrl ) {
	
		try {
			
			context.getPackageManager().getApplicationInfo( "com.google.android.apps.plus", 0 );
			return Uri.parse( communityUrl );
		}
		catch ( PackageManager.NameNotFoundException e ) {
			
			return Uri.parse( "market://details?id=com.google.android.apps.plus" );
		}
	}
}
