package com.tous.application.mvc.controller.activity.map;


import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.moka.framework.controller.BaseFragment;
import com.tous.application.R;
import com.tous.application.mvc.view.map.FindLocationFragmentLayout;
import com.tous.application.mvc.view.map.FindLocationFragmentLayoutListener;


public class FindLocationFragment extends BaseFragment implements FindLocationFragmentLayoutListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private FindLocationFragmentLayout fragmentLayout;

	private GoogleApiClient googleApiClient;
	private PlaceAutocompleteAdapter mAdapter;

	private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
			new LatLng( -34.041458, 150.790100 ), new LatLng( -33.682247, 151.383362 ) );

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new FindLocationFragmentLayout( this, this, inflater, container );

		return fragmentLayout.getRootView();
	}

	private void setUpGoogleApiClient() {

		mAdapter = new PlaceAutocompleteAdapter( getActivity(), android.R.layout.simple_list_item_1,
				googleApiClient, BOUNDS_GREATER_SYDNEY, null );
	}

	@Override
	public void onConnected( Bundle bundle ) {

	}

	@Override
	public void onConnectionSuspended( int i ) {

	}

	@Override
	public void onConnectionFailed( ConnectionResult connectionResult ) {

		Toast.makeText( getActivity(),
				"Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
				Toast.LENGTH_SHORT ).show();
	}

	@Override
	public AdapterView.OnItemClickListener getAutocompleteClickListener() {

		AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
			/*
			 Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
				final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem( position );
				final String placeId = String.valueOf( item.placeId );
				Log.i( "tag ", "Autocomplete item selected: " + item.description );

            /*
			 Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
				PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
						.getPlaceById( googleApiClient, placeId );
				placeResult.setResultCallback( mUpdatePlaceDetailsCallback );

				Toast.makeText( getActivity(), "Clicked: " + item.description,
						Toast.LENGTH_SHORT ).show();
				Log.i( "tag ", "Called getPlaceById to get Place details for " + item.placeId );
			}
		};

		return mAutocompleteClickListener;
	}

	@Override
	public PlaceAutocompleteAdapter getAdapter() {

		if ( null == mAdapter )
			mAdapter = new PlaceAutocompleteAdapter( getActivity(), android.R.layout.simple_list_item_1,
					googleApiClient, BOUNDS_GREATER_SYDNEY, null );

		return mAdapter;
	}

	private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
			= new ResultCallback<PlaceBuffer>() {

		@Override
		public void onResult( PlaceBuffer places ) {

			if ( !places.getStatus().isSuccess() ) {
				// Request did not complete successfully
				Log.e( "tag ", "Place query did not complete. Error: " + places.getStatus().toString() );
				places.release();
				return;
			}
			// Get the Place object from the buffer.
			final Place place = places.get( 0 );

			// Format details of the place for display and show it in a TextView.
			// Display the third party attributions if set.
			final CharSequence thirdPartyAttribution = places.getAttributions();

			Log.i( "tag ", "Place details received: " + place.getName() );

			places.release();
		}
	};

	private static Spanned formatPlaceDetails( Resources res, CharSequence name, String id,
											   CharSequence address, CharSequence phoneNumber, Uri websiteUri ) {

		Log.e( "tag ", res.getString( R.string.place_details, name, id, address, phoneNumber,
				websiteUri ) );
		return Html.fromHtml( res.getString( R.string.place_details, name, id, address, phoneNumber,
				websiteUri ) );

	}

	public static FindLocationFragment newInstance() {

		return new FindLocationFragment();
	}
}
