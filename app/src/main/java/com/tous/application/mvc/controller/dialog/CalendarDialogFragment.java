package com.tous.application.mvc.controller.dialog;


import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.moka.framework.controller.BaseDialogFragment;
import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.CalendarViewMode;
import com.moka.framework.widget.calendar.util.DateProvider;
import com.moka.framework.widget.calendar.view.CalendarView;
import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.mvc.controller.activity.main.calendar.CalendarAdapter;
import com.tous.application.mvc.model.plan.Plan;


public class CalendarDialogFragment extends BaseDialogFragment implements OnClickListener, CalendarView.CalendarListener, DateProvider {

	private View rootView;

	private TextView textView_month;
	private CalendarView calendarView;
	private TextView textView_cancle;
	private TextView textView_delete;
	private long spotId = -1;
	private long planId = -1;

	private OnCalendarListener onCalendarListener;

	private CalendarAdapter calendarAdapter;

	@NonNull
	@SuppressLint( "InflateParams" )
	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {

		rootView = getActivity().getLayoutInflater().inflate( R.layout.dialog_calendar, null );

		initView();
		bindView();

		Builder builder = new Builder( getActivity() );
		builder.setView( rootView );

		return builder.create();
	}

	private void initView() {

		textView_month = (TextView) rootView.findViewById( R.id.textView_month );
		textView_month.setBackgroundResource( R.color.base_app_color_red );

		calendarView = (CalendarView) rootView.findViewById( R.id.calendarView );
		calendarView.setCalendarListener( this );
		calendarView.setDateProvider( this );
		calendarView.setScrollable( false );
		calendarView.init( getCalendarViewAdapter(), CalendarViewMode.MONTH );

//		textView_cancle = (TextView) rootView.findViewById( R.id.textView_cancle );
//		textView_cancle.setOnClickListener( this );

		textView_delete = (TextView) rootView.findViewById( R.id.textView_delete );
		textView_delete.setOnClickListener( this );
	}

	private CalendarViewAdapter getCalendarViewAdapter() {

		if ( null == calendarAdapter )
			calendarAdapter = new CalendarAdapter( getActivity() );

		return calendarAdapter;
	}

	private void bindView() {

		Plan plan = PlanTable.from( getActivity() ).find( planId );
		calendarAdapter.setPlan( plan );
	}

	@Override
	public void onMonthChanged( int year, int month ) {

		textView_month.setText( String.format( "%04d.%02d", year, month + 1 ) );
	}

	@Override
	public void onDateSelected( CalendarCellData data ) {

	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

//			case R.id.textView_cancle:
//
//				dismiss();
//				break;

			case R.id.textView_delete:

				dismiss();
				break;
		}
	}

	@Override
	public void onDismiss( DialogInterface dialog ) {

		super.onDismiss( dialog );
	}

	public CalendarDialogFragment setOnCalendarListener( OnCalendarListener onCalendarListener ) {

		this.onCalendarListener = onCalendarListener;
		return this;
	}

	public CalendarDialogFragment setSpotId( long spotId ) {

		this.spotId = spotId;
		return this;
	}

	public CalendarDialogFragment setPlan( long planId ) {

		this.planId = planId;
		return this;
	}

	public void showDialog( FragmentManager fragmentManager, OnCalendarListener onCalendarListener ) {

		setOnCalendarListener( onCalendarListener );
		show( fragmentManager, "setOnCalendarListener" );
	}

	public static CalendarDialogFragment newInstance() {

		return new CalendarDialogFragment();
	}

	public interface OnCalendarListener {

		void onDelete();

		void onCancle();

	}

}
