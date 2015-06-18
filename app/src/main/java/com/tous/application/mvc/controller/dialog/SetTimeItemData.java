package com.tous.application.mvc.controller.dialog;


import com.moka.framework.widget.adapter.ItemData;
import com.tous.application.mvc.model.spot.Spot;

import java.util.List;


public class SetTimeItemData extends ItemData {

	private List<Spot> spotList;
	private Spot currentSpot;
	private int timeOfHour;

	public SetTimeItemData( List<Spot> spotList, Spot currentSpot, int timeOfHour ) {

		this.spotList = spotList;
		this.currentSpot = currentSpot;
		this.timeOfHour = timeOfHour;
	}

	public List<Spot> getSpotList() {

		return spotList;
	}

	public void setSpotList( List<Spot> spotList ) {

		this.spotList = spotList;
	}

	public Spot getCurrentSpot() {

		return currentSpot;
	}

	public void setCurrentSpot( Spot currentSpot ) {

		this.currentSpot = currentSpot;
	}

	public int getTimeOfHour() {

		return timeOfHour;
	}

	public void setTimeOfHour( int timeOfHour ) {

		this.timeOfHour = timeOfHour;
	}

}
