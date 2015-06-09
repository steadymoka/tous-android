package com.tous.application.mvc.controller.activity.plandetail.lodgment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.lodgment.LodgmentFragmentLayout;
import com.tous.application.mvc.view.plandetail.lodgment.LodgmentFragmentLayoutListener;


public class LodgmentFragment extends BaseFragment implements LodgmentFragmentLayoutListener {

	private LodgmentFragmentLayout fragmentLayout;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new LodgmentFragmentLayout( this, this, inflater, container );

		return fragmentLayout.getRootView();
	}

	public LodgmentFragment setPlan( Plan plan ) {

		return this;
	}

	public static LodgmentFragment newInstance() {

		return new LodgmentFragment();
	}

}
