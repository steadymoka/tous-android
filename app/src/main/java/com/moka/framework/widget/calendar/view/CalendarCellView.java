package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.widget.calendar.model.CalendarCellData;


public abstract class CalendarCellView<DATA extends CalendarCellData>
		extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {

	private DATA data;

	private boolean belongToMonthView;

	private OnCalendarCellViewListener<DATA, CalendarCellView<DATA>> onCalendarCellViewListener;

	public CalendarCellView( Context context ) {

		this( context, null );
	}

	public CalendarCellView( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView();
	}

	private void initView() {

		setOnClickListener( this );
	}

	protected int getCellViewResId() {

		return 0;
	}

	protected void onLayoutInflated() {

	}

	@Override
	protected void onLayout( boolean changed, int l, int t, int r, int b ) {

	}

	@Override
	public void onClick( View view ) {

		if ( null != onCalendarCellViewListener )
			onCalendarCellViewListener.onClick( this );
	}

	@Override
	public boolean onLongClick( View view ) {

		if ( null != onCalendarCellViewListener )
			return onCalendarCellViewListener.onLongClick( this );

		return false;
	}

	public final DATA getData() {

		return data;
	}

	public final void setData( DATA data ) {

		this.data = data;
		refreshView( data );
	}

	protected abstract void refreshView( DATA data );

	public void refreshView() {

		if ( null != data )
			refreshView( data );
	}

	public boolean isBelongToMonthView() {

		return belongToMonthView;
	}

	public void setBelongToMonthView( boolean belongToMonthView ) {

		this.belongToMonthView = belongToMonthView;
	}

	public void setOnCalendarCellViewListener( OnCalendarCellViewListener<DATA, CalendarCellView<DATA>> onCalendarCellViewListener ) {

		this.onCalendarCellViewListener = onCalendarCellViewListener;
	}

	public interface OnCalendarCellViewListener<DATA extends CalendarCellData, VIEW extends CalendarCellView<DATA>> {

		void onClick( VIEW cellView );

		boolean onLongClick( VIEW cellView );

	}

}
