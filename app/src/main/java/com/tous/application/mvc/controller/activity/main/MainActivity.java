package com.tous.application.mvc.controller.activity.main;


import android.annotation.SuppressLint;
import android.os.Bundle;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainActivityLayout;
import com.tous.application.mvc.view.main.MainActivityLayoutListener;


public class MainActivity extends BaseActivity implements MainActivityLayoutListener {

	public static final String KEY_PLAN_ID = "MainActivity.KEY_PLAN_ID";

	private MainActivityLayout activityLayout;

	private Plan plan;

	@SuppressLint( "MissingSuperCall" )
	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );

		activityLayout = new MainActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		long planId = getIntent().getLongExtra( KEY_PLAN_ID, -1 );
		loadDataFromDB( planId );
		setTitle();

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFragmentContainerID(), MainFragment.newInstance().setPlan( plan ) )
				.commit();
	}

	public void loadDataFromDB( long planId ) {

		if ( -1 == planId )
			planId = 1;
//			planId = PlanTable.from( this ).findAll().size();
		plan = PlanTable.from( this ).find( planId );
	}

	public void setTitle() {

		activityLayout.setTitle( plan.getName() );
	}

}
