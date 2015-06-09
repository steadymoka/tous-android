package com.moka.framework.widget.calendar.adapter;


import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import com.moka.framework.widget.calendar.util.CalendarDataProvider;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.view.CalendarCellView;
import com.moka.framework.widget.calendar.view.MonthView;
import com.tous.application.util.DateUtil;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;


public class MonthViewPagerAdapter extends CalendarViewPagerAdapter {

	private Context context;
	private CalendarDataProvider calendarDataProvider;
	private SparseArray<MonthView> currentViewHash = new SparseArray<>();
	private Queue<MonthView> monthViewQueue = new LinkedList<>();

	private CalendarCellView selectedCalendarCellView;

	public MonthViewPagerAdapter( Context context, CalendarDataProvider calendarDataProvider ) {

		this.context = context;
		this.calendarDataProvider = calendarDataProvider;
	}

	@Override
	public int getCount() {

		return 5000;
	}

	@Override
	public boolean isViewFromObject( View view, Object object ) {

		return ( view == object );
	}

	@Override
	public Object instantiateItem( ViewGroup container, int position ) {

		MonthView monthView = getMonthView();
		monthView.init( position, calendarDataProvider );
		monthView.setShowDivider( isShowDivider() );

		container.addView( monthView );
		currentViewHash.put( position, monthView );

		return monthView;
	}

	private MonthView getMonthView() {

		MonthView monthView = monthViewQueue.poll();

		if ( null == monthView )
			monthView = MonthView.newInstance( context );

		return monthView;
	}

	@Override
	public void destroyItem( ViewGroup container, int position, Object object ) {

		MonthView monthView = (MonthView) object;
		container.removeView( monthView );
		currentViewHash.remove( position );

		releaseMonthView( monthView );
	}

	private void releaseMonthView( MonthView monthView ) {

		monthViewQueue.offer( monthView );
	}

	@Override
	protected void onSelectDate( Calendar calendar ) {

		deselectSelectedCalendarCellView();
		selectCalendarCellView( calendar );
	}

	@Override
	public void onDataChanged() {

	}

	private void deselectSelectedCalendarCellView() {

		if ( null != selectedCalendarCellView ) {

			selectedCalendarCellView.getData().setSelected( false );
			selectedCalendarCellView.refreshView();
		}
	}

	private void selectCalendarCellView( Calendar calendar ) {

		int monthIndex = CalendarUtil.getMonthIndexFrom( calendar );
		MonthView monthView = currentViewHash.get( monthIndex );

		if ( null != monthView ) {

			int selectedDate = DateUtil.formatToInt( calendar.getTime() );
			selectedCalendarCellView = monthView.getCalendarCellViewAt( selectedDate );
			selectedCalendarCellView.getData().setSelected( true );
			selectedCalendarCellView.refreshView();
		}
	}

	public int getSelectedRowDistance() {

		if ( null != selectedCalendarCellView ) {

			int[] location = new int[2];
			getViewPager().getLocationOnScreen( location );
			int top = location[1];

			selectedCalendarCellView.getLocationOnScreen( location );
			int cellViewTop = location[1];

			return top - cellViewTop;
		}

		return 0;
	}

}
