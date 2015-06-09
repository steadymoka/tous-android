package com.tous.application.util;


public class DateCounter {

	private static final int YEAR = 0;
	private static final int MONTH = 1;
	private static final int DAY = 2;

	private static final int[][] COUNT_OF_DAY;

	static {

		COUNT_OF_DAY = new int[][]{
				{ 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }, // 평년
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } // 윤년
		};
	}

	public static void iterate( int startDate, int endDate, Iterator iterator ) {

		if ( null != iterator ) {

			int[] cursor = getDateFieldsFrom( startDate );

			for ( int i = 0, c = startDate; c <= endDate; i++, c = getIntDateFrom( cursor ) ) {

				iterator.dateAt( i, c );
				plusDay( cursor );
			}
		}
	}

	static int[] getDateFieldsFrom( int date ) {

		return new int[]{ date / 10000, ( date % 10000 ) / 100, date % 100 };
	}

	static int getIntDateFrom( int[] dateFields ) {

		int result = dateFields[YEAR] * 10000;
		result += dateFields[MONTH] * 100;
		result += dateFields[DAY];

		return result;
	}

	static int isLeapYear( int year ) {

		return ( 0 == year % 4 ) ? ( 1 ) : ( 0 );
	}

	static void plusDay( int[] dateFields ) {

		if ( COUNT_OF_DAY[isLeapYear( dateFields[YEAR] )][dateFields[MONTH]] == dateFields[DAY] ) {

			plusMonth( dateFields );
			dateFields[DAY] = 1;
		}
		else {

			dateFields[DAY] = dateFields[DAY] + 1;
		}
	}

	static void plusMonth( int[] dateFields ) {

		if ( 12 == dateFields[MONTH] ) {

			plusYear( dateFields );
			dateFields[MONTH] = 1;
		}
		else {

			dateFields[MONTH] = dateFields[MONTH] + 1;
		}

		int leapYear = isLeapYear( dateFields[YEAR] );
		if ( COUNT_OF_DAY[leapYear][dateFields[MONTH]] < dateFields[DAY] )
			dateFields[DAY] = COUNT_OF_DAY[leapYear][dateFields[MONTH]];
	}

	static void plusYear( int[] dateFields ) {

		dateFields[YEAR] = dateFields[YEAR] + 1;

		int leapYear = isLeapYear( dateFields[YEAR] );
		if ( COUNT_OF_DAY[leapYear][dateFields[MONTH]] < dateFields[DAY] )
			dateFields[DAY] = COUNT_OF_DAY[leapYear][dateFields[MONTH]];
	}

	public interface Iterator {

		void dateAt( int index, int date );

	}

}
