package com.tous.application.mvc.view.main;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.ScreenUtil;
import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.fab.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.MainFragment;

import java.io.File;
import java.util.Random;


public class MainFragmentLayout extends FragmentLayout<MainFragment, MainFragmentLayoutListener> implements View.OnClickListener {

	private ImageView imageView_plan_background;
	private FloatingActionButton floatingActionButton_detail_plan;

	private TextView textView_dDay;
	private TextView textView_planName;
	private TextView textView_planPeriod;
	private TextView textView_planContent;

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

		textView_dDay = (TextView) findViewById( R.id.textView_dDay );
		textView_planName = (TextView) findViewById( R.id.textView_planName );
		textView_planPeriod = (TextView) findViewById( R.id.textView_planPeriod );
		textView_planContent = (TextView) findViewById( R.id.textView_planContent );

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

	public void setPlanImage( String imagePath ) {

		int[] images = { R.drawable.ic_default_image_02, R.drawable.ic_default_image_03, R.drawable.ic_default_image_04, R.drawable.ic_default_image_05, R.drawable.ic_default_image_01 };
		int defaultImage = R.drawable.ic_default_image_01;

		int headerWidth = ScreenUtil.getWidthPixels( getContext() );
		int headerHeight = ScreenUtil.getHeightPixels( getContext() );
//		int headerHeight = (int) ( headerWidth / 1.5f );

		if ( null != imagePath ) {

			Picasso.with( getContext() )
					.load( new File( imagePath ) )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 1.7 ) )
					.into( imageView_plan_background );
		}
		else {

			Picasso.with( getContext() )
					.load( images[new Random().nextInt( images.length )] )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 1.7 ) )
					.into( imageView_plan_background );
		}
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

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		inflater.inflate( R.menu.fragment_detail_plan, menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case R.id.menuItem_editPlan:

				getLayoutListener().onEditPlanMenuItemSelected();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	public void setTitle( String planName ) {

		getActivity().setTitle( planName );
	}

	public void setTextView_planName( String planName ) {

		textView_planName.setText( planName );

		LinearLayout linearLayout_planName = (LinearLayout) findViewById( R.id.linearLayout_planName );
		linearLayout_planName.setBackgroundColor( 0xFFf44336 );
	}

	public void setTextView_planPeriod( String planPeroid ) {

		textView_planPeriod.setText( planPeroid );
	}

	public void setTextView_dDay( String dDay ) {

		textView_dDay.setText( dDay );
	}

	public void setTextView_planContent( String content ) {

		textView_planContent.setText( content );
	}

	public void setNoPlan() {

		textView_dDay.setText( "" );
		textView_planName.setText( "" );
		textView_planName.setHint( "새 여행을 등록하세요" );
		textView_planPeriod.setHint( "여행 날짜" );
		textView_planContent.setHint( "메모" );
	}

}
