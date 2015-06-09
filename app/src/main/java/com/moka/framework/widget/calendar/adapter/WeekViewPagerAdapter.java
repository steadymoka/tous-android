package com.moka.framework.widget.calendar.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import com.moka.framework.widget.calendar.util.CalendarDataProvider;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.view.CalendarCellView;
import com.moka.framework.widget.calendar.view.WeekView;
import com.tous.application.util.DateUtil;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;


public class WeekViewPagerAdapter extends CalendarViewPagerAdapter {

	private Context context;
	private CalendarDataProvider calendarDataProvider;
	private SparseArray<WeekView> currentViewHash = new SparseArray<>();
	private Queue<WeekView> weekViewQueue = new LinkedList<>();

	private CalendarCellView selectedCalendarCellView;

	public WeekViewPagerAdapter( Context context, CalendarDataProvider calendarDataProvider ) {

		this.context = context;
		this.calendarDataProvider = calendarDataProvider;
	}

	@Override
	public int getCount() {

		return 10000;
	}

	@Override
	public boolean isViewFromObject( View view, Object object ) {

		return ( view == object );
	}

	@Override
	public Object instantiateItem( ViewGroup container, int position ) {

		WeekView weekView = getWeekView();
		weekView.init( position, calendarDataProvider );
		weekView.setShowDivider( isShowDivider() );

		container.addView( weekView );
		currentViewHash.put( position, weekView );

		return weekView;
	}

	private WeekView getWeekView() {

		WeekView weekView = weekViewQueue.poll();

		if ( null == weekView )
			weekView = WeekView.newInstance( context );

		return weekView;
	}

	@Override
	public void destroyItem( ViewGroup container, int position, Object object ) {

		WeekView weekView = (WeekView) object;
		container.removeView( weekView );
		currentViewHash.remove( position );

		releaseWeekView( weekView );
	}

	private void releaseWeekView( WeekView weekView ) {

		weekViewQueue.offer( weekView );
	}

	@Override
	protected void onSelectDate( @NonNull Calendar calendar ) {

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

		int weekIndex = CalendarUtil.getWeekIndexFrom( calendar );
		WeekView weekView = currentViewHash.get( weekIndex );

		if ( null != weekView ) {

			int selectedDate = DateUtil.formatToInt( calendar.getTime() );
			selectedCalendarCellView = weekView.getCalendarCellViewAt( selectedDate );
			selectedCalendarCellView.getData().setSelected( true );
			selectedCalendarCellView.refreshView();
		}
	}

}
