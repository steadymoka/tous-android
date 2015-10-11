package com.tous.application.mvc.view.spot;


import com.moka.framework.view.LayoutListener;


public interface SpotCreationFragmentLayoutListener extends LayoutListener {

	void onSaveSpot();

	void onSearchSpotInWeb();

	void onSearchSpotAddressInMap();

	void onClickImage();

//	void onClickToSpot();
//
//	void onClickToRestaurant();

}
