package com.tous.application.mvc.view.spot;


import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotCreationActivity;


public class SpotCreationActivityLayout extends SupportActivityLayout<SpotCreationActivity, SpotCreationActivityLayoutListener> {

	private ToolbarLayout toolbarLayout;

	public SpotCreationActivityLayout( SpotCreationActivity activity, SpotCreationActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_spot_creation;
	}

	@Override
	protected void onLayoutInflated() {

		ActionBar actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );

		toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case android.R.id.home:

				getLayoutListener().onHomeMenuItemSelected();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_spot_creation;
	}

	public void setToolbarBackgroundViewSpot() {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
			getActivity().getWindow().setStatusBarColor( 0xFF00bcd4 );
		toolbarLayout.getToolbar().setBackgroundResource( R.color.spot_type_view_spot );
	}

	public void setToolbarBackgroundRestaurant() {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
			getActivity().getWindow().setStatusBarColor( 0xFFfb8c00 );
		toolbarLayout.getToolbar().setBackgroundResource( R.color.spot_type_restaurant );
	}

	public void setToolbarBackgroundLodgment() {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
			getActivity().getWindow().setStatusBarColor( 0xFF5e35b1 );
		toolbarLayout.getToolbar().setBackgroundResource( R.color.spot_type_lodgment );
	}

}
