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


public class WeekView extends ViewGroup {

	private SparseArray<CalendarCellView> calendarCellViewIndexHash = new SparseArray<>();
	private SparseArray<CalendarCellView> calendarCellViewHash = new SparseArray<>();

	private boolean showDivider;
	private Paint dividerPaint;

	private int weekIndex = 0;
	private CalendarViewAdapter calendarAdapter;

	public WeekView( Context context ) {

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
		int heightWithPadding = (int) ( ( cellSize + getPaddingTop() + getPaddingBottom() ) * 0.75f );

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

			int childLeft = left + childMeasuredWidth * i;
			int childRight = childLeft + childMeasuredWidth;
			int childBottom = (int) ( top + childMeasuredHeight * 0.75 );

			childView.layout( childLeft, top, childRight, childBottom );
		}
	}

	@Override
	protected void dispatchDraw( @NonNull Canvas canvas ) {

		super.dispatchDraw( canvas );

		if ( showDivider ) {

			float itemWidth = canvas.getWidth() / 7.0f;
			float height = canvas.getHeight();

			for ( int i = 0; i < 8; i++ ) {

				int x = (int) ( itemWidth * i );
				canvas.drawLine( x, 0, x, height, dividerPaint );
			}
		}
	}

	public void setShowDivider( boolean showDivider ) {

		this.showDivider = showDivider;
	}

	public void init( int weekIndex, CalendarDataProvider calendarDataProvider ) {

		this.weekIndex = weekIndex;
		Calendar calendar = CalendarUtil.getCalendarFromWeekIndex( weekIndex );
		calendarAdapter = calendarDataProvider.getCalendarViewAdapter();
		initCalendarViews( calendarDataProvider.getCalendarViewAdapter(), weekIndex, calendar );
	}

	private void initCalendarViews( final CalendarViewAdapter calendarAdapter, final int weekIndex, Calendar calendar ) {

		Calendar tempCalendar = (Calendar) calendar.clone();
		int startDate = DateUtil.formatToInt( tempCalendar.getTime() );

		tempCalendar.add( Calendar.DAY_OF_YEAR, 6 );
		int endDate = DateUtil.formatToInt( tempCalendar.getTime() );

		DateCounter.iterate( startDate, endDate, new DateCounter.Iterator() {

			@Override
			public void dateAt( int index, int date ) {

				CalendarCellView cachedCalendarCellView = calendarCellViewIndexHash.get( index );
				CalendarCellView calendarCellView = calendarAdapter
						.getCalendarCellView( cachedCalendarCellView, date, weekIndex, false );

				calendarCellViewIndexHash.put( index, calendarCellView );
				calendarCellViewHash.put( date, calendarCellView );

				if ( null == cachedCalendarCellView )
					addView( calendarCellView, index );
			}

		} );
	}

	public void refreshData() {

		Calendar tempCalendar = CalendarUtil.getCalendarFromWeekIndex( weekIndex );
		int startDate = DateUtil.formatToInt( tempCalendar.getTime() );

		tempCalendar.add( Calendar.DAY_OF_YEAR, 6 );
		int endDate = DateUtil.formatToInt( tempCalendar.getTime() );

		DateCounter.iterate( startDate, endDate, new DateCounter.Iterator() {

			@Override
			public void dateAt( int index, int date ) {

				CalendarCellView cachedCalendarCellView = calendarCellViewIndexHash.get( index );
				CalendarCellView calendarCellView = calendarAdapter
						.getCalendarCellView( cachedCalendarCellView, date, weekIndex, false );

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

	public static WeekView newInstance( Context context ) {

		return new WeekView( context );
	}

}
