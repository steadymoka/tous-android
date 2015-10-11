package com.tous.application.mvc.controller.activity.browser;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.event.OnWebUrlCopyEvent;
import com.tous.application.mvc.view.browser.WebViewFragmentLayout;
import com.tous.application.mvc.view.browser.WebViewFragmentLayoutListener;


public class WebViewFragment extends BaseFragment implements WebViewFragmentLayoutListener {

	public static final String KEY_URL = "WebViewFragment.KEY_URL";

	private WebViewFragmentLayout fragmentLayout;

	private String mUrl = null;

	@SuppressLint( "InlinedApi" )
	@Override
	public void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );

		this.mUrl = getArguments().getString( KEY_URL );

		if ( !this.mUrl.startsWith( "http" ) )
			this.mUrl = ( "http://" + this.mUrl );

		if ( Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT )
			getActivity().getWindow().addFlags( WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED );
	}

	@SuppressLint( { "SetJavaScriptEnabled", "NewApi" } )
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new WebViewFragmentLayout( this, this, inflater, container );
		fragmentLayout.loadUrl( mUrl );

		return fragmentLayout.getRootView();
	}

	@Override
	public WebViewClient getWebViewClient() {

		return new TodaitWebViewClient();
	}

	@Override
	public WebChromeClient getWebChromeClient() {

		return new TousWebChromeClient();
	}

	@Override
	public void onRefresh() {

		fragmentLayout.refresh();
	}

	@Override
	public void onCopy() {

		fragmentLayout.openContextMenu();
	}

	@Override
	public void onOpenInWebBrowser() {

		try {

			startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( this.mUrl ) ) );
		}
		catch ( ActivityNotFoundException localActivityNotFoundException ) {

			fragmentLayout.showToast( "ddddd" );
		}
	}

	@TargetApi( Build.VERSION_CODES.HONEYCOMB )
	@SuppressLint( "NewApi" )
	@SuppressWarnings( "deprecation" )
	@Override
	public void onCopyLink( String url ) {

		if ( Build.VERSION_CODES.HONEYCOMB > Build.VERSION.SDK_INT )
			( (android.text.ClipboardManager) getActivity().getSystemService( Context.CLIPBOARD_SERVICE ) ).setText( url );
		else
			( (ClipboardManager) getActivity().getSystemService( Context.CLIPBOARD_SERVICE ) ).setPrimaryClip( ClipData.newPlainText( "Tous", url ) );

		OttoUtil.getInstance().postInMainThread( new OnWebUrlCopyEvent( url ) );

		fragmentLayout.showToast( "링크가 저장되었습니다" );
	}

	@Override
	public void onClose() {

		getActivity().finish();
	}

	@Override
	public void onCreateContextMenu( ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo ) {

		super.onCreateContextMenu( contextMenu, view, contextMenuInfo );
		fragmentLayout.onCreateContextMenu( contextMenu, view, contextMenuInfo );
	}

	@SuppressLint( { "NewApi" } )
	public boolean onContextItemSelected( MenuItem menuItem ) {

		if ( fragmentLayout.onContextItemSelected( menuItem ) )
			return true;
		else
			return super.onContextItemSelected( menuItem );
	}

	@Override
	public boolean onBackPressed() {

		fragmentLayout.goBack();
		return false;
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
		fragmentLayout.destroyWebView();
	}

	private class TousWebChromeClient extends WebChromeClient {

		public TousWebChromeClient() {

		}

		public void onProgressChanged( WebView webView, int progress ) {

			fragmentLayout.updateProgressBar( progress );
		}
	}

	private class TodaitWebViewClient extends WebViewClient {

		public TodaitWebViewClient() {

		}

		public void onPageStarted( WebView webView, String url, Bitmap favicon ) {

			fragmentLayout.startLoading();
		}

		public void onPageFinished( WebView webView, String url ) {

			// TODO
			if ( url.startsWith( "market://details?id=" ) ) {

				Intent intent;

				// WebViewFragment.this.mMarketTv.setVisibility( View.VISIBLE );
				//
				// AlphaAnimation localAlphaAnimation2 = new AlphaAnimation( 0.0F, 1.0F );
				// localAlphaAnimation2.setDuration( 200L );
				// WebViewFragment.this.mMarketTv.startAnimation( localAlphaAnimation2 );

				intent = new Intent( Intent.ACTION_VIEW );
				intent.setData( Uri.parse( url ) );

				try {

					WebViewFragment.this.startActivity( intent );
				}
				catch ( ActivityNotFoundException localActivityNotFoundException ) {

					// WebViewFragment.this.mMarketTv.setVisibility( View.GONE );
				}
			}
			else {

				fragmentLayout.finishLoading();
			}
		}

		public boolean shouldOverrideUrlLoading( WebView webView, String url ) {

			Uri uri = Uri.parse( url );
			String lastPathSegment = uri.getLastPathSegment();

			if ( ( lastPathSegment != null ) && ( lastPathSegment.endsWith( ".mp4" ) ) ) {

				Intent intent = new Intent( "android.intent.action.VIEW" );
				intent.setDataAndType( uri, "video/*" );
				webView.getContext().startActivity( intent );

				return true;
			}

			return super.shouldOverrideUrlLoading( webView, url );
		}

	}

}