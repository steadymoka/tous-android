package com.tous.application.mvc.view.browser;


import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.moka.framework.view.LayoutListener;


public interface WebViewFragmentLayoutListener extends LayoutListener {

	public WebViewClient getWebViewClient();

	public WebChromeClient getWebChromeClient();

	public void onGoBack();

	public void onGoForward();

	public void onRefresh();

	public void onShare();

	public void onOpenInWebBrowser();

	public void onCopyLink();

}
