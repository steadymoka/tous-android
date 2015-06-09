package com.tous.application.mvc.view.main.drawer;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.moka.framework.view.LayoutListener;


public interface NavigationFragmentLayoutListener extends LayoutListener {

	RecyclerView.Adapter getRecyclerAdapter( NavigationListItemView.OnItemClickListener onItemClickListener, View headerView );

	void onClickPlanList();

	void onClickSnsService();

	void onClickExchangeRate();

	void onClickSignOut();

}
