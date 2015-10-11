package com.tous.application.mvc.controller.activity.map;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moka.framework.controller.BaseActivity;
import com.moka.framework.support.toolbar.ToolbarLayout;
import com.tous.application.R;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.mvc.model.spot.Spot;

import java.util.List;


public class SearchLocationActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

	private MapFragment mapFragment;
	private GoogleApiClient mGoogleApiClient;
	private GoogleMap googleMap;

	private PlaceAutocompleteAdapter mAdapter;

	private AutoCompleteTextView mAutocompleteView;

	private TextView mPlaceDetailsText;

	private TextView mPlaceDetailsAttribution;

	private String address;
	private double longitude;
	private double latitude;

	private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
			new LatLng( -34.041458, 150.790100 ), new LatLng( -33.682247, 151.383362 ) );

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_search_location );

		ToolbarLayout toolbarLayout = (ToolbarLayout) findViewById( R.id.toolbarLayout );
		toolbarLayout.setAlpha( 1 );
		setSupportActionBar( toolbarLayout.getToolbar() );

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled( true );
		actionBar.setHomeButtonEnabled( true );
		actionBar.setTitle( "지도 검색" );

		mGoogleApiClient = new GoogleApiClient.Builder( this )
				.enableAutoManage( this, 0 /* clientId */, this )
				.addApi( Places.GEO_DATA_API )
				.build();

		mapFragment = (MapFragment) getFragmentManager().findFragmentById( R.id.map );
		googleMap = mapFragment.getMap();

		mAutocompleteView = (AutoCompleteTextView)
				findViewById( R.id.autocomplete_places );

		mAutocompleteView.setOnItemClickListener( mAutocompleteClickListener );

		mPlaceDetailsText = (TextView) findViewById( R.id.place_details );
		mPlaceDetailsAttribution = (TextView) findViewById( R.id.place_attribution );

		mAdapter = new PlaceAutocompleteAdapter( this, android.R.layout.simple_list_item_1,
				mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null );
		mAutocompleteView.setAdapter( mAdapter );

		if ( null != getIntent() ) {

			longitude = getIntent().getDoubleExtra( "longitude", -34.041458 );
			latitude = getIntent().getDoubleExtra( "latitude", 150.790100 );
			address = getIntent().getStringExtra( "address" );

			LatLng latLng = new LatLng( latitude, longitude );

			setMapFirst( latLng, address );
			mAutocompleteView.setText( address );
		}
	}

	private AdapterView.OnItemClickListener mAutocompleteClickListener
			= new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {

			final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem( position );
			final String placeId = String.valueOf( item.placeId );

			PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
					.getPlaceById( mGoogleApiClient, placeId );
			placeResult.setResultCallback( mUpdatePlaceDetailsCallback );
		}
	};

	public void setMap( LatLng latLng, String name ) {

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position( latLng );
		markerOptions.title( name );
		googleMap.addMarker( markerOptions );

//		googleMap.moveCamera( CameraUpdateFactory.newLatLng( latLng ) );
		googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom( latLng, 12 ) );
		googleMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );
		googleMap.animateCamera( CameraUpdateFactory.zoomIn() );
	}

	public void setMapFirst( LatLng latLng, String name ) {

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position( latLng );
		markerOptions.title( name );
		googleMap.addMarker( markerOptions );

		googleMap.moveCamera( CameraUpdateFactory.newLatLng( latLng ) );
//		googleMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );
		googleMap.animateCamera( CameraUpdateFactory.zoomTo( 11 ) );
	}

	private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
			= new ResultCallback<PlaceBuffer>() {

		@Override
		public void onResult( PlaceBuffer places ) {

			if ( !places.getStatus().isSuccess() ) {
				// Request did not complete successfully
				places.release();
				return;
			}
			// Get the Place object from the buffer.
			final Place place = places.get( 0 );

			address = place.getAddress().toString();
			longitude = place.getLatLng().longitude;
			latitude = place.getLatLng().latitude;

			// Format details of the place for display and show it in a TextView.
			formatPlaceDetails( getResources(), place.getName(),
					place.getId(), place.getAddress(), place.getPhoneNumber(),
					place.getWebsiteUri() );

			setMap( place.getLatLng(), place.getName().toString() );

			// Display the third party attributions if set.
			final CharSequence thirdPartyAttribution = places.getAttributions();
			if ( thirdPartyAttribution == null ) {
				mPlaceDetailsAttribution.setVisibility( View.GONE );
			}
			else {
				mPlaceDetailsAttribution.setVisibility( View.VISIBLE );
				mPlaceDetailsAttribution.setText( Html.fromHtml( thirdPartyAttribution.toString() ) );
			}

			places.release();
		}
	};

	private static Spanned formatPlaceDetails( Resources res, CharSequence name, String id,
											   CharSequence address, CharSequence phoneNumber, Uri websiteUri ) {

		return Html.fromHtml( res.getString( R.string.place_details, name, id, address, phoneNumber,
				websiteUri ) );
	}

	@Override
	public void onConnectionFailed( ConnectionResult connectionResult ) {

		// TODO(Developer): Check error code and notify the user of error state and resolution.
		Toast.makeText( this,
				"Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
				Toast.LENGTH_SHORT ).show();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {

		getMenuInflater().inflate( R.menu.fragment_plan_or_spot_creation, menu );
		return super.onCreateOptionsMenu( menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case R.id.menuItem_save:

				Intent intent = new Intent();
				intent.putExtra( "key_address_name", address );
				intent.putExtra( "longitude", longitude );
				intent.putExtra( "latitude", latitude );

				setResult( Activity.RESULT_OK, intent );
				finish();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

}
