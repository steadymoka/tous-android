package com.tous.application.mvc.view.plandetail.viewspot;


import android.support.v7.widget.RecyclerView;

import com.moka.framework.view.LayoutListener;


public interface ViewSpotFragmentLayoutListener extends LayoutListener {

	RecyclerView.Adapter getRecyclerViewAdapter();

	void onClickToAddSpot();

}
