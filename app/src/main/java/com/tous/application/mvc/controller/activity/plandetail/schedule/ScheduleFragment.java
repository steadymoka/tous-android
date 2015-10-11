package com.tous.application.mvc.controller.activity.plandetail.schedule;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.util.DateProvider;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.calendar.CalendarAdapter;
import com.tous.application.mvc.controller.activity.map.CheckLocationActivity;
import com.tous.application.mvc.controller.dialog.CalendarDialogFragment;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleFragmentLayout;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleFragmentLayoutListener;
import com.tous.application.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ScheduleFragment extends BaseFragment implements ScheduleFragmentLayoutListener {

	private ScheduleFragmentLayout fragmentLayout;

	private ScheduleAdapter scheduleAdapter;

	private Plan plan;
	private SimpleDateFormat dateFormat;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new ScheduleFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );

		refreshView();

		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		onPageSelected( 0 );
	}

	@Override
	public PagerAdapter getSchedulePagerAdapter() {

		if ( null == scheduleAdapter )
			scheduleAdapter = new ScheduleAdapter( getActivity().getSupportFragmentManager() );

		scheduleAdapter.setPlanId( plan.getId() );
		scheduleAdapter.setPlan( plan );

		return scheduleAdapter;
	}

	@Override
	public void onPageSelected( int position ) {

		SpannableStringBuilder dayCountBuilder = new SpannableStringBuilder( position + 1 + " 일차 (총 " + plan.getPlanDayCount() + "일)   " );

		String planDay = formatToString( DateUtil.addDate( DateUtil.parseDate( plan.getStartDate() ), position ) );
		SpannableString dayCountString = new SpannableString( planDay );
		dayCountString.setSpan( new ForegroundColorSpan( 0xFFf44336 ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );
		dayCountString.setSpan( new RelativeSizeSpan( 0.818f ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );

		dayCountBuilder.append( dayCountString );

		fragmentLayout.setDayCount( dayCountBuilder );
	}

	private String formatToString( Date date ) {

		if ( null == dateFormat )
			dateFormat = new SimpleDateFormat( getString( R.string.fragment_plan_create_button_date_format ), Locale.getDefault() );

		return dateFormat.format( date );
	}

	/**
	 * onClickListener
	 */

	@Override
	public void onClickCalendar() {

		CalendarDialogFragment.newInstance()
				.setPlan( plan.getId() )
				.showDialog( getFragmentManager(), null );
	}

	@Override
	public void onClickMap() {

		fragmentLayout.showToast( "서비스 준비중입니다 :)" );
		startActivity( new Intent( getActivity(), CheckLocationActivity.class ) );
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		OttoUtil.getInstance().unregister( this );
	}

	public ScheduleFragment setPlan( Plan plan ) {

		if ( null != plan )
			this.plan = plan;
		else
			this.plan = new Plan();

		return this;
	}

	public static ScheduleFragment newInstance() {

		return new ScheduleFragment();
	}

}
