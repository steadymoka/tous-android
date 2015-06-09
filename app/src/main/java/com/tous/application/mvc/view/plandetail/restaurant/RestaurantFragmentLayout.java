package com.tous.application.mvc.view.plandetail.restaurant;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.adapter.OnScrollDelegate;
import com.moka.framework.widget.fab.FloatingActionButton;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.restaurant.RestaurantFragment;


public class RestaurantFragmentLayout extends FragmentLayout<RestaurantFragment, RestaurantFragmentLayoutListener> implements View.OnClickListener {

	private ObservableRecyclerView observableRecyclerView_restaurants;
	private FloatingActionButton floatingActionButton_add_restaurant;

	private OnScrollDelegate onScrollDelegate;

	public RestaurantFragmentLayout( RestaurantFragment fragment, RestaurantFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_restaurant;
	}

	@Override
	protected void onLayoutInflated() {

		initView();
		bindView();
	}

	private void initView() {

		observableRecyclerView_restaurants = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_restaurants );
		floatingActionButton_add_restaurant = (FloatingActionButton) findViewById( R.id.floatingActionButton_add_restaurant );
	}

	private void bindView() {

		onScrollDelegate = new OnScrollDelegate();

		observableRecyclerView_restaurants.setOnScrollDelegate( onScrollDelegate );
		LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
		observableRecyclerView_restaurants.setLayoutManager( layoutManager );
		observableRecyclerView_restaurants.setAdapter( getLayoutListener().getRecyclerViewAdapter() );

		floatingActionButton_add_restaurant.setOnClickListener( this );
		floatingActionButton_add_restaurant
				.attachToRecyclerView( observableRecyclerView_restaurants, onScrollDelegate );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.floatingActionButton_add_restaurant:

				getLayoutListener().onClickToAddRestaurant();
				break;
		}
	}

}
