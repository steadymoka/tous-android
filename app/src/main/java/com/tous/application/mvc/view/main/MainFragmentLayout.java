package com.tous.application.mvc.view.main;


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
import com.tous.application.mvc.controller.activity.main.MainFragment;


public class MainFragmentLayout extends FragmentLayout<MainFragment, MainFragmentLayoutListener> implements CalendarView.CalendarListener, ViewPager.OnPageChangeListener, View.OnClickListener {

	private TextView textView_calendar;
	private TextView textView_map;

	private ImageView mapView;
	private CalendarView calendarView;

	private TextView textView_dayCount;
	private TextView textView_planName;

	private ViewPager viewPager_schedule;

	public MainFragmentLayout( MainFragment fragment, MainFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_main;
	}

	@Override
	protected void onLayoutInflated() {

		textView_calendar = (TextView) findViewById( R.id.textView_calendar );
		textView_calendar.setOnClickListener( this );
		textView_map = (TextView) findViewById( R.id.textView_map );
		textView_map.setOnClickListener( this );

		mapView = (ImageView) findViewById( R.id.mapView );

		calendarView = (CalendarView) findViewById( R.id.calendarView );
		calendarView.setCalendarListener( this );
		calendarView.setScrollable( false );
		calendarView.setDateProvider( getLayoutListener().getDateProvider() );
		calendarView.init( getLayoutListener().getCalendarViewAdapter(), CalendarViewMode.WEEK );

		textView_dayCount = (TextView) findViewById( R.id.textView_dayCount );
		textView_planName = (TextView) findViewById( R.id.textView_planName );

		viewPager_schedule = (ViewPager) findViewById( R.id.viewPager_schedule );
		viewPager_schedule.setPageMargin( (int) ScreenUtil.dipToPixel( getContext(), 16 ) );
		viewPager_schedule.setOnPageChangeListener( this );
		viewPager_schedule.setAdapter( getLayoutListener().getSchedulePagerAdapter() );
	}

	@Override
	public void onMonthChanged( int year, int month ) {

		textView_calendar.setText( String.format( "%02d월 %04d", month + 1, year ) );
	}

	@Override
	public void onDateSelected( CalendarCellData data ) {

		getLayoutListener().onCalendarItemSelected( data );
	}


	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

	}

	@Override
	public void onPageSelected( int position ) {

		getLayoutListener().onDaySelected( position );
		calendarView.selectDate( CalendarUtil.getDateFromDayIndex( position ) );
	}

	@Override
	public void onPageScrollStateChanged( int state ) {

	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.textView_calendar:

				getLayoutListener().onClickToCalendar();
				break;

			case R.id.textView_map:

				getLayoutListener().onClickToMap();
				break;
		}
	}

	public void setDayIndex( int dayIndex ) {

		int oldIndex = viewPager_schedule.getCurrentItem();
		int diff = Math.abs( dayIndex - oldIndex );

		viewPager_schedule.setCurrentItem( dayIndex, ( 7 > diff ) );
	}

	public void showCalendar() {

		calendarView.setVisibility( View.VISIBLE );
		mapView.setVisibility( View.GONE );

		textView_calendar.setTextColor( getActivity().getResources().getColor( R.color.base_text_view_selected ) );
		textView_map.setTextColor( getActivity().getResources().getColor( R.color.base_text_view_non_selected ) );
	}

	public void showMap() {

		calendarView.setVisibility( View.GONE );
		mapView.setVisibility( View.VISIBLE );

		textView_calendar.setTextColor( getActivity().getResources().getColor( R.color.base_text_view_non_selected ) );
		textView_map.setTextColor( getActivity().getResources().getColor( R.color.base_text_view_selected ) );
	}

	public void setDayCount( String dayCount ) {

		textView_dayCount.setText( dayCount );
	}

	public void setPlanName( String planName ) {

		textView_planName.setText( planName );
	}

}
