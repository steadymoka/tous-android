package com.tous.application.mvc.controller.activity.main;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.moka.framework.controller.BaseActivity;
import com.moka.framework.util.BackPressedHandler;
import com.moka.framework.util.OttoUtil;
import com.squareup.otto.Subscribe;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.event.OnClickDrawerPlanItem;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainActivityLayout;
import com.tous.application.mvc.view.main.MainActivityLayoutListener;
import com.tous.application.util.FragmentStack;

import java.util.List;


public class MainActivity extends BaseActivity implements MainActivityLayoutListener {

	public static final String KEY_PLAN_ID = "MainActivity.KEY_PLAN_ID";

	private MainActivityLayout activityLayout;
	private FragmentStack fragmentStack;

	private BackPressedHandler backPressedHandler;

	private Plan plan;

	@SuppressLint( "MissingSuperCall" )
	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );

		activityLayout = new MainActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );
		OttoUtil.getInstance().register( this );

		long planId = getIntent().getLongExtra( KEY_PLAN_ID, -1 );

		loadDataFromDB( planId );
		activityLayout.setPlanToNavigationDrawerFragment( plan );

		initFragmentStack();

		backPressedHandler = new BackPressedHandler( this );
		backPressedHandler.setMessage( "뒤로 버튼을 한 번 더 누르면 종료됩니다" );
	}

	private void initFragmentStack() {

		FragmentManager fragmentManager = getSupportFragmentManager();
		int containerViewId = activityLayout.getFragmentContainerID();
		MainFragment taskIndexFragment = getMainFragment();

		fragmentStack = FragmentStack.newInstance( fragmentManager, containerViewId, taskIndexFragment );
	}

	private MainFragment getMainFragment() {

		MainFragment mainFragment = MainFragment.newInstance()
				.setPlan( plan )
				.setMainActivityListener( activityLayout );
		return mainFragment;
	}

	@Subscribe
	public void onClickDrawerPlanItem( OnClickDrawerPlanItem onClickDrawerPlanItem ) {

		plan = onClickDrawerPlanItem.getPlan();
		fragmentStack.replaceBottomWith( getMainFragment() );
	}

	@Subscribe
	public void onRefreshViewEvent( OnRefreshViewEvent onRefreshViewEvent ) {

		if ( null != plan )
			loadDataFromDB( plan.getId() );
	}

	public void loadDataFromDB( long planId ) {

		if ( -1 == planId ) {

			List<Plan> planList = PlanTable.from( this ).findAll();
			if ( 0 < planList.size() )
				plan = planList.get( 0 );
		}
		else {

			plan = PlanTable.from( this ).find( planId );
		}
	}

	@Override
	public void onBackPressed() {

		backPressedHandler.onBackPressed();
	}

	@Override
	protected void onDestroy() {

		OttoUtil.getInstance().unregister( this );
		super.onDestroy();
	}

}
