package com.tous.application.mvc.controller.activity.plandetail.transport;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.transport.TransportFragmentLayout;
import com.tous.application.mvc.view.plandetail.transport.TransportFragmentLayoutListener;


public class TransportFragment extends BaseFragment implements TransportFragmentLayoutListener {

	private TransportFragmentLayout fragmentLayout;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new TransportFragmentLayout( this, this, inflater, container );

		return fragmentLayout.getRootView();
	}

	public TransportFragment setPlan( Plan plan ) {

		return this;
	}

	public static TransportFragment newInstance() {

		return new TransportFragment();
	}

}
