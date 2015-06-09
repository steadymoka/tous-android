package com.moka.framework.widget.calendar.util;


import java.util.Calendar;
import java.util.Date;


public class CalendarUtil {

	private static final int MIN_YEAR = 1978;
	private static Calendar minCalendar;
	private static Calendar baseCalendar;

	static {

		minCalendar = Calendar.getInstance();
		minCalendar.set( Calendar.YEAR, MIN_YEAR );
		minCalendar.set( Calendar.MONTH, 0 );
		minCalendar.set( Calendar.DAY_OF_MONTH, 1 );
		minCalendar.set( Calendar.HOUR_OF_DAY, 0 );
		minCalendar.set( Calendar.MINUTE, 0 );
		minCalendar.set( Calendar.SECOND, 0 );
		minCalendar.set( Calendar.MILLISECOND, 0 );

		baseCalendar = Calendar.getInstance();
	}

	private static final Calendar calendar_getMonthIndexFrom = Calendar.getInstance();

	public static int getMonthIndexFrom( Date date ) {

		calendar_getMonthIndexFrom.setTime( date );

		return getMonthIndexFrom( calendar_getMonthIndexFrom );
	}

	public static int getMonthIndexFrom( Calendar calendar ) {

		int year = calendar.get( Calendar.YEAR );
		int month = calendar.get( Calendar.MONTH );

		return getMonthIndexFrom( year, month );
	}

	public static int getMonthIndexFrom( int date ) {

		return getMonthIndexFrom( date / 10000, ( date % 10000 ) / 100 - 1 );
	}

	public static int getMonthIndexFrom( int year, int month ) {

		return Math.max( year - MIN_YEAR, 0 ) * 12 + month;
	}

	public static Date getDateFromMonthIndex( int monthIndex ) {

		return getCalendarFromMonthIndex( monthIndex ).getTime();
	}

	private static final Calendar calendar_getCalendarFromMonthIndex = Calendar.getInstance();

	public static Calendar getCalendarFromMonthIndex( int monthIndex ) {

		int year = monthIndex / 12 + MIN_YEAR;
		int month = monthIndex % 12;

		calendar_getCalendarFromMonthIndex.set( Calendar.YEAR, year );
		calendar_getCalendarFromMonthIndex.set( Calendar.MONTH, month );
		calendar_getCalendarFromMonthIndex.set( Calendar.DAY_OF_MONTH, 1 );

		return calendar_getCalendarFromMonthIndex;
	}

	private static final Calendar calendar_getWeekIndexFrom = Calendar.getInstance();

	public static int getWeekIndexFrom( Date date ) {

		calendar_getWeekIndexFrom.setTime( date );

		return getWeekIndexFrom( calendar_getWeekIndexFrom );
	}

	public static int getWeekIndexFrom( Calendar calendar ) {

		return getDiffDayCount( minCalendar, calendar ) / 7;
	}

	public static Date getDateFromWeekIndex( int weekIndex ) {

		return getCalendarFromWeekIndex( weekIndex ).getTime();
	}

	public static Calendar getCalendarFromWeekIndex( int weekIndex ) {

		Calendar calendar = (Calendar) minCalendar.clone();
		calendar.add( Calendar.DAY_OF_YEAR, weekIndex * 7 );

		return calendar;
	}

	private static final Calendar calendar_getDayIndexFrom = Calendar.getInstance();

	public static int getDayIndexFrom( Date date ) {

		calendar_getDayIndexFrom.setTime( date );

		return getDayIndexFrom( calendar_getDayIndexFrom );
	}

	public static int getDayIndexFrom( Calendar calendar ) {

		return getDiffDayCount( minCalendar, calendar );
	}

	public static Date getDateFromDayIndex( int dayIndex ) {

		return getCalendarFromDayIndex( dayIndex ).getTime();
	}

	public static Calendar getCalendarFromDayIndex( int dayIndex ) {

		Calendar calendar = (Calendar) minCalendar.clone();
		calendar.add( Calendar.DAY_OF_YEAR, dayIndex );

		return calendar;
	}

	public static int getDiffDayCount( Calendar fromDate, Calendar toDate ) {

		Calendar fromDateNew = (Calendar) fromDate.clone();
		fromDateNew.set( Calendar.HOUR_OF_DAY, 0 );
		fromDateNew.set( Calendar.MINUTE, 0 );
		fromDateNew.set( Calendar.SECOND, 0 );
		fromDateNew.set( Calendar.MILLISECOND, 0 );

		Calendar toDateNew = (Calendar) toDate.clone();
		toDateNew.set( Calendar.HOUR_OF_DAY, 0 );
		toDateNew.set( Calendar.MINUTE, 0 );
		toDateNew.set( Calendar.SECOND, 0 );
		toDateNew.set( Calendar.MILLISECOND, 0 );

		long fromTimeMillis = fromDateNew.getTimeInMillis();
		long toTimeMillis = toDateNew.getTimeInMillis();

		return (int) ( ( toTimeMillis - fromTimeMillis ) / ( 24 * 60 * 60 * 1000 ) );
	}

	public static Calendar getFirstDayOfCalendar( Calendar calendar ) {

		Calendar resultCalendar = (Calendar) calendar.clone();
		resultCalendar.set( Calendar.DAY_OF_MONTH, 1 );

		int dayOfWeek = resultCalendar.get( Calendar.DAY_OF_WEEK );
		int diff = dayOfWeek - Calendar.SUNDAY;
		resultCalendar.add( Calendar.DAY_OF_YEAR, -diff );

		return resultCalendar;
	}

	private static final Calendar calendar_getIntDate = Calendar.getInstance();

	public static int getIntDate( Date date ) {

		calendar_getIntDate.setTime( date );
		return getIntDate( calendar_getIntDate );
	}

	public static int getIntDate( Calendar calendar ) {

		int year = calendar.get( Calendar.YEAR );
		int month = calendar.get( Calendar.MONTH );
		int day = calendar.get( Calendar.DAY_OF_MONTH );

		return year * 10000 + ( month + 1 ) * 100 + day;
	}

	public static boolean isEquals( Date date1, Date date2 ) {

		int intDate1 = getIntDate( date1 );
		int intDate2 = getIntDate( date2 );

		return ( intDate1 == intDate2 );
	}

	public static Calendar getCalendar( Date date ) {

		Calendar calendar = (Calendar) baseCalendar.clone();
		calendar.setTime( date );

		return calendar;
	}

	public static Calendar getCalendar() {

		return (Calendar) baseCalendar.clone();
	}

}
