package com.moka.framework.widget.calendar.adapter;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Calendar;


public abstract class CalendarViewPagerAdapter extends PagerAdapter {

	private ViewPager viewPager;
	private Calendar calendar;
	private boolean showDivider;

	public ViewPager getViewPager() {

		return viewPager;
	}

	public void setViewPager( ViewPager viewPager ) {

		this.viewPager = viewPager;
	}

	public Calendar getSelectedDate() {

		return calendar;
	}

	public void selectDate( Calendar calendar ) {

		this.calendar = (Calendar) calendar.clone();
		onSelectDate( this.calendar );
	}

	protected abstract void onSelectDate( Calendar calendar );

	public boolean isShowDivider() {

		return showDivider;
	}

	public void setShowDivider( boolean showDivider ) {

		this.showDivider = showDivider;
	}

	public abstract void onDataChanged();

}
