package com.moka.framework.widget.calendar.util;



import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;

import java.util.Date;


public interface CalendarDataProvider {

	Date getTodayDate();

	CalendarViewAdapter getCalendarViewAdapter();

}
