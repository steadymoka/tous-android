package com.tous.application.mvc.controller.activity.plancreation;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plancreation.PlanCreationFragmentLayout;
import com.tous.application.mvc.view.plancreation.PlanCreationFragmentLayoutListener;
import com.tous.application.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class PlanCreationFragment extends BaseFragment implements PlanCreationFragmentLayoutListener {

	private long planId;
	private boolean editMode = false;

	private GregorianCalendar startDateCalendar;
	private GregorianCalendar endDateCalendar;
	private SimpleDateFormat dateFormat;

	private CalendarDatePickerDialog.OnDateSetListener startDateSetListener;
	private CalendarDatePickerDialog.OnDateSetListener endDateSetListener;

	private PlanCreationFragmentLayout fragmentLayout;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new PlanCreationFragmentLayout( this, this, inflater, container );
		setHasOptionsMenu( true );

		if ( editMode )
			setPlanForEdit( planId );
		else
			initData();

		return fragmentLayout.getRootView();
	}

	private void setPlanForEdit( long planId ) {

		Plan plan = PlanTable.from( getActivity() ).find( planId );

		fragmentLayout.setPlanName( plan.getName() );
		fragmentLayout.setPlanContent( plan.getContent() );
		setPeriod( DateUtil.parseDate( plan.getStartDate() ), DateUtil.parseDate( plan.getEndDate() ) );
	}

	private void initData() {

		setPeriod( DateUtil.getTodayDate(), DateUtil.getDiffDateFromToday( 5 ) );
	}

	private void setPeriod( Date startDate, Date endDate ) {

		startDateCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		startDateCalendar.setTime( startDate );

		endDateCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		endDateCalendar.setTime( endDate );

		fragmentLayout.setStartDate( formatToString( startDate ) );
		fragmentLayout.setEndDate( formatToString( endDate ) );
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		fragmentLayout.onCreateOptionsMenu( menu, inflater );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		if ( fragmentLayout.onOptionsItemSelected( item ) )
			return true;
		else
			return super.onOptionsItemSelected( item );
	}

	@Override
	public void onSavePlan() {

		Plan plan = getPlan();

		if ( isValidPlan( plan ) ) {

			PlanTable.from( getActivity() ).insert( plan );
			OttoUtil.getInstance().post( new OnRefreshViewEvent() );
			getActivity().finish();
		}
	}

	private Plan getPlan() {

		Plan plan = new Plan();

		plan.setName( fragmentLayout.getPlanName() );
		plan.setContent( fragmentLayout.getPlanContent() );
		plan.setStartDate( DateUtil.formatToInt( startDateCalendar.getTime() ) );
		plan.setEndDate( DateUtil.formatToInt( endDateCalendar.getTime() ) );

		return plan;
	}

	private boolean isValidPlan( Plan plan ) {

		if ( TextUtils.isEmpty( plan.getName() ) ) {

			fragmentLayout.setErrorPlanName();
			return false;
		}

		return true;
	}

	@Override
	public void onShowStartDatePicker() {

		if ( null == startDateSetListener ) {

			startDateSetListener = new CalendarDatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet( CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth ) {

					startDateCalendar.set( GregorianCalendar.YEAR, year );
					startDateCalendar.set( GregorianCalendar.MONTH, monthOfYear );
					startDateCalendar.set( GregorianCalendar.DAY_OF_MONTH, dayOfMonth );

					fragmentLayout.setStartDate( formatToString( startDateCalendar.getTime() ) );

					if ( !isValidateDate( DateUtil.formatToInt( startDateCalendar.getTime() ), DateUtil.formatToInt( endDateCalendar.getTime() ) ) ) {

						endDateCalendar.set( GregorianCalendar.YEAR, year );
						endDateCalendar.set( GregorianCalendar.MONTH, monthOfYear );
						endDateCalendar.set( GregorianCalendar.DAY_OF_MONTH, dayOfMonth );

						fragmentLayout.setEndDate( formatToString( endDateCalendar.getTime() ) );
					}
				}
			};
		}

		int startYear = startDateCalendar.get( GregorianCalendar.YEAR );
		int startMonth = startDateCalendar.get( GregorianCalendar.MONTH );
		int startDay = startDateCalendar.get( GregorianCalendar.DAY_OF_MONTH );

		CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog.newInstance( startDateSetListener, startYear, startMonth, startDay );
		calendarDatePickerDialog.show( getFragmentManager(), "CalendarDatePickerDialog" );
	}

	private String formatToString( Date date ) {

		if ( null == dateFormat )
			dateFormat = new SimpleDateFormat( getString( R.string.fragment_plan_create_button_date_format ), Locale.getDefault() );

		return dateFormat.format( date );
	}

	private boolean isValidateDate( int startDate, int endDate ) {

		if ( 0 > DateUtil.getDiffDayCount( startDate, endDate ) ) {

			fragmentLayout.showToast( R.string.fragment_plan_create_error_end_date );
			return false;
		}
		else {

			return true;
		}
	}

	@Override
	public void onShowEndDatePicker() {

		if ( null == endDateSetListener ) {

			endDateSetListener = new CalendarDatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet( CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth ) {

					GregorianCalendar newEndDate = (GregorianCalendar) GregorianCalendar.getInstance();
					newEndDate.set( GregorianCalendar.YEAR, year );
					newEndDate.set( GregorianCalendar.MONTH, monthOfYear );
					newEndDate.set( GregorianCalendar.DAY_OF_MONTH, dayOfMonth );

					if ( isValidateDate( DateUtil.formatToInt( startDateCalendar.getTime() ), DateUtil.formatToInt( newEndDate.getTime() ) ) ) {

						endDateCalendar.set( GregorianCalendar.YEAR, year );
						endDateCalendar.set( GregorianCalendar.MONTH, monthOfYear );
						endDateCalendar.set( GregorianCalendar.DAY_OF_MONTH, dayOfMonth );

						fragmentLayout.setEndDate( formatToString( endDateCalendar.getTime() ) );
					}
				}
			};
		}

		int endYear = endDateCalendar.get( GregorianCalendar.YEAR );
		int endMonth = endDateCalendar.get( GregorianCalendar.MONTH );
		int endDay = endDateCalendar.get( GregorianCalendar.DAY_OF_MONTH );

		CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog.newInstance( endDateSetListener, endYear, endMonth, endDay );
		calendarDatePickerDialog.show( getFragmentManager(), "CalendarDatePickerDialog" );
	}

	public PlanCreationFragment setPlanId( long planId ) {

		this.planId = planId;
		return this;
	}

	public PlanCreationFragment setEditMode( boolean editMode ) {

		this.editMode = editMode;
		return this;
	}

	public static PlanCreationFragment newInstance() {

		return new PlanCreationFragment();
	}

}
