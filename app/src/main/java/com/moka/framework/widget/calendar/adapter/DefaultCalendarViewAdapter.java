package com.moka.framework.widget.calendar.adapter;


import android.content.Context;

import com.moka.framework.widget.calendar.model.DefaultCalendarCellData;
import com.moka.framework.widget.calendar.view.DefaultCalendarCellView;


public class DefaultCalendarViewAdapter extends CalendarViewAdapter<DefaultCalendarCellData, DefaultCalendarCellView> {

	public DefaultCalendarViewAdapter( Context context ) {

		super( context, DefaultCalendarCellView.class );
	}

	@Override
	protected DefaultCalendarCellView onCreateView( Context context ) {

		return new DefaultCalendarCellView( context );
	}

	@Override
	public DefaultCalendarCellData getCalendarCellData( int date ) {

		return new DefaultCalendarCellData( date );
	}

}
