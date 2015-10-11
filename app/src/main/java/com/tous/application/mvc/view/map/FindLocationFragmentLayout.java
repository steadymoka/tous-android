package com.tous.application.mvc.view.map;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.map.FindLocationFragment;


public class FindLocationFragmentLayout extends FragmentLayout<FindLocationFragment, FindLocationFragmentLayoutListener> implements OnMapReadyCallback {

	private MapFragment mapFragment;

	public FindLocationFragmentLayout( FindLocationFragment fragment, FindLocationFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_find_location;
	}

	@Override
	protected void onLayoutInflated() {

		mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById( R.id.map );
		mapFragment.getMapAsync( this );

		GoogleMap googleMap = mapFragment.getMap();
	}

	@Override
	public void onMapReady( GoogleMap googleMap ) {

		googleMap.addMarker( new MarkerOptions()
				.position( new LatLng( 0, 0 ) )
				.title( "Marker" ) );
	}

}
