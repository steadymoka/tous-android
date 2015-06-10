package com.tous.application.mvc.view.browser;


import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;


public class WebViewActivityLayout extends SupportActivityLayout<BaseActivity, WebViewActivityLayoutListener> {

	private ActionBar actionBar;

	public WebViewActivityLayout( BaseActivity activity, WebViewActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_web_view;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();

		if ( null != actionBar ) {

			actionBar.setDisplayShowHomeEnabled( true );
			actionBar.setDisplayHomeAsUpEnabled( true );
		}
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case android.R.id.home:

				getLayoutListener().onHomeMenuItemSelected();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	public void setTitle( String title ) {

		if ( null != actionBar )
			actionBar.setTitle( title );
	}

	public int getWebViewFragmentContainer() {

		return R.id.frameLayout_webViewFragmentContainer;
	}

}
