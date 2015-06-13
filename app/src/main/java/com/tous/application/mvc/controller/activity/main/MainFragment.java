package com.tous.application.mvc.controller.activity.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainFragmentLayout;
import com.tous.application.mvc.view.main.MainFragmentLayoutListener;


public class MainFragment extends BaseFragment implements MainFragmentLayoutListener {

	private MainFragmentLayout fragmentLayout;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new MainFragmentLayout( this, this, inflater, container );

		return fragmentLayout.getRootView();
	}

	public static MainFragment newInstance() {

		return new MainFragment();
	}

	public MainFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

}
