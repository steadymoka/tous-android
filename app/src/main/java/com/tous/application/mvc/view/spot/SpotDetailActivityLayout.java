package com.tous.application.mvc.view.spot;


import android.support.v7.app.ActionBar;

import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotDetailActivity;


public class SpotDetailActivityLayout extends SupportActivityLayout<SpotDetailActivity, SpotDetailActivityLayoutListener> {

	private ActionBar actionBar;

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
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_spot_detail;
	}

	public void setTitle( String title ) {

		actionBar.setTitle( title );
	}

}
