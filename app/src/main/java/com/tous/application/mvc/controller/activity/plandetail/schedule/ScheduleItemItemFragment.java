package com.tous.application.mvc.controller.activity.plandetail.schedule;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.ManjongRecyclerAdapter;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.spot.SpotDetailActivity;
import com.tous.application.mvc.model.itemdata.ScheduleItemData;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleItemFragmentLayoutListener;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleItemFragmentLayout;
import com.tous.application.mvc.view.plandetail.shedule.ScheduleItemView;
import com.tous.application.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ScheduleItemItemFragment extends BaseFragment implements ScheduleItemFragmentLayoutListener {

	private ScheduleItemFragmentLayout fragmentLayout;
	private ManjongRecyclerAdapter<ScheduleItemData, ScheduleItemView> recyclerAdapter;

	private long planId;
	private int dayCount;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new ScheduleItemFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );

		refreshView();

		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		List<ScheduleItemData> scheduleItemDatas = makeItems();
		recyclerAdapter.setItems( scheduleItemDatas );

		if ( scheduleItemDatas.isEmpty() )
			fragmentLayout.setEmptyMessage( true );
		else
			fragmentLayout.setEmptyMessage( false );
	}

	private List<ScheduleItemData> makeItems() {

		List<ScheduleItemData> scheduleItemDatas = new ArrayList<>();
		for ( Spot spot : getDataFromDB() )
			scheduleItemDatas.add( new ScheduleItemData( spot ) );

		return scheduleItemDatas;
	}

	private List<Spot> getDataFromDB() {

		return SpotTable.from( getActivity() )
				.findSpotListOfDayCountAndPlanByOrder( dayCount, planId );
	}

	@Override
	public RecyclerView.Adapter getRecyclerViewAdapter() {

		if ( null == recyclerAdapter )
			recyclerAdapter = new ManjongRecyclerAdapter<>( getActivity(), ScheduleItemView.class, R.layout.view_schedule_item );

		return recyclerAdapter;
	}

	@Subscribe
	public void onSpotItemClickEvent( ScheduleItemView.OnSpotItemClickEvent onSpotItemClickEvent ) {

		Intent intent = new Intent( getActivity(), SpotDetailActivity.class );
		intent.putExtra( SpotDetailActivity.KEY_SPOT_ID, onSpotItemClickEvent.getSpotId() );
		startActivity( intent );
	}

	@Subscribe
	public void onRefreshViewEvent( OnRefreshViewEvent onRefreshViewEvent ) {

		refreshView();
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		OttoUtil.getInstance().unregister( this );
	}

	public ScheduleItemItemFragment setPlanId( long planId ) {

		this.planId = planId;
		return this;
	}

	public ScheduleItemItemFragment setDayCount( int dayIndex ) {

		this.dayCount = dayIndex + 1;
		return this;
	}

	public static ScheduleItemItemFragment newInstance() {

		return new ScheduleItemItemFragment();
	}

}
