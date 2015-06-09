package com.tous.application.mvc.controller.activity.main.calendar;


import android.os.Handler;

import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.tous.application.mvc.model.plan.Plan;

import java.util.concurrent.ExecutorService;


public class CalendarItemData extends CalendarCellData implements Runnable {

	private Plan plan;

	//	private AsyncDataProcessor asyncDataProcessor;
	private ExecutorService executor;
	private Handler handler;

	public CalendarItemData( int date, Plan plan ) {

		super( date );
		this.plan = plan;
	}

	public Plan getPlan() {

		return plan;
	}

	public boolean isPlanningDay() {

		return plan.isPlaningDate( getIntDate() );
	}

	public void setExecutorService( ExecutorService executor ) {

		this.executor = executor;
	}

	private Listener listener;

	public void makeData( Handler handler, Listener listener ) {

		this.handler = handler;
		this.listener = listener;
		executor.execute( this );
	}

	@Override
	public void run() {

		makeDataFromDay();
		handler.post( new Runnable() {

			@Override
			public void run() {

				if ( null != listener )
					listener.onDataMade( CalendarItemData.this );
			}

		} );
	}

	private void makeDataFromDay() {

	}

	public interface Listener {

		void onDataMade( CalendarItemData calendarItemData );

	}

}
