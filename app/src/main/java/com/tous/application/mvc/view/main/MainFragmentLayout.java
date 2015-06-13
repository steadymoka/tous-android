package com.tous.application.mvc.view.main;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.moka.framework.util.ScreenUtil;
import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.MainFragment;

import java.io.File;


public class MainFragmentLayout extends FragmentLayout<MainFragment, MainFragmentLayoutListener> implements View.OnClickListener {

	private ImageView imageView_plan_background;
	private FloatingActionButton floatingActionButton_detail_plan;

	private MainActivityListener mainActivityListener;

	public MainFragmentLayout( MainFragment fragment, MainFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_main;
	}

	@Override
	protected void onLayoutInflated() {

		if ( null != mainActivityListener ) {

			mainActivityListener.setExpandToolbar( true );
			mainActivityListener.setAlpha( 0.0f );
		}

		imageView_plan_background = (ImageView) findViewById( R.id.imageView_plan_background );
		imageView_plan_background.setOnClickListener( this );

		floatingActionButton_detail_plan = (FloatingActionButton) findViewById( R.id.floatingActionButton_detail_plan );
		floatingActionButton_detail_plan.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.floatingActionButton_detail_plan:

				getLayoutListener().onClickToDetailPlan();
				break;

			case R.id.imageView_plan_background:

				getLayoutListener().onClickToSettingImage();
				break;
		}
	}

	public void setPlanBackgroundImage( String imagePath ) {

		int headerWidth = ScreenUtil.getWidthPixels( getContext() );
		int headerHeight = (int) ( headerWidth / 1.5f );

		Picasso.with( getContext() )
				.load( new File( imagePath ) )
				.centerCrop()
				.resize( headerWidth, headerHeight )
				.into( imageView_plan_background );
	}

	public void setFloatingActionButton( boolean visible ) {

		if ( visible )
			floatingActionButton_detail_plan.setVisibility( View.VISIBLE );
		else
			floatingActionButton_detail_plan.setVisibility( View.GONE );
	}

	public void setMainActivityListener( MainActivityListener mainActivityListener ) {

		this.mainActivityListener = mainActivityListener;

		if ( null != mainActivityListener ) {

			mainActivityListener.setExpandToolbar( true );
			mainActivityListener.setAlpha( 0.0f );
		}
	}

}
