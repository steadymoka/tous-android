package com.tous.application.mvc.view.browser;


import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.moka.framework.view.LayoutListener;


public interface WebViewFragmentLayoutListener extends LayoutListener {

	WebViewClient getWebViewClient();

	WebChromeClient getWebChromeClient();

	void onRefresh();

	void onCopy();

	void onOpenInWebBrowser();

	void onCopyLink( String url );

	void onClose();

}
