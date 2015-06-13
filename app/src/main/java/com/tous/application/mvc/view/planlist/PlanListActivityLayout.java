package com.tous.application.mvc.view.planlist;


import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.planlist.PlanListActivity;


public class PlanListActivityLayout extends SupportActivityLayout<PlanListActivity, PlanListActivityLayoutListener> {

	private ActionBar actionBar;
	private ToolbarLayout toolbarLayout;

	public PlanListActivityLayout( PlanListActivity activity, PlanListActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_plan_list;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );

		toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_plan_list;
	}

}
