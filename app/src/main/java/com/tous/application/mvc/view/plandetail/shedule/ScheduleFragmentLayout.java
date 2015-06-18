package com.tous.application.mvc.view.plandetail.shedule;


import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moka.framework.util.ScreenUtil;
import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.util.CalendarViewMode;
import com.moka.framework.widget.calendar.view.CalendarView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.schedule.ScheduleFragment;


public class ScheduleFragmentLayout extends FragmentLayout<ScheduleFragment, ScheduleFragmentLayoutListener> implements ViewPager.OnPageChangeListener, View.OnClickListener {

	private TextView textView_dayCount;

	private ViewPager viewPager_schedule;

	public ScheduleFragmentLayout( ScheduleFragment fragment, ScheduleFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_schedule;
	}

	@Override
	protected void onLayoutInflated() {

		textView_dayCount = (TextView) findViewById( R.id.textView_dayCount );

		viewPager_schedule = (ViewPager) findViewById( R.id.viewPager_schedule );
		viewPager_schedule.setClipToPadding( false );
		viewPager_schedule.setPageMargin( -16 );
		viewPager_schedule.setOnPageChangeListener( this );
		viewPager_schedule.setAdapter( getLayoutListener().getSchedulePagerAdapter() );
	}

	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

	}

	@Override
	public void onPageSelected( int position ) {

		getLayoutListener().onDaySelected( position );
	}

	@Override
	public void onPageScrollStateChanged( int state ) {

	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

		}
	}

	public void setDayIndex( int dayIndex ) {

//		int oldIndex = viewPager_schedule.getCurrentItem();
//		int diff = Math.abs( dayIndex - oldIndex );
//
//		viewPager_schedule.setCurrentItem( dayIndex, ( 7 > diff ) );
		viewPager_schedule.setCurrentItem( 0 );
	}

	public void setDayCount( String dayCount ) {

		textView_dayCount.setText( dayCount );
	}

}
