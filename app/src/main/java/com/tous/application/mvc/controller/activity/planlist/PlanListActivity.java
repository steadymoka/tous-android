package com.tous.application.mvc.controller.activity.planlist;


import android.os.Bundle;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.mvc.view.planlist.PlanListActivityLayout;
import com.tous.application.mvc.view.planlist.PlanListActivityLayoutListener;


public class PlanListActivity extends BaseActivity implements PlanListActivityLayoutListener {

	private PlanListActivityLayout activityLayout;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new PlanListActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), PlanListFragment.newInstance() )
				.commit();
	}

}
