package com.tous.application.mvc.view.map;


import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.view.ActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.map.FindLocationActivity;


public class FindLocationActivityLayout extends ActivityLayout<FindLocationActivity, FindLocationActivityLayoutListener> {

	public FindLocationActivityLayout( FindLocationActivity activity, FindLocationActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_find_map;
	}

	@Override
	protected void onLayoutInflated() {

		ToolbarLayout toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_find_map;
	}

}
