package com.tous.application.mvc.view.plandetail.lodgment;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.lodgment.LodgmentFragment;


public class LodgmentFragmentLayout extends FragmentLayout<LodgmentFragment, LodgmentFragmentLayoutListener> {

	public LodgmentFragmentLayout( LodgmentFragment fragment, LodgmentFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_lodgment;
	}

	@Override
	protected void onLayoutInflated() {

	}

}
