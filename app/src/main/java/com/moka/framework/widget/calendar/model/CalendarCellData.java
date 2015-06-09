package com.moka.framework.widget.calendar.model;



import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.tous.application.util.DateUtil;

import java.util.Calendar;
import java.util.Date;


public class CalendarCellData {

	private int intDate;
	private Calendar calendar;
	private Date date;
	private boolean selected = false;
	private boolean currentMonth = false;
	private boolean today = false;

	public CalendarCellData( int intDate ) {

		this.intDate = intDate;
	}

	public int getIntDate() {

		return intDate;
	}

	public final Date getDate() {

		if ( null == date )
			date = DateUtil.parseDate( intDate );

		return date;
	}

	public boolean isSelected() {

		return selected;
	}

	public void setSelected( boolean selected ) {

		this.selected = selected;
	}

	public final boolean isCurrentMonth() {

		return currentMonth;
	}

	public final void setCurrentMonth( boolean currentMonth ) {

		this.currentMonth = currentMonth;
	}

	public final boolean isToday() {

		return today;
	}

	public final void setToday( boolean today ) {

		this.today = today;
	}

	public final int getYear() {

		return intDate / 10000;
	}

	public final int getMonth() {

		return ( intDate % 10000 ) / 100;
	}

	public final int getDayOfMonth() {

		return intDate % 100;
	}

	public final int getDayOfWeek() {

		if ( null == calendar ) {

			calendar = CalendarUtil.getCalendar();
			calendar.setTime( getDate() );
		}

		return calendar.get( Calendar.DAY_OF_WEEK );
	}

}
