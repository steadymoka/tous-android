package com.tous.application.mvc.controller.activity.plandetail;


import android.os.Bundle;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.DetailPlanActivityLayout;
import com.tous.application.mvc.view.plandetail.DetailPlanActivityLayoutListener;


public class DetailPlanActivity extends BaseActivity implements DetailPlanActivityLayoutListener {

	public static final String KEY_PLAN_ID = "DetailPlanActivity.KEY_PLAN_ID";

	private DetailPlanActivityLayout activityLayout;

	private Plan plan;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new DetailPlanActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		long planId = getIntent().getLongExtra( KEY_PLAN_ID, -1 );
		loadDataFromDB( planId );

		DetailPlanFragment detailPlanFragment = DetailPlanFragment
				.newInstance()
				.setPlan( plan )
				.setDetailPlanActivityListener( activityLayout );

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), detailPlanFragment )
				.commit();

		activityLayout.setActionBarTitle( plan.getName() );
	}

	private void loadDataFromDB( long planId ) {

		plan = PlanTable.from( this ).find( planId );
	}

}
