package com.tous.application.mvc.view.plandetail.shedule;


import android.support.v4.view.PagerAdapter;

import com.moka.framework.view.LayoutListener;
import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.DateProvider;


public interface ScheduleFragmentLayoutListener extends LayoutListener {

	PagerAdapter getSchedulePagerAdapter();

	void onPageSelected( int position );

	void onClickCalendar();

	void onClickMap();

}
