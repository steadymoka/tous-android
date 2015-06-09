package com.tous.application.mvc.controller.activity.plancreation;


import android.os.Bundle;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.mvc.view.plancreation.PlanCreationActivityLayout;
import com.tous.application.mvc.view.plancreation.PlanCreationActivityLayoutListener;


public class PlanCreationActivity extends BaseActivity implements PlanCreationActivityLayoutListener {

	public static final String KEY_PLAN_ID = "PlanCreationActivity.KEY_PLAN_ID";

	private PlanCreationActivityLayout activityLayout;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new PlanCreationActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		long planId = getIntent().getLongExtra( KEY_PLAN_ID, -1 );
		activityLayout.setTitle( -1 == planId ); // TODO 땜빵

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), PlanCreationFragment.newInstance().setPlanId( planId ) )
				.commit();
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
	}

}
