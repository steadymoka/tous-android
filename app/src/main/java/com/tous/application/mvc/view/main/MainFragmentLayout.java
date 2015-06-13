package com.tous.application.mvc.view.main;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.MainFragment;


public class MainFragmentLayout extends FragmentLayout<MainFragment, MainFragmentLayoutListener> {

	public MainFragmentLayout( MainFragment fragment, MainFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_main;
	}

	@Override
	protected void onLayoutInflated() {

	}

}
