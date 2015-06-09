package com.tous.application.mvc.view.plandetail;


import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;


public class DetailPlanActivityLayout extends SupportActivityLayout<DetailPlanActivity, DetailPlanActivityLayoutListener> {

	private ActionBar actionBar;

	public DetailPlanActivityLayout( DetailPlanActivity activity, DetailPlanActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_detail_plan;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );
		actionBar.setHomeButtonEnabled( true );
	}

	public void setActionBarTitle( String title ) {

		actionBar.setTitle( title );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_detail_plan;
	}

}
