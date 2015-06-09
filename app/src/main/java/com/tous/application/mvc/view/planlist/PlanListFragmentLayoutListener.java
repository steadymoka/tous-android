package com.tous.application.mvc.view.planlist;


import android.support.v7.widget.RecyclerView;

import com.moka.framework.view.LayoutListener;


public interface PlanListFragmentLayoutListener extends LayoutListener {

	RecyclerView.Adapter getRecyclerViewAdapter();

	void onClickToAddPlan();

}
