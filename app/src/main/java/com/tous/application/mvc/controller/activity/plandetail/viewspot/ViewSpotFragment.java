package com.tous.application.mvc.controller.activity.plandetail.viewspot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.ManjongRecyclerAdapter;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanFragment;
import com.tous.application.mvc.controller.activity.spot.SpotCreationActivity;
import com.tous.application.mvc.controller.activity.spot.SpotDetailActivity;
import com.tous.application.mvc.controller.dialog.SetDayCountAndTimeDialogFragment;
import com.tous.application.mvc.model.itemdata.SpotItemData;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.mvc.view.plandetail.viewspot.ViewSpotFragmentLayout;
import com.tous.application.mvc.view.plandetail.viewspot.ViewSpotFragmentLayoutListener;
import com.tous.application.mvc.view.plandetail.viewspot.SpotItemView;

import java.util.ArrayList;
import java.util.List;


public class ViewSpotFragment extends BaseFragment implements ViewSpotFragmentLayoutListener {

	private ViewSpotFragmentLayout fragmentLayout;

	private ManjongRecyclerAdapter<SpotItemData, SpotItemView> recyclerAdapter;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new ViewSpotFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );

		refreshView();
		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		recyclerAdapter.setItems( makeItems() );
	}

	private List<SpotItemData> makeItems() {

		List<SpotItemData> spotItemDatas = new ArrayList<>();
		for ( Spot spot : getDataFromDB() )
			spotItemDatas.add( new SpotItemData( spot ) );

		return spotItemDatas;
	}

	private List<Spot> getDataFromDB() {

		return SpotTable.from( getActivity() )
				.findSpotListOfPlanAndType( plan.getId(), DetailPlanFragment.TYPE_VIEW_SPOT );
	}

	@Override
	public RecyclerView.Adapter getRecyclerViewAdapter() {

		if ( null == recyclerAdapter )
			recyclerAdapter = new ManjongRecyclerAdapter<>( getActivity(), SpotItemView.class, R.layout.view_spot_item );

		return recyclerAdapter;
	}

	/**
	 * Item ClickListener
	 */

	@Override
	public void onClickToAddSpot() {

		Intent intent = new Intent( getActivity(), SpotCreationActivity.class );
		intent.putExtra( SpotCreationActivity.KEY_SPOT_TYPE, DetailPlanFragment.TYPE_VIEW_SPOT );
		intent.putExtra( SpotCreationActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
	}

	@Subscribe
	public void onSpotItemClickEvent( SpotItemView.OnSpotItemClickEvent onSpotItemClickEvent ) {

		Intent intent = new Intent( getActivity(), SpotDetailActivity.class );
		intent.putExtra( SpotDetailActivity.KEY_SPOT_ID, onSpotItemClickEvent.getSpotId() );
		startActivity( intent );
	}

	@Subscribe
	public void onSpotItemSetTimeClickEvent( SpotItemView.OnSpotItemSetTimeClickEvent onSpotItemSetTimeClickEvent ) {

		Spot spot = onSpotItemSetTimeClickEvent.getSpot();

		SetDayCountAndTimeDialogFragment.newInstance()
				.setSpot( spot )
				.setDayCount( plan.getPlanDayCount() )
				.showDialog( getFragmentManager(), new SetDayCountAndTimeDialogFragment.OnDayCountSelectedListener() {

					@Override
					public void onDayCountSelected( int dayCount ) {

						OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
					}

					@Override
					public void onInvalid() {

						fragmentLayout.showToast( "연속된 시간을 선택해야 합니다" );
					}

				} );
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

	public ViewSpotFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	public static ViewSpotFragment newInstance() {

		return new ViewSpotFragment();
	}

}
