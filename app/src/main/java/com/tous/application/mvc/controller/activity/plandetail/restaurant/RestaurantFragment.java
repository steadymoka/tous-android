package com.tous.application.mvc.controller.activity.plandetail.restaurant;


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
import com.tous.application.mvc.model.itemdata.SpotItemData;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.mvc.view.plandetail.restaurant.RestaurantFragmentLayout;
import com.tous.application.mvc.view.plandetail.restaurant.RestaurantFragmentLayoutListener;
import com.tous.application.mvc.view.plandetail.viewspot.SpotItemView;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends BaseFragment implements RestaurantFragmentLayoutListener {

	private RestaurantFragmentLayout fragmentLayout;

	private ManjongRecyclerAdapter<SpotItemData, SpotItemView> recyclerAdapter;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new RestaurantFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );

		refreshView();
		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		List<SpotItemData> spotItemDatas = makeItems();
		recyclerAdapter.setItems( spotItemDatas );

		if ( spotItemDatas.isEmpty() )
			fragmentLayout.setEmptyMessage( true );
		else
			fragmentLayout.setEmptyMessage( false );
	}

	private List<SpotItemData> makeItems() {

		List<SpotItemData> spotItemDatas = new ArrayList<>();
		for ( Spot spot : getDataFromDB() )
			spotItemDatas.add( new SpotItemData( spot ) );

		return spotItemDatas;
	}

	private List<Spot> getDataFromDB() {

		return SpotTable.from( getActivity() )
				.findSpotListOfPlanAndType( plan.getId(), DetailPlanFragment.TYPE_RESTAURANT );
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
	public void onClickToAddRestaurant() {

		Intent intent = new Intent( getActivity(), SpotCreationActivity.class );
		intent.putExtra( SpotCreationActivity.KEY_SPOT_TYPE, DetailPlanFragment.TYPE_RESTAURANT );
		intent.putExtra( SpotCreationActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
	}

	@Subscribe
	public void onRefreshViewEvent( OnRefreshViewEvent onRefreshViewEvent ) {

		refreshView();
	}

	public RestaurantFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		OttoUtil.getInstance().unregister( this );
	}

	public static RestaurantFragment newInstance() {

		return new RestaurantFragment();
	}

}
