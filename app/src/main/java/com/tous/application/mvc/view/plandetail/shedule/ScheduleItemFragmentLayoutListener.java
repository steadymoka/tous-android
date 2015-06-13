package com.tous.application.mvc.view.plandetail.shedule;


import android.support.v7.widget.RecyclerView;

import com.moka.framework.view.LayoutListener;


public interface ScheduleItemFragmentLayoutListener extends LayoutListener {

	void onClickToDetail();

	RecyclerView.Adapter getRecyclerViewAdapter();

}
