package com.tous.application.mvc.controller.activity.main.schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.ManjongRecyclerAdapter;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.scrollobservableview.OnTouchScrollListener;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanFragment;
import com.tous.application.mvc.model.itemdata.ScheduleItemData;
import com.tous.application.mvc.model.itemdata.SpotItemData;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.transport.Spot;
import com.tous.application.mvc.view.main.shedule.ScheduleFragmentLayoutListener;
import com.tous.application.mvc.view.main.shedule.ScheduleItemFragmentLayout;
import com.tous.application.mvc.view.main.shedule.ScheduleItemView;
import com.tous.application.mvc.view.plandetail.viewspot.SpotItemView;
import com.tous.application.util.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class ScheduleItemFragment extends BaseFragment implements ScheduleFragmentLayoutListener {

	private ScheduleItemFragmentLayout fragmentLayout;
	private ManjongRecyclerAdapter<ScheduleItemData, ScheduleItemView> recyclerAdapter;

	private long planId;
	private int dayIndex;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new ScheduleItemFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );
		refreshView();

		Plan plan = PlanTable.from( getActivity() ).find( planId ); // TODO 땜빵

		// TODO 땜빵
		if ( plan.isPlaningDate( DateUtil.formatToInt( CalendarUtil.getDateFromDayIndex( dayIndex ) ) ) )
			fragmentLayout.setYesMode(); // TODO 땜빵
		else
			fragmentLayout.setNoMode(); // TODO 땜빵


		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		recyclerAdapter.setItems( makeItems() );
	}

	private List<ScheduleItemData> makeItems() {

		List<ScheduleItemData> spotItemDatas = new ArrayList<>();
		for ( Spot spot : getDataFromDB() )
			spotItemDatas.add( new ScheduleItemData( spot ) );

		return spotItemDatas;
	}

	private List<Spot> getDataFromDB() {

		return SpotTable.from( getActivity() )
				.findSpotListOfPlan( planId );
	}

	@Override
	public RecyclerView.Adapter getRecyclerViewAdapter() {

		if ( null == recyclerAdapter )
			recyclerAdapter = new ManjongRecyclerAdapter<>( getActivity(), ScheduleItemView.class, R.layout.view_schedule_item );

		return recyclerAdapter;
	}

	@Override
	public void onClickToDetail() {

		OttoUtil.getInstance().post( new OnClickToDetailPlan() );
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

	public ScheduleItemFragment setPlanId( long planId ) {

		this.planId = planId;
		return this;
	}

	public ScheduleItemFragment setDayIndex( int dayIndex ) {

		this.dayIndex = dayIndex;
		return this;
	}

	public static ScheduleItemFragment newInstance() {

		return new ScheduleItemFragment();
	}

	public class OnClickToDetailPlan implements OttoUtil.Event {

	}

}
