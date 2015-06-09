package com.tous.application.mvc.view.plandetail.restaurant;


import android.support.v7.widget.RecyclerView;

import com.moka.framework.view.LayoutListener;


public interface RestaurantFragmentLayoutListener extends LayoutListener {

	RecyclerView.Adapter getRecyclerViewAdapter();

	void onClickToAddRestaurant();

}
