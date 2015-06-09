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


public class DetailPlanFragmentLayout extends FragmentLayout<DetailPlanFragment, DetailPlanFragmentLayoutListener> {

	private SlidingTabPagerAdapter slidingTabPagerAdapter;
	private ViewPager viewPager;
	private PagerSlidingTabStrip pagerSlidingTabStrip;

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
		pagerSlidingTabStrip.setViewPager( viewPager );
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

}
