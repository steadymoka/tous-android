package com.tous.application.mvc.view.spot;


import com.moka.framework.view.LayoutListener;


public interface SpotCreationFragmentLayoutListener extends LayoutListener {

	void onSavePlan();

	void onSearchSpotInWeb();

	void onShowStartTimePicker();

	void onShowEndTimePicker();

	void onSearchSpotAddressInMap();

}
