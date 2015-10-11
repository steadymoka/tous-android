package com.tous.application.mvc.controller.activity.map;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.moka.framework.controller.BaseActivity;
import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.util.OttoUtil;
import com.tous.application.R;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.model.spot.Spot;

import java.util.ArrayList;
import java.util.List;


public class CheckLocationActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

	private MapFragment mapFragment;
	private GoogleApiClient mGoogleApiClient;
	private GoogleMap googleMap;

	private List<Spot> spotList;
	private List<LatLng> latLngList = new ArrayList<>();

	private String address;
	private double longitude;
	private double latitude;

	private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
			new LatLng( -34.041458, 150.790100 ), new LatLng( -33.682247, 151.383362 ) );

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_check_location );

		initView();
		loadDataFromDB();
		refreshView();
		connectMarker();

		if ( 0 < latLngList.size() )
			moveCamera( latLngList.get( latLngList.size() - 1 ) );

		long spotId = getIntent().getLongExtra( "SPOT_ID", -1 );
		if ( -1 != spotId ) {

			Spot spot = SpotTable.from( this ).find( spotId );
			LatLng latLng = new LatLng( spot.getLatitude(), spot.getLongitude() );
			moveCamera( latLng );
		}
	}

	private void initView() {

		ToolbarLayout toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
		setSupportActionBar( toolbarLayout.getToolbar() );

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled( true );
		actionBar.setHomeButtonEnabled( true );
		actionBar.setTitle( "지도" );

		mGoogleApiClient = new GoogleApiClient.Builder( this )
				.enableAutoManage( this, 0 /* clientId */, this )
				.addApi( Places.GEO_DATA_API )
				.build();

		mapFragment = (MapFragment) getFragmentManager().findFragmentById( R.id.map );
		googleMap = mapFragment.getMap();
	}

	public void moveCamera( LatLng latLng ) {

		googleMap.moveCamera( CameraUpdateFactory.newLatLng( latLng ) );
		googleMap.animateCamera( CameraUpdateFactory.zoomTo( 12 ) );
	}

	private void loadDataFromDB() {

		spotList = SpotTable.from( this ).findAll();
	}

	private void refreshView() {

		if ( 0 < spotList.size() )
			for ( Spot spot : spotList ) {

				double longitude = spot.getLongitude();
				double latitude = spot.getLatitude();

				LatLng latLng = new LatLng( latitude, longitude );
				latLngList.add( latLng );

				setMarker( latLng, spot.getName() );
			}
	}

	private void setMarker( LatLng latLng, String name ) {

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position( latLng );
		markerOptions.title( name );
		googleMap.addMarker( markerOptions );
	}

	private void connectMarker() {

		PolylineOptions polylineOptions = new PolylineOptions();
		polylineOptions.addAll( latLngList );

		googleMap.addPolyline( polylineOptions );
	}

	@Override
	public void onConnectionFailed( ConnectionResult connectionResult ) {

		// TODO(Developer): Check error code and notify the user of error state and resolution.
		Toast.makeText( this,
				"Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
				Toast.LENGTH_SHORT ).show();
	}

	@Override
	protected void onDestroy() {

		OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
		super.onDestroy();
	}
}
