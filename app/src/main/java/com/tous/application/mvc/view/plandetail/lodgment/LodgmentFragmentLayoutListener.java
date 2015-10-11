package com.tous.application.mvc.view.plandetail.lodgment;


import android.support.v7.widget.RecyclerView;

import com.moka.framework.view.LayoutListener;


public interface LodgmentFragmentLayoutListener extends LayoutListener {

	RecyclerView.Adapter getRecyclerViewAdapter();

	void onClickToAddLodgment();

}
