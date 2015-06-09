package com.tous.application.mvc.view.plancreation;


import android.support.v7.app.ActionBar;

import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationActivity;


public class PlanCreationActivityLayout extends SupportActivityLayout<PlanCreationActivity, PlanCreationActivityLayoutListener> {

	private ActionBar actionBar;

	public PlanCreationActivityLayout( PlanCreationActivity activity, PlanCreationActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_plan_creation;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_plan_creation;
	}

	public void setTitle( boolean flag ) {

		if ( flag ) // TODO 땜빵
			actionBar.setTitle( "새로운 여정" );
	}

}
