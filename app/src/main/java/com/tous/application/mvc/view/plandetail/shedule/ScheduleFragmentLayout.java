package com.tous.application.mvc.view.plandetail.shedule;


import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.schedule.ScheduleFragment;


public class ScheduleFragmentLayout extends FragmentLayout<ScheduleFragment, ScheduleFragmentLayoutListener> implements ViewPager.OnPageChangeListener, View.OnClickListener {

	private TextView textView_dayCount;

	private ViewPager viewPager_schedule;
	private ImageView imageView_calender;
	private ImageView imageView_map;

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
//		viewPager_schedule.setClipToPadding( false );
//		viewPager_schedule.setPageMargin( -16 );
		viewPager_schedule.setOnPageChangeListener( this );
		viewPager_schedule.setAdapter( getLayoutListener().getSchedulePagerAdapter() );

		imageView_calender = (ImageView) findViewById( R.id.imageView_calender );
		imageView_calender.setOnClickListener( this );

		imageView_map = (ImageView) findViewById( R.id.imageView_map );
		imageView_map.setOnClickListener( this );
	}

	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

	}

	@Override
	public void onPageSelected( int position ) {

		getLayoutListener().onPageSelected( position );
	}

	@Override
	public void onPageScrollStateChanged( int state ) {

	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.imageView_calender:

				getLayoutListener().onClickCalendar();
				break;

			case R.id.imageView_map:

				getLayoutListener().onClickMap();
				break;
		}
	}

	public void setDayCount( CharSequence dayCount ) {

		textView_dayCount.setText( dayCount );
	}

}
