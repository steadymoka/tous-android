package com.tous.application.util;


import android.content.Context;

import com.moka.framework.util.CrashlyticsUtil;
import com.tous.application.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DateUtil {

	private static Context context;

	public static void setContext( Context context ) {

		DateUtil.context = context;
	}

	private static final SimpleDateFormat dateFormat_parseDate = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault() );

	public static Date parseDate( String date ) {

		try {

			synchronized ( dateFormat_parseDate ) {

				return dateFormat_parseDate.parse( date );
			}
		}
		catch ( ParseException parseException ) {

			CrashlyticsUtil.s( parseException );
			return new Date();
		}
	}

	public static Date parseDate( int dateTime ) {

		return parseDate( String.valueOf( dateTime ) );
	}

	public static Date parseDate( long dateTime ) {

		return parseDate( String.valueOf( dateTime ) );
	}

	public static long getTimestamp() {

		return System.currentTimeMillis() / 1000;
	}

	private static final SimpleDateFormat dateFormat_formatToInt = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault() );

	public static int formatToInt( Date date ) {

		synchronized ( dateFormat_formatToInt ) {

			return Integer.parseInt( dateFormat_formatToInt.format( date ) );
		}
	}

	public static Date getTodayDate() {

		return Calendar.getInstance().getTime();
	}

	public static String formatIntDateToString( int date ) {

		SimpleDateFormat dateFormat = new SimpleDateFormat( "MM월 dd일", Locale.getDefault() );
		return dateFormat.format( parseDate( date ) );
	}

	public static int getDiffDayCount( Date fromDate, Date toDate ) {

		int fromDateInt = formatToInt( fromDate );
		int toDateInt = formatToInt( toDate );

		Date fromDateNew = parseDate( fromDateInt );
		Date toDateNew = parseDate( toDateInt );

		return (int) ( ( toDateNew.getTime() - fromDateNew.getTime() ) / ( 24 * 60 * 60 * 1000 ) );
	}

	private static final SimpleDateFormat dateFormat_getDiffDayCount = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault() );

	public static int getDiffDayCount( String fromDate, String toDate ) {

		try {

			synchronized ( dateFormat_getDiffDayCount ) {

				return getDiffDayCount( dateFormat_getDiffDayCount.parse( fromDate ), dateFormat_getDiffDayCount.parse( toDate ) );
			}
		}
		catch ( ParseException parseException ) {

			CrashlyticsUtil.s( parseException );
			return 0;
		}
	}

	public static int getDiffDayCount( int fromDate, int toDate ) {

		return getDiffDayCount( String.valueOf( fromDate ), String.valueOf( toDate ) );
	}

	private static final Calendar calendar_getDiffDateFromRealToday = Calendar.getInstance();

	public static Date getDiffDateFromToday( int diff ) {

		Date todayDate = getTodayDate();

		synchronized ( calendar_getDiffDateFromRealToday ) {

			calendar_getDiffDateFromRealToday.setTime( todayDate );
			calendar_getDiffDateFromRealToday.add( GregorianCalendar.DAY_OF_YEAR, diff );

			return calendar_getDiffDateFromRealToday.getTime();
		}
	}

	private static final SimpleDateFormat dateFormatTimeStamp_formatToTimestamp = new SimpleDateFormat( "yyyyMMdd_HHmmss", Locale.getDefault() );

	public static String formatToTimestamp( Date date ) {

		synchronized ( dateFormatTimeStamp_formatToTimestamp ) {

			return dateFormatTimeStamp_formatToTimestamp.format( date );
		}
	}

	private static final Calendar calendar_addDate = Calendar.getInstance();

	public static Date addDate( Date date, int value ) {

		synchronized ( calendar_addDate ) {

			calendar_addDate.setTime( date );
			calendar_addDate.add( Calendar.DATE, value );

			return calendar_addDate.getTime();
		}
	}

	public static String formatToString( Date date ) {

		SimpleDateFormat dateFormat = new SimpleDateFormat( context.getString( R.string.fragment_plan_create_button_date_format ), Locale.getDefault() );

		return dateFormat.format( date );
	}

}
