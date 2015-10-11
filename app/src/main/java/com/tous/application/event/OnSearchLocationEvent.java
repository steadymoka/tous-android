package com.tous.application.event;


import com.google.android.gms.location.places.Place;
import com.moka.framework.util.OttoUtil;


public class OnSearchLocationEvent implements OttoUtil.Event {

	private final Place place;

	public OnSearchLocationEvent( Place place ) {

		this.place = place;
	}

	public Place getPlace() {

		return place;
	}

}
