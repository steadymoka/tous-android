package com.tous.application.mvc.view.browser;


import com.moka.framework.controller.BaseActivity;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;


public class WebViewActivityLayout extends SupportActivityLayout<BaseActivity, WebViewActivityLayoutListener> {

	public WebViewActivityLayout( BaseActivity activity, WebViewActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_web_view;
	}

	@Override
	protected void onLayoutInflated() {

	}

	public int getWebViewFragmentContainer() {

		return R.id.frameLayout_webViewFragmentContainer;
	}

}
