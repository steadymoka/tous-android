package com.tous.application.mvc.view.main.shedule;


import android.support.v7.widget.RecyclerView;

import com.moka.framework.view.LayoutListener;


public interface ScheduleFragmentLayoutListener extends LayoutListener {

	void onClickToDetail();

	RecyclerView.Adapter getRecyclerViewAdapter();

}
