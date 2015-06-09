package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.tous.application.util.DateUtil;

import java.util.Calendar;
import java.util.Date;


public class CalendarRowView extends ViewGroup {

	private CalendarViewAdapter calendarViewAdapter;
	private boolean belongToMonthView;
	private int selectedIndex;

	public CalendarRowView( Context context ) {

		this( context, null );
	}

	public CalendarRowView( Context context, AttributeSet attrs ) {

		super( context, attrs );
	}

	@Override
	public void addView( @NonNull View child, int index, LayoutParams params ) {

		if ( !( child instanceof CalendarCellView ) )
			throw new IllegalArgumentException( "" );

		super.addView( child, index, params );
	}

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

		final int totalWidth = MeasureSpec.getSize( widthMeasureSpec );
		int rowHeight = 0;

		for ( int c = 0, numChildren = getChildCount(); c < numChildren; c++ ) {

			final View child = getChildAt( c );

			double l = ( c * totalWidth ) / 7.0;
			double r = ( ( c + 1 ) * totalWidth ) / 7.0;
			int cellSize = (int) ( r - l );
			int cellWidthSpec = MeasureSpec.makeMeasureSpec( cellSize, MeasureSpec.EXACTLY );
			int cellHeightSpec = MeasureSpec.makeMeasureSpec( cellSize, MeasureSpec.EXACTLY );
			child.measure( cellWidthSpec, cellHeightSpec );

			if ( child.getMeasuredHeight() > rowHeight )
				rowHeight = child.getMeasuredHeight();
		}

		final int widthWithPadding = totalWidth + getPaddingLeft() + getPaddingRight();
		final int heightWithPadding = rowHeight + getPaddingTop() + getPaddingBottom();

		setMeasuredDimension( widthWithPadding, heightWithPadding );
	}

	@Override
	protected void onLayout( boolean changed, int left, int top, int right, int bottom ) {

		int cellHeight = bottom - top;
		int width = ( right - left );
		for ( int c = 0, numChildren = getChildCount(); c < numChildren; c++ ) {

			final View child = getChildAt( c );
			int l = ( c * width ) / 7;
			int r = ( ( c + 1 ) * width ) / 7;
			child.layout( l, 0, r, cellHeight );
		}
	}

	public void setCalendarViewAdapter( CalendarViewAdapter calendarViewAdapter ) {

		this.calendarViewAdapter = calendarViewAdapter;
	}

	public void setBelongToMonthView( boolean belongToMonthView ) {

		this.belongToMonthView = belongToMonthView;
	}

	public void setSelectedIndex( int selectedIndex ) {

		this.selectedIndex = selectedIndex;
	}

	public void setFirstDayOfWeek( Calendar calendar, SparseArray<CalendarCellView> calendarCellViewHash ) {

		for ( int i = 0; i < 7; i++ ) {

			Date date = calendar.getTime();

			CalendarCellView calendarCellView = (CalendarCellView) getChildAt( i );
			CalendarCellView newCalendarCellView = calendarViewAdapter
					.getCalendarCellView( calendarCellView, DateUtil.formatToInt( date ), selectedIndex, belongToMonthView );
			int intDate = CalendarUtil.getIntDate( calendar );
			calendarCellViewHash.put( intDate, newCalendarCellView );

			if ( null == calendarCellView )
				addView( newCalendarCellView );

			calendar.add( Calendar.DAY_OF_YEAR, 1 );
		}
	}

}
