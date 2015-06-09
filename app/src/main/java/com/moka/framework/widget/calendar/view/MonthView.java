package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.util.CalendarDataProvider;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.tous.application.util.DateCounter;
import com.tous.application.util.DateUtil;

import java.util.Calendar;


public class MonthView extends ViewGroup {

	private SparseArray<CalendarCellView> calendarCellViewIndexHash = new SparseArray<>();
	private SparseArray<CalendarCellView> calendarCellViewHash = new SparseArray<>();

	private boolean showDivider;
	private Paint dividerPaint;

	private int monthIndex = 0;
	private CalendarViewAdapter calendarAdapter;

	public MonthView( Context context ) {

		super( context );

		dividerPaint = new Paint();
		dividerPaint.setStrokeWidth( 1 );
		dividerPaint.setColor( 0xFFE6E6E6 );
	}

	@Override
	public void addView( @NonNull View child, int index, LayoutParams params ) {

		if ( !( child instanceof CalendarCellView ) )
			throw new IllegalArgumentException( "" );

		super.addView( child, index, params );
	}

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

		int widthSize = MeasureSpec.getSize( widthMeasureSpec ) - getPaddingLeft() - getPaddingRight();
		int width = Math.max( widthSize, 0 );
		int cellSize = (int) ( width / 7.0 );

		for ( int i = 0, numOfChild = getChildCount(); i < numOfChild; i++ ) {

			View child = getChildAt( i );

			int cellWidthSpec = MeasureSpec.makeMeasureSpec( cellSize, MeasureSpec.EXACTLY );
			int cellHeightSpec = MeasureSpec.makeMeasureSpec( cellSize, MeasureSpec.EXACTLY );
			child.measure( cellWidthSpec, cellHeightSpec );
		}

		int widthWithPadding = width + getPaddingLeft() + getPaddingRight();
		int heightWithPadding = cellSize * 6 + getPaddingTop() + getPaddingBottom();

		setMeasuredDimension( widthWithPadding, heightWithPadding );
	}

	@Override
	protected void onLayout( boolean changed, int l, int t, int r, int b ) {

		int left = getPaddingLeft();
		int top = getPaddingTop();

		for ( int i = 0, childCount = getChildCount(); i < childCount; i++ ) {

			View childView = getChildAt( i );

			int childMeasuredWidth = childView.getMeasuredWidth();
			int childMeasuredHeight = childView.getMeasuredHeight();

			int x = i % 7;
			int y = i / 7;

			int childLeft = left + childMeasuredWidth * x;
			int childTop = top + childMeasuredHeight * y;
			int childRight = childLeft + childMeasuredWidth;
			int childBottom = childTop + childMeasuredHeight;

			childView.layout( childLeft, childTop, childRight, childBottom );
		}
	}

	@Override
	protected void dispatchDraw( @NonNull Canvas canvas ) {

		super.dispatchDraw( canvas );

		if ( showDivider ) {

			float height = canvas.getHeight();
			int last = 0;

			for ( int i = 0; i < 7; i++ ) {

				View childView = getChildAt( i );
				int childWidth = childView.getMeasuredWidth();
				int x = childWidth * i;
				last += childWidth;
				canvas.drawLine( x, 0, x, height, dividerPaint );
			}

			canvas.drawLine( last, 0, last, height, dividerPaint );

			for ( int i = 0; i < 5; i++ ) {

				View childView = getChildAt( i * 7 );
				int y = childView.getMeasuredHeight() * ( i + 1 );
				canvas.drawLine( 0, y, last, y, dividerPaint );
			}
		}
	}

	public void setShowDivider( boolean showDivider ) {

		this.showDivider = showDivider;
	}

	public void init( final int monthIndex, final CalendarDataProvider calendarDataProvider ) {

		this.monthIndex = monthIndex;
		final Calendar calendar = CalendarUtil.getCalendarFromMonthIndex( monthIndex );
		calendarAdapter = calendarDataProvider.getCalendarViewAdapter();
		initCalendarViews( calendarAdapter, monthIndex, calendar );
	}

	private void initCalendarViews( final CalendarViewAdapter calendarAdapter, final int monthIndex, Calendar calendar ) {

		Calendar tempCalendar = CalendarUtil.getFirstDayOfCalendar( calendar );
		int startDate = DateUtil.formatToInt( tempCalendar.getTime() );

		tempCalendar.add( Calendar.DAY_OF_YEAR, 41 );
		int endDate = DateUtil.formatToInt( tempCalendar.getTime() );

		DateCounter.iterate( startDate, endDate, new DateCounter.Iterator() {

			@Override
			public void dateAt( int index, int date ) {

				CalendarCellView cachedCalendarCellView = calendarCellViewIndexHash.get( index );
				CalendarCellView calendarCellView = calendarAdapter
						.getCalendarCellView( cachedCalendarCellView, date, monthIndex, true );

				calendarCellViewIndexHash.put( index, calendarCellView );
				calendarCellViewHash.put( date, calendarCellView );

				if ( null == cachedCalendarCellView )
					addView( calendarCellView, index );
			}

		} );
	}

	public void refreshData() {

		Calendar calendar = CalendarUtil.getCalendarFromMonthIndex( monthIndex );
		Calendar tempCalendar = CalendarUtil.getFirstDayOfCalendar( calendar );
		int startDate = DateUtil.formatToInt( tempCalendar.getTime() );

		tempCalendar.add( Calendar.DAY_OF_YEAR, 41 );
		int endDate = DateUtil.formatToInt( tempCalendar.getTime() );

		DateCounter.iterate( startDate, endDate, new DateCounter.Iterator() {

			@Override
			public void dateAt( int index, int date ) {

				CalendarCellView cachedCalendarCellView = calendarCellViewIndexHash.get( index );
				CalendarCellView calendarCellView = calendarAdapter
						.getCalendarCellView( cachedCalendarCellView, date, monthIndex, true );

				calendarCellViewIndexHash.put( index, calendarCellView );
				calendarCellViewHash.put( date, calendarCellView );

				if ( null == cachedCalendarCellView )
					addView( calendarCellView, index );
			}

		} );
	}

	public CalendarCellView getCalendarCellViewAt( int date ) {

		return calendarCellViewHash.get( date );
	}

	public static MonthView newInstance( Context context ) {

		return new MonthView( context );
	}

}
