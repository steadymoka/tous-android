package com.tous.application.mvc.view.browser;


import android.annotation.SuppressLint;
import android.os.Build;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;


public class WebViewFragmentLayout extends FragmentLayout<BaseFragment, WebViewFragmentLayoutListener> implements OnClickListener {

	private WebView webView;
	private LinearLayout linearLayout_web_view_title;
	private FrameLayout frameLayout_webViewContainer;

	private ProgressBar progressBar_progress;

	private ImageView imageView_refresh;
	private ProgressBar progressBar_loading;
	private TextView textView_copy;

	public WebViewFragmentLayout( BaseFragment fragment, WebViewFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_web_view;
	}

	@Override
	protected void onLayoutInflated() {

		webView = new WebView( getActivity() );
		webView.setLayoutParams( new ViewGroup.LayoutParams( -1, -1 ) );
		webView.setWebViewClient( getLayoutListener().getWebViewClient() );
		webView.setWebChromeClient( getLayoutListener().getWebChromeClient() );
		initWebSettings( webView.getSettings() );

		linearLayout_web_view_title = (LinearLayout) findViewById( R.id.linearLayout_web_view_title );
		linearLayout_web_view_title.setOnClickListener( this );

		frameLayout_webViewContainer = (FrameLayout) findViewById( R.id.frameLayout_webViewContainer );
		frameLayout_webViewContainer.setLayoutParams( new RelativeLayout.LayoutParams( -1, -1 ) );
		frameLayout_webViewContainer.addView( webView );

		progressBar_progress = (ProgressBar) findViewById( R.id.progressBar_progress );

		imageView_refresh = (ImageView) findViewById( R.id.imageView_refresh );
		imageView_refresh.setOnClickListener( this );

		progressBar_loading = (ProgressBar) findViewById( R.id.progressBar_loading );

		textView_copy = (TextView) findViewById( R.id.textView_copy );
		textView_copy.setOnClickListener( this );

		updateActionView();
	}

	@SuppressWarnings( "deprecation" )
	@SuppressLint( { "SetJavaScriptEnabled", "NewApi" } )
	private void initWebSettings( WebSettings webSettings ) {

		webSettings.setPluginState( WebSettings.PluginState.ON );
		webSettings.setUseWideViewPort( true );
		webSettings.setDefaultZoom( WebSettings.ZoomDensity.MEDIUM );
		webSettings.setBuiltInZoomControls( true );
		webSettings.setSupportZoom( true );
		webSettings.setJavaScriptCanOpenWindowsAutomatically( true );
		webSettings.setAllowFileAccess( true );
		webSettings.setDomStorageEnabled( true );
		webSettings.setJavaScriptEnabled( true );
		webSettings.setAppCacheEnabled( true );

		if ( Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT )
			webSettings.setDisplayZoomControls( false );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_web_view_title:

				getLayoutListener().onClose();
				break;

			case R.id.imageView_refresh:

				getLayoutListener().onRefresh();
				break;

			case R.id.textView_copy:

				getLayoutListener().onCopyLink( webView.getUrl() );
//				getLayoutListener().onCopy();
				break;
		}

//		getLayoutListener().onGoBack();
//		getLayoutListener().onGoForward();
		updateActionView();
	}

	private void updateActionView() {

		if ( null != webView ) {

		}
	}

	@Override
	public void onCreateContextMenu( ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo ) {

		getActivity().getMenuInflater().inflate( R.menu.fragment_web_view, contextMenu );
	}

	@Override
	public boolean onContextItemSelected( MenuItem menuItem ) {

		switch ( menuItem.getItemId() ) {

			case R.id.open_in_browser:

				getLayoutListener().onOpenInWebBrowser();
				return true;

			case R.id.copy_link:

				getLayoutListener().onCopyLink( webView.getUrl() );
				return true;

			default:

				return super.onContextItemSelected( menuItem );
		}
	}

	public void loadUrl( String url ) {

		if ( null != webView )
			webView.loadUrl( url );
	}

	public void updateProgressBar( int progress ) {

		if ( ( 100 > progress ) && ( View.GONE == progressBar_progress.getVisibility() ) )
			progressBar_progress.setVisibility( View.VISIBLE );
		else if ( 100 == progress )
			progressBar_progress.setVisibility( View.GONE );

		progressBar_progress.setProgress( progress );
	}

	public void startLoading() {

		progressBar_loading.setVisibility( View.VISIBLE );
		imageView_refresh.setVisibility( View.INVISIBLE );

		updateActionView();
	}

	public void finishLoading() {

		progressBar_loading.setVisibility( View.INVISIBLE );
		imageView_refresh.setVisibility( View.VISIBLE );

		updateActionView();
	}

	public void goBack() {

		if ( null != webView )
			webView.goBack();
	}

	public void goForward() {

		if ( null != webView )
			webView.goForward();
	}

	public void refresh() {

		if ( null != webView )
			webView.reload();
	}

	public void openContextMenu() {

//		getFragment().registerForContextMenu( getRootView() );
//		getActivity().openContextMenu( getRootView() );
	}

	public void destroyWebView() {

		if ( null != frameLayout_webViewContainer )
			frameLayout_webViewContainer.removeView( webView );

		if ( null != webView ) {

			webView.removeAllViews();
			webView.destroy();
		}
	}

}
