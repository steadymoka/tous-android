package com.tous.application.mvc.controller.activity.spot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.tous.application.mvc.view.spot.SpotDetailFragmentLayout;
import com.tous.application.mvc.view.spot.SpotDetailFragmentLayoutListener;


public class SpotDetailFragment extends BaseFragment implements SpotDetailFragmentLayoutListener {

	private SpotDetailFragmentLayout fragmentLayout;

	private long spotId;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new SpotDetailFragmentLayout( this, this, inflater, container );

		return fragmentLayout.getRootView();
	}

	public Fragment setSpotId( long spotId ) {

		this.spotId = spotId;
		return this;
	}

	public static SpotDetailFragment newInstance() {

		return new SpotDetailFragment();
	}

}
