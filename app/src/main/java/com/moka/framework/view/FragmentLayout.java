package com.moka.framework.view;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tous.application.R;


public abstract class FragmentLayout<FRAGMENT extends Fragment, LAYOUT_LISTENER extends LayoutListener> extends BaseLayout {

	private FRAGMENT fragment;
	private LAYOUT_LISTENER layoutListener;
	private LayoutInflater layoutInflater;
	private ViewGroup root;
	private AlertDialog loadingDialog;

	public FragmentLayout( FRAGMENT fragment, LAYOUT_LISTENER layoutListener, LayoutInflater inflater, ViewGroup container ) {

		this.fragment = fragment;
		this.layoutListener = layoutListener;
		this.layoutInflater = inflater;
		this.root = container;

		inflateLayout( getLayoutResId() );
	}

	@Override
	protected final View inflateLayout( int layoutResId ) {

		View rootView = layoutInflater.inflate( layoutResId, root, false );
		if ( null != rootView ) {

			setRootView( rootView );
			onLayoutInflated();
		}

		return getRootView();
	}

	@Override
	protected final Context getContext() {

		return getFragment().getActivity();
	}

	@Override
	protected final Activity getActivity() {

		return getFragment().getActivity();
	}

	protected final FRAGMENT getFragment() {

		return fragment;
	}

	protected final LAYOUT_LISTENER getLayoutListener() {

		return layoutListener;
	}

	public void onCreateContextMenu( ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo ) {

	}

	public boolean onContextItemSelected( MenuItem menuItem ) {

		return false;
	}

	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

	}

	public boolean onOptionsItemSelected( MenuItem item ) {

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
