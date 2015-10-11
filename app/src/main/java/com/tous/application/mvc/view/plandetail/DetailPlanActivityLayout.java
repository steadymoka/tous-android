package com.tous.application.mvc.view.plandetail;


import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.internal.app.WindowDecorActionBar;
import android.view.Window;
import android.view.WindowContentFrameStats;
import android.view.WindowManager;

import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;


public class DetailPlanActivityLayout extends SupportActivityLayout<DetailPlanActivity, DetailPlanActivityLayoutListener> implements DetailPlanActivityListener {

	private ActionBar actionBar;

	private ToolbarLayout toolbarLayout;

	public DetailPlanActivityLayout( DetailPlanActivity activity, DetailPlanActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_detail_plan;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );
		actionBar.setHomeButtonEnabled( true );

		toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
		toolbarLayout.setShadow( false );

	}

	public void setActionBarTitle( String title ) {

		actionBar.setTitle( title );
	}

	public int getFrameContainerId() {

		return R.id.frameLayout_container_detail_plan;
	}

	@Override
	public void setToolbarBackgroundColorFrom( String spotType ) {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {

			switch ( spotType ) {

				case "명소":

					getActivity().getWindow().setStatusBarColor( 0xFF4db6ac );
					toolbarLayout.getToolbar().setBackgroundColor( 0xFF80cbc4 );
					break;

				case "맛집":

					getActivity().getWindow().setStatusBarColor( 0xFFffa726 );
					toolbarLayout.getToolbar().setBackgroundColor( 0xFFffb74d );
					break;

				case "숙소":

					getActivity().getWindow().setStatusBarColor( 0xFF7e57c2 );
					toolbarLayout.getToolbar().setBackgroundColor( 0xFF9575cd );
					break;

				default:

					getActivity().getWindow().setStatusBarColor( 0xFFd32f2f );
					toolbarLayout.getToolbar().setBackgroundColor( 0xFFf44336 );
					break;
			}
		}
		else {

			switch ( spotType ) {

				case "명소":

					toolbarLayout.getToolbar().setBackgroundColor( 0xFF80cbc4 );
					break;

				case "맛집":

					toolbarLayout.getToolbar().setBackgroundColor( 0xFFffb74d );
					break;

				case "숙소":

					toolbarLayout.getToolbar().setBackgroundColor( 0xFF9575cd );
					break;

				default:

					toolbarLayout.getToolbar().setBackgroundColor( 0xFFf44336 );
					break;
			}
		}
	}


}
