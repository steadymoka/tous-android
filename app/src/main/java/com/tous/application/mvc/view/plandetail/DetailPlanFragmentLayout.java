package com.tous.application.mvc.view.plandetail;


import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.moka.framework.app.SlidingTabPagerAdapter;
import com.moka.framework.util.ScreenUtil;
import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanFragment;
import com.tous.application.widget.PagerSlidingTabStrip;


public class DetailPlanFragmentLayout extends FragmentLayout<DetailPlanFragment, DetailPlanFragmentLayoutListener> implements ViewPager.OnPageChangeListener {

	private SlidingTabPagerAdapter slidingTabPagerAdapter;
	private ViewPager viewPager;
	private PagerSlidingTabStrip pagerSlidingTabStrip;

	private DetailPlanActivityListener detailPlanActivityListener;

	public DetailPlanFragmentLayout( DetailPlanFragment fragment, DetailPlanFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_detail_plan;
	}

	@Override
	protected void onLayoutInflated() {

		slidingTabPagerAdapter = getLayoutListener().getSlidingTabPagerAdapter();

		viewPager = (ViewPager) findViewById( R.id.viewPager );
		viewPager.setPageMargin( (int) ScreenUtil.dipToPixel( getContext(), 16 ) );
		viewPager.setOffscreenPageLimit( 5 );
		viewPager.setAdapter( slidingTabPagerAdapter );

		pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById( R.id.pagerSlidingTabStrip );
		pagerSlidingTabStrip.setBackgroundColor( 0xFFf44336 );
		pagerSlidingTabStrip.setViewPager( viewPager );
		pagerSlidingTabStrip.setOnPageChangeListener( this );
	}

	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {


	}

	@Override
	public void onPageSelected( int position ) {

		if ( position == 0 ) {

			if ( null != detailPlanActivityListener )
				detailPlanActivityListener.setToolbarBackgroundColorFrom( "" );
			pagerSlidingTabStrip.setBackgroundColor( 0xFFf44336 );
		}
		else if ( position == 1 ) {

			if ( null != detailPlanActivityListener )
				detailPlanActivityListener.setToolbarBackgroundColorFrom( "명소" );
			pagerSlidingTabStrip.setBackgroundColor( 0xFF80cbc4 );
		}
		else if ( position == 2 ) {

			if ( null != detailPlanActivityListener )
				detailPlanActivityListener.setToolbarBackgroundColorFrom( "맛집" );
			pagerSlidingTabStrip.setBackgroundColor( 0xFFffb74d );
		}
		else {

			if ( null != detailPlanActivityListener )
				detailPlanActivityListener.setToolbarBackgroundColorFrom( "숙소" );
			pagerSlidingTabStrip.setBackgroundColor( 0xFF9575cd );
		}

	}

	@Override
	public void onPageScrollStateChanged( int state ) {

	}

	public void setDetailPlanActivityListener( DetailPlanActivityListener detailPlanActivityListener ) {

		this.detailPlanActivityListener = detailPlanActivityListener;
	}

}
