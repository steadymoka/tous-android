package com.tous.application.mvc.view.plandetail.transport;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.transport.TransportFragment;


public class TransportFragmentLayout extends FragmentLayout<TransportFragment, TransportFragmentLayoutListener> {

	public TransportFragmentLayout( TransportFragment fragment, TransportFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_transport;
	}

	@Override
	protected void onLayoutInflated() {

	}

}
