package com.moka.framework.view;


import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.R;


public abstract class SupportActivityLayout<ACTIVITY extends BaseActivity, LAYOUT_LISTENER extends LayoutListener> extends BaseLayout {

	private ACTIVITY activity;
	private LAYOUT_LISTENER layoutListener;

	private AlertDialog loadingDialog;

	public SupportActivityLayout( ACTIVITY activity, LAYOUT_LISTENER layoutListener ) {

		this.activity = activity;
		this.layoutListener = layoutListener;

		inflateLayout( getLayoutResId() );
		onLayoutInflated();
	}

	@Override
	protected final View inflateLayout( int layoutResId ) {

		View rootView = LayoutInflater.from( activity ).inflate( layoutResId, null );
		if ( null != rootView ) {

			setRootView( rootView );
			initToolbar( rootView );
		}

		return getRootView();
	}

	private void initToolbar( View rootView ) {

		Toolbar toolbar = (Toolbar) rootView.findViewById( R.id.toolbar );
		if ( null != toolbar )
			activity.setSupportActionBar( toolbar );
	}

	@Override
	protected final Context getContext() {

		return getActivity();
	}

	@Override
	protected final ACTIVITY getActivity() {

		return activity;
	}

	protected final LAYOUT_LISTENER getLayoutListener() {

		return layoutListener;
	}

	public boolean onCreateOptionsMenu( Menu menu ) {

		return false;
	}

	public boolean onOptionsItemSelected( MenuItem item ) {

		return false;
	}

	public boolean onPrepareOptionsMenu( Menu menu ) {

		return false;
	}

	public void showLoadingDialog() {

		if ( !getLoadingDialog().isShowing() )
			loadingDialog.show();
	}

	private AlertDialog getLoadingDialog() {

		if ( null == loadingDialog ) {

			AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
			View rootView = getActivity().getLayoutInflater().inflate( R.layout.dialog_loading, null );
			builder.setView( rootView );
			builder.setCancelable( false );

			loadingDialog = builder.create();
		}

		return loadingDialog;
	}

	public void dismissLoadingDialog() {

		if ( null != loadingDialog && loadingDialog.isShowing() )
			loadingDialog.dismiss();
	}

}
