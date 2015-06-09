package com.tous.application.mvc.model.itemdata;


import com.moka.framework.widget.adapter.ItemData;
import com.tous.application.mvc.model.transport.Spot;


public class ScheduleItemData extends ItemData {

	private Spot spot;

	public ScheduleItemData( Spot spot ) {

		this.spot = spot;
	}

	public Spot getSpot() {

		return spot;
	}

}
