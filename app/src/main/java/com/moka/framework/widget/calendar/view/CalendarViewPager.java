package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.util.ScreenUtil;
import com.moka.framework.widget.calendar.adapter.CalendarViewPagerAdapter;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.util.OnPageChangedListenerAdapter;
import com.nineoldandroids.view.ViewHelper;

import java.util.Calendar;


public class CalendarViewPager extends ViewPager {

	private ViewPagerMode viewPagerMode;
	private CalendarViewPagerAdapter pagerAdapter;
	private OnPageChangedListener onPageChangedListener;

	private Calendar selectedCalendar = Calendar.getInstance();
	private boolean changeByCallingSelectDate = false;

	private boolean showDivider;

	public CalendarViewPager( Context context ) {

		this( context, null );
	}

	public CalendarViewPager( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView();
	}

	private void initView() {

		setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT ) );
		setPageMargin( (int) ScreenUtil.dipToPixel( getContext(), 12 ) );
		setOnPageChangeListener( onPageChangedListenerAdapter );
	}

	private OnPageChangedListenerAdapter onPageChangedListenerAdapter = new OnPageChangedListenerAdapter() {

		@Override
		protected void onPageChanged( int position ) {

			if ( !changeByCallingSelectDate && null != pagerAdapter ) {

				if ( ViewPagerMode.MONTH == viewPagerMode )
					selectedCalendar = CalendarUtil.getCalendarFromMonthIndex( position );
				else
					selectedCalendar = CalendarUtil.getCalendarFromWeekIndex( position );

				pagerAdapter.selectDate( selectedCalendar );
			}

			if ( null != onPageChangedListener )
				onPageChangedListener.onPageChanged( selectedCalendar, !changeByCallingSelectDate );

			changeByCallingSelectDate = false;
		}

	};

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

		int height = 0;
		for ( int i = 0, childCount = getChildCount(); i < childCount; i++ ) {

			View child = getChildAt( i );
			child.measure( widthMeasureSpec, MeasureSpec.makeMeasureSpec( 0, MeasureSpec.UNSPECIFIED ) );
			int h = child.getMeasuredHeight();

			if ( h > height )
				height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec( height, MeasureSpec.EXACTLY );

		super.onMeasure( widthMeasureSpec, heightMeasureSpec );
	}

	@Override
	public boolean dispatchTouchEvent( @NonNull MotionEvent ev ) {

		if ( ViewPagerMode.WEEK == viewPagerMode && 0 == ViewHelper.getAlpha( this ) )
			return false;
		else
			return super.dispatchTouchEvent( ev );
	}

	public void setOnPageChangedListener( OnPageChangedListener onPageChangedListener ) {

		this.onPageChangedListener = onPageChangedListener;
	}

	public void setShowDivider( boolean showDivider ) {

		this.showDivider = showDivider;

		if ( null != pagerAdapter )
			pagerAdapter.setShowDivider( showDivider );
	}

	public void init( ViewPagerMode viewPagerMode, CalendarViewPagerAdapter pagerAdapter, int initialIndex ) {

		this.viewPagerMode = viewPagerMode;
		this.pagerAdapter = pagerAdapter;
		this.pagerAdapter.setViewPager( this );
		pagerAdapter.setShowDivider( showDivider );
		setAdapter( pagerAdapter );
		setCurrentItem( initialIndex, false );
	}

	public void selectDate( @NonNull Calendar calendar ) {

		selectedCalendar.setTime( calendar.getTime() );

		int previousIndex = getCurrentItem();
		int newIndex = getIndex( calendar );

		if ( previousIndex != newIndex ) {

			changeByCallingSelectDate = true;
			boolean animation = ( View.VISIBLE == getVisibility() && 3 > Math.abs( newIndex - previousIndex ) );
			setCurrentItem( newIndex, animation );
		}

		if ( null != pagerAdapter )
			pagerAdapter.selectDate( calendar );
	}

	private int getIndex( Calendar calendar ) {

		if ( ViewPagerMode.MONTH == viewPagerMode )
			return CalendarUtil.getMonthIndexFrom( calendar );
		else
			return CalendarUtil.getWeekIndexFrom( calendar );
	}

	public void changeData() {

		pagerAdapter.onDataChanged();
	}

	public enum ViewPagerMode {

		MONTH, WEEK

	}

	public interface OnPageChangedListener {

		void onPageChanged( Calendar calendar, boolean fromScroll );

	}

}
