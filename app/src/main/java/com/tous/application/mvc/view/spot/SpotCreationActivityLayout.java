package com.tous.application.mvc.view.spot;


import android.support.v7.app.ActionBar;

import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotCreationActivity;


public class SpotCreationActivityLayout extends SupportActivityLayout<SpotCreationActivity, SpotCreationActivityLayoutListener> {

	public SpotCreationActivityLayout( SpotCreationActivity activity, SpotCreationActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_spot_creation;
	}

	@Override
	protected void onLayoutInflated() {

		ActionBar actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_spot_creation;
	}

}
