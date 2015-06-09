package com.tous.application.mvc.model.itemdata;


import com.moka.framework.widget.adapter.ItemData;
import com.tous.application.mvc.model.transport.Spot;


public class SpotItemData extends ItemData {

	private Spot spot;

	public SpotItemData( Spot spot ) {

		this.spot = spot;
	}

	public Spot getSpot() {

		return spot;
	}

	public long getSpotId() {

		return spot.getId();
	}

}
