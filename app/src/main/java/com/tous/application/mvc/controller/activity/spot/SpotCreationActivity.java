package com.tous.application.mvc.controller.activity.spot;


import android.os.Bundle;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.moka.framework.util.OttoUtil;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.view.spot.SpotCreationActivityLayout;
import com.tous.application.mvc.view.spot.SpotCreationActivityLayoutListener;


public class SpotCreationActivity extends BaseActivity implements SpotCreationActivityLayoutListener {

	public static final String KEY_SPOT_TYPE = "SpotCreationActivity.key_spot_type";
	public static final String KEY_PLAN_ID = "SpotCreationActivity.key_plan_id";
	public static final String KEY_SPOT_ID = "SpotCreationActivity.key_spot_id";

	private SpotCreationActivityLayout activityLayout;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new SpotCreationActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		String spotType = getIntent().getStringExtra( KEY_SPOT_TYPE );
		long planId = getIntent().getLongExtra( KEY_PLAN_ID, -1 );
		long spotId = getIntent().getLongExtra( KEY_SPOT_ID, -1 );

		SpotCreationFragment spotCreationFragment;
		if ( -1 == spotId ) {

			spotCreationFragment = SpotCreationFragment.newInstance()
					.setType( spotType )
					.setPlanId( planId );
		}
		else {

			spotCreationFragment = SpotCreationFragment.newInstance()
					.setType( spotType )
					.setPlanId( planId )
					.setSpotId( spotId )
					.setEditMode( true );
		}

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), spotCreationFragment )
				.commit();

		setToolbarColor( spotType );
	}

	private void setToolbarColor( String spotType ) {

		switch ( spotType ) {

			case "명소":

				activityLayout.setToolbarBackgroundViewSpot();
				break;

			case "맛집":

				activityLayout.setToolbarBackgroundRestaurant();
				break;

			case "숙소":

				activityLayout.setToolbarBackgroundLodgment();
				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		if ( activityLayout.onOptionsItemSelected( item ) )
			return true;
		else
			return super.onOptionsItemSelected( item );
	}

	@Override
	public void onHomeMenuItemSelected() {

		onBackPressed();
	}

	@Override
	public void onBackPressed() {

		OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
		super.onBackPressed();
	}

}
