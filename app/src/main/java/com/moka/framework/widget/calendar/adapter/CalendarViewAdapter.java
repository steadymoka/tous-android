package com.moka.framework.widget.calendar.adapter;


import android.content.Context;
import android.util.SparseArray;


import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.CalendarDataProvider;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.view.CalendarCellView;
import com.tous.application.util.DateUtil;

import java.util.HashMap;


public abstract class CalendarViewAdapter<DATA extends CalendarCellData, VIEW extends CalendarCellView<DATA>> {

	private Context context;

	private CalendarCellView.OnCalendarCellViewListener<DATA, CalendarCellView<DATA>> onCalendarCellViewListener;

	private HashMap<Boolean, SparseArray<SparseArray<DATA>>> dataHashByIndexByBelongTo = new HashMap<>();

	private OnDataChangeListener onDataChangeListener;

	private int todayDate;

	protected CalendarViewAdapter( Context context, Class<VIEW> viewClass ) {

		this.context = context;

		dataHashByIndexByBelongTo.put( true, new SparseArray<SparseArray<DATA>>() );
		dataHashByIndexByBelongTo.put( false, new SparseArray<SparseArray<DATA>>() );
	}

	@SuppressWarnings( { "unchecked", "ConstantConditions" } )
	public final CalendarCellView getCalendarCellView( CalendarCellView calendarCellView, int date, int currentMonthIndex, boolean belongToMonthView ) {

		VIEW view = (VIEW) calendarCellView;

		if ( null == view ) {

			view = onCreateView( context );
			view.setBelongToMonthView( belongToMonthView );
			view.setOnCalendarCellViewListener( onCalendarCellViewListener );
		}

		SparseArray<DATA> dataHash = getDataHash( belongToMonthView, currentMonthIndex );
		DATA data = dataHash.get( date );

		if ( null == data ) {

			data = getCalendarCellData( date );
			dataHash.put( date, data );
		}

		data.setCurrentMonth( currentMonthIndex == CalendarUtil.getMonthIndexFrom( date ) );
		data.setToday( todayDate == date );

		view.setData( data );

		return view;
	}

	protected abstract VIEW onCreateView( Context context );

	private SparseArray<DATA> getDataHash( boolean belongToMonthView, int index ) {

		SparseArray<SparseArray<DATA>> dataHashByIndex = dataHashByIndexByBelongTo.get( belongToMonthView );
		SparseArray<DATA> dataHash = dataHashByIndex.get( index );

		if ( null == dataHash ) {

			dataHash = new SparseArray<>();
			dataHashByIndex.put( index, dataHash );
		}

		return dataHash;
	}

	public abstract DATA getCalendarCellData( int date );

	public void setCalendarDataProvider( CalendarDataProvider calendarDataProvider ) {

		todayDate = DateUtil.formatToInt( calendarDataProvider.getTodayDate() );
	}

	public void setOnCalendarCellViewListener( CalendarCellView.OnCalendarCellViewListener<DATA, CalendarCellView<DATA>> onCalendarCellViewListener ) {

		this.onCalendarCellViewListener = onCalendarCellViewListener;
	}

	public void setOnDataChangeListener( OnDataChangeListener onDataChangeListener ) {

		this.onDataChangeListener = onDataChangeListener;
	}

	public void notifyDataSetChanged() {

		dataHashByIndexByBelongTo.clear();
		dataHashByIndexByBelongTo.put( true, new SparseArray<SparseArray<DATA>>() );
		dataHashByIndexByBelongTo.put( false, new SparseArray<SparseArray<DATA>>() );

		if ( null != onDataChangeListener )
			onDataChangeListener.onDataChanged();
	}

	public interface OnDataChangeListener {

		void onDataChanged();

	}

}
