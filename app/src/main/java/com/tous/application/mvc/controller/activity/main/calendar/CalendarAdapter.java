package com.tous.application.mvc.controller.activity.main.calendar;


import android.content.Context;

import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.calendar.CalendarItemView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CalendarAdapter extends CalendarViewAdapter<CalendarItemData, CalendarItemView> {

	private Plan plan;
	private final ExecutorService executor = Executors.newFixedThreadPool( 10 );

	public CalendarAdapter( Context context ) {

		super( context, CalendarItemView.class );
	}

	@Override
	protected CalendarItemView onCreateView( Context context ) {

		return new CalendarItemView( context );
	}

	@Override
	public CalendarItemData getCalendarCellData( int date ) {

		CalendarItemData calendarItemData = new CalendarItemData( date, plan );
		calendarItemData.setExecutorService( executor );

		return calendarItemData;
	}

	public Plan getPlan() {

		return plan;
	}

	public void setPlan( Plan plan ) {

		this.plan = plan;
		notifyDataSetChanged();
	}

	public void shutdownExecutorService() {

		executor.shutdown();
	}

}
