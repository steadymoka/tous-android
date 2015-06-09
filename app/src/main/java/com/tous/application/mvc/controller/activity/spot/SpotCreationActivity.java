package com.tous.application.mvc.controller.activity.spot;


import android.os.Bundle;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.mvc.view.spot.SpotCreationActivityLayout;
import com.tous.application.mvc.view.spot.SpotCreationActivityLayoutListener;


public class SpotCreationActivity extends BaseActivity implements SpotCreationActivityLayoutListener {

	public static final String KEY_SPOT_TYPE = "SpotCreationActivity.key_spot_type";
	public static final String KEY_PLAN_ID = "SpotCreationActivity.key_plan_id";

	private SpotCreationActivityLayout activityLayout;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new SpotCreationActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		String spotType = getIntent().getStringExtra( KEY_SPOT_TYPE );
		long planId = getIntent().getLongExtra( KEY_PLAN_ID, -1 );

		SpotCreationFragment spotCreationFragment = SpotCreationFragment.newInstance()
				.setType( spotType )
				.setPlanId( planId );

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), spotCreationFragment )
				.commit();
	}

}
