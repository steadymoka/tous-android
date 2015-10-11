package com.tous.application.mvc.view.spot;


import android.support.v7.app.ActionBar;

import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotDetailActivity;


public class SpotDetailActivityLayout extends SupportActivityLayout<SpotDetailActivity, SpotDetailActivityLayoutListener> implements SpotDetailListener {

	private ActionBar actionBar;
	private ToolbarLayout toolbarLayout;

	public SpotDetailActivityLayout( SpotDetailActivity activity, SpotDetailActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_spot_detail;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );
		setTitle( "" );

		toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 0.0f );
		toolbarLayout.setExpand( true );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_spot_detail;
	}

	public void setTitle( String title ) {

		getActivity().setTitle( title );
	}

	@Override
	public void hideToolbar() {

		toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 0.0f );
		toolbarLayout.setExpand( true );
	}

}
