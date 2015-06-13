package com.tous.application.mvc.controller.activity.plandetail.schedule;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.util.DateProvider;
import com.squareup.otto.Subscribe;
import com.tous.application.mvc.controller.activity.main.calendar.CalendarAdapter;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleFragmentLayout;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleFragmentLayoutListener;
import com.tous.application.util.DateUtil;

import java.util.Date;


public class ScheduleFragment extends BaseFragment implements ScheduleFragmentLayoutListener, DateProvider {

	private ScheduleFragmentLayout fragmentLayout;

	private CalendarAdapter calendarAdapter;
	private ScheduleAdapter scheduleAdapter;

	private Handler handler;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new ScheduleFragmentLayout( this, this, inflater, container );

		handler = new Handler();
		OttoUtil.getInstance().register( this );

		initView();
		refreshView();

		return fragmentLayout.getRootView();
	}

	private void initView() {

		fragmentLayout.showCalendar();
		handler.postDelayed( new Runnable() {

			@Override
			public void run() {

				fragmentLayout.setDayIndex( CalendarUtil.getDayIndexFrom( DateUtil.parseDate( plan.getStartDate() ) ) );
			}
		}, 100 );
//		fragmentLayout.setDayIndex( CalendarUtil.getDayIndexFrom( DateUtil.parseDate( plan.getStartDate() ) ) );
	}

	private void refreshView() {

		fragmentLayout.setPlanName( plan.getName() );
		calendarAdapter.setPlan( plan );
	}

	@Override
	public PagerAdapter getSchedulePagerAdapter() {

		if ( null == scheduleAdapter )
			scheduleAdapter = new ScheduleAdapter( getActivity().getSupportFragmentManager() );

		scheduleAdapter.setPlanId( plan.getId() );

		return scheduleAdapter;
	}

	@Override
	public CalendarViewAdapter<?, ?> getCalendarViewAdapter() {

		if ( null == calendarAdapter )
			calendarAdapter = new CalendarAdapter( getActivity() );

		return calendarAdapter;
	}

	@Override
	public DateProvider getDateProvider() {

		return this;
	}

	/**
	 * Click Listener
	 */

	@Override
	public void onCalendarItemSelected( final CalendarCellData data ) {

		int dayIndex = CalendarUtil.getDayIndexFrom( data.getDate() );
		if ( null != fragmentLayout )
			fragmentLayout.setDayIndex( dayIndex );
	}

	@Override
	public void onDaySelected( int dayIndex ) {

		Date date = CalendarUtil.getDateFromDayIndex( dayIndex );
		int currentDate = CalendarUtil.getIntDate( date );

		setDayCountTextAndFloatingButton( currentDate );
	}

	private void setDayCountTextAndFloatingButton( int currentDate ) {

		if ( plan.isPlaningDate( currentDate ) ) {

			fragmentLayout.setDayCount( plan.getDayCount( currentDate ) + "일째" );
			fragmentLayout.setFloatingActionButton( true );
		}
		else {

			fragmentLayout.setDayCount( "ToUs 와 함께" );
			fragmentLayout.setFloatingActionButton( false );
		}
	}

	@Override
	public void onClickToCalendar() {

		fragmentLayout.showCalendar();
	}

	@Override
	public void onClickToMap() {

		fragmentLayout.showMap();
	}

	@Override
	public void onClickToDetailPlan() {

		Intent intent = new Intent( getActivity(), DetailPlanActivity.class );
		intent.putExtra( DetailPlanActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
	}

	@Subscribe
	public void onClickToDetailPlan( ScheduleItemItemFragment.OnClickToDetailPlan onClickToDetailPlan ) {

//		Intent intent = new Intent( getActivity(), DetailPlanActivity.class );
//		intent.putExtra( DetailPlanActivity.KEY_PLAN_ID, plan.getId() );
//		startActivity( intent );
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		OttoUtil.getInstance().unregister( this );
	}

	public ScheduleFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	public static ScheduleFragment newInstance() {

		return new ScheduleFragment();
	}

}
