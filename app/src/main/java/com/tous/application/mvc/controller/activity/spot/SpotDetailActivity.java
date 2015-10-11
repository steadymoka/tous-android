package com.tous.application.mvc.controller.activity.spot;


import android.os.Bundle;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.mvc.view.spot.SpotDetailActivityLayout;
import com.tous.application.mvc.view.spot.SpotDetailActivityLayoutListener;


public class SpotDetailActivity extends BaseActivity implements SpotDetailActivityLayoutListener {

	public static final String KEY_SPOT_ID = "SpotDetailActivity.key_spot_id";

	private SpotDetailActivityLayout activityLayout;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new SpotDetailActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		long spotId = getIntent().getLongExtra( KEY_SPOT_ID, -1 );

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), SpotDetailFragment.newInstance().setSpotId( spotId ).setListener( activityLayout ) )
				.commit();
	}

}
