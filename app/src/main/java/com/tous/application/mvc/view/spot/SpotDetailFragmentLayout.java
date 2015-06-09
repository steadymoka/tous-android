package com.tous.application.mvc.view.spot;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotDetailFragment;


public class SpotDetailFragmentLayout extends FragmentLayout<SpotDetailFragment, SpotDetailFragmentLayoutListener> {

	public SpotDetailFragmentLayout( SpotDetailFragment fragment, SpotDetailFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_spot_deatail;
	}

	@Override
	protected void onLayoutInflated() {

	}

}
