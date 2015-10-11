package com.tous.application.mvc.controller.activity.browser;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.mvc.view.browser.WebViewActivityLayout;
import com.tous.application.mvc.view.browser.WebViewActivityLayoutListener;


public class WebViewActivity extends BaseActivity implements WebViewActivityLayoutListener {

	public static final String KEY_URL = "WebViewActivity.KEY_URL";
	public static final String KEY_TITLE = "WebViewActivity.KEY_TITLE";

	private WebViewActivityLayout activityLayout;

	private WebViewFragment webViewFragment;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new WebViewActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		Intent intent = getIntent();
		String url = intent.getStringExtra( KEY_URL );
		if ( null != url ) {

			webViewFragment = new WebViewFragment();

			Bundle args = new Bundle();
			args.putString( WebViewFragment.KEY_URL, url );
			webViewFragment.setArguments( args );

			getSupportFragmentManager().beginTransaction().replace( activityLayout.getWebViewFragmentContainer(), webViewFragment ).commit();
		}
	}

	@Override
	public void onBackPressed() {

		webViewFragment.onBackPressed();
	}

}
