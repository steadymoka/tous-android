package com.tous.application.mvc.controller.activity.map;


import android.os.Bundle;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.mvc.view.map.FindLocationActivityLayout;
import com.tous.application.mvc.view.map.FindLocationActivityLayoutListener;


public class FindLocationActivity extends BaseActivity implements FindLocationActivityLayoutListener {

	private FindLocationActivityLayout activityLayout;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		activityLayout = new FindLocationActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFrameContainerId(), FindLocationFragment.newInstance() )
				.commit();
	}

}
