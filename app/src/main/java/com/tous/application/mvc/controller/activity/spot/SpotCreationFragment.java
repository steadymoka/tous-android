package com.tous.application.mvc.controller.activity.spot;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.util.UriBuilder;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.database.table.image.ImageTable;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.event.OnSearchLocationEvent;
import com.tous.application.event.OnWebUrlCopyEvent;
import com.tous.application.mvc.controller.activity.browser.WebViewActivity;
import com.tous.application.mvc.controller.activity.image.ImagePickerDialogFragment;
import com.tous.application.mvc.controller.activity.map.FindLocationActivity;
import com.tous.application.mvc.controller.activity.map.SearchLocationActivity;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationFragment;
import com.tous.application.mvc.model.image.Image;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayout;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayoutListener;
import com.tous.application.util.ImageFileUtil;
import com.tous.application.util.TousPreference;

import java.util.ArrayList;
import java.util.Objects;


public class SpotCreationFragment extends BaseFragment implements SpotCreationFragmentLayoutListener, ImagePickerDialogFragment.OnImagePickedListener {

	private SpotCreationFragmentLayout fragmentLayout;

	private String spotType;

	private long planId;
	private long spotId;
	private Spot spot;

	private double longitude;
	private double latitude;
	private boolean isExistImage;
	private Image image;
	private boolean editMode = false;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new SpotCreationFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );
		setHasOptionsMenu( true );

		loadData();
		refreshView();

		return fragmentLayout.getRootView();
	}

	private void loadData() {

		if ( editMode )
			spot = SpotTable.from( getActivity() ).find( spotId );
	}

	private void refreshView() {

		fragmentLayout.setTitle( spotType + " 등록하기" );

		if ( spotType.equals( "명소" ) )
			fragmentLayout.setSpotTypeTextColorViewSpot();
		else if ( spotType.equals( "맛집" ) )
			fragmentLayout.setSpotTypeTextColorRestaurant();
		else
			fragmentLayout.setSpotTypeTextColorLogment();

		if ( editMode )
			setDataForEdit();
	}

	private void setDataForEdit() {

		fragmentLayout.setSpotName( spot.getName() );
		fragmentLayout.setContent( spot.getContent() );
		fragmentLayout.setTextView_search_spot_map( spot.getAddress() );
		fragmentLayout.setSearchSpotWebUrl( spot.getSite() );

		longitude = spot.getLongitude();
		latitude = spot.getLatitude();

		image = ImageTable.from( getActivity() ).findImageFrom( "spot", spotId );
		if ( null != image ) {

			fragmentLayout.setImage( ImageFileUtil.from( getActivity() ).getFilePath( image.getImageName() ) );
			isExistImage = true;
		}
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		fragmentLayout.onCreateOptionsMenu( menu, inflater );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		if ( fragmentLayout.onOptionsItemSelected( item ) )
			return true;
		else
			return super.onOptionsItemSelected( item );
	}

	@Override
	public void onSaveSpot() {

		Spot spot = getSpot();

		long spotId = spot.getId();

		if ( isValidSpot( spot ) ) {

			if ( !editMode )
				spotId = ContentUris.parseId( SpotTable.from( getActivity() ).insert( spot ) );
			else
				SpotTable.from( getActivity() ).update( spot );

			OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
			getActivity().finish();
		}

		if ( null != image ) {

			image.setImageId( spotId );
			ImageTable.from( getActivity() ).update( image );
		}
	}

	private Spot getSpot() {

		if ( null == spot )
			spot = new Spot();

		spot.setPlanId( planId );
		spot.setName( fragmentLayout.getSpotName() );
		spot.setAddress( fragmentLayout.getTextView_search_spot_map() );
		spot.setSite( fragmentLayout.getSearchSpotWebUrl() );
		spot.setLongitude( longitude );
		spot.setLatitude( latitude );
		spot.setContent( fragmentLayout.getContent() );
		spot.setType( spotType );

		return spot;
	}

	private boolean isValidSpot( Spot spot ) {

		if ( TextUtils.isEmpty( spot.getName() ) ) {

			fragmentLayout.setErrorSpotName();
			return false;
		}

		return true;
	}

	/**
	 * onClickListener
	 */

	@Override
	public void onClickImage() {

		ImagePickerDialogFragment.newInstance()
				.setExistImage( isExistImage )
				.setMaxImageCount( 1 )
				.setCropEnable( true )
				.setAspect( 5, 3 )
				.showDialog( getFragmentManager(), this );
	}

	@Override
	public void onImagePicked( ArrayList<String> imageNames ) {

		String imageName = imageNames.get( 0 );

		if ( null == image ) {

			image = new Image();
			image.setImageType( "spot" );
			image.setImageName( imageName );

			long imageId = ContentUris.parseId( ImageTable.from( getActivity() ).insert( image ) );
			image.setId( imageId );
		}
		else {

			image.setImageType( "spot" );
			image.setImageName( imageName );

			ImageTable.from( getActivity() ).update( image );
		}

		fragmentLayout.setImage( ImageFileUtil.from( getActivity() ).getFilePath( imageNames.get( 0 ) ) );

		isExistImage = true;
	}

	@Override
	public void onDeleteImage() {

		TousPreference.getInstance( getActivity() ).setPlanImageName( null );
		fragmentLayout.setImage( null );
		isExistImage = false;
	}

	@Override
	public void onSearchSpotInWeb() {

		String url = fragmentLayout.getSearchSpotWebUrl();

		if ( url.equals( "" ) )
			url = "http://www.naver.com";

		Intent intent = new Intent( getActivity(), WebViewActivity.class );
		intent.putExtra( WebViewActivity.KEY_URL, url );
		startActivity( intent );
	}

	@Override
	public void onSearchSpotAddressInMap() {

		String dd = fragmentLayout.getTextView_search_spot_map();
		if ( dd.isEmpty() ) {

			startActivityForResult( new Intent( getActivity(), SearchLocationActivity.class ), 1 );
		}
		else {

			Intent intent = new Intent( getActivity(), SearchLocationActivity.class );
			intent.putExtra( "address", fragmentLayout.getTextView_search_spot_map() );
			intent.putExtra( "longitude", longitude );
			intent.putExtra( "latitude", latitude );
			startActivity( intent );
		}
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {

		switch ( requestCode ) {

			case 1:

				if ( Activity.RESULT_OK == resultCode ) {

					fragmentLayout.setTextView_search_spot_map( data.getStringExtra( "key_address_name" ) );
					longitude = data.getDoubleExtra( "longitude", 0 );
					latitude = data.getDoubleExtra( "latitude", 0 );
				}

				break;
		}
	}

	@Subscribe
	public void onWebUrlCopyEvent( OnWebUrlCopyEvent onWebUrlCopyEvent ) {

		fragmentLayout.setSearchSpotWebUrl( onWebUrlCopyEvent.getCopyUrl() );
	}

	@Override
	public boolean onBackPressed() {

		OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
		return super.onBackPressed();
	}

	@Override
	public void onDestroyView() {

		OttoUtil.getInstance().unregister( this );
		super.onDestroyView();
	}

	public SpotCreationFragment setType( String spotType ) {

		this.spotType = spotType;
		return this;
	}

	public SpotCreationFragment setPlanId( long planId ) {

		this.planId = planId;
		return this;
	}

	public SpotCreationFragment setSpotId( long spotId ) {

		this.spotId = spotId;
		return this;
	}

	public SpotCreationFragment setEditMode( boolean editMode ) {

		this.editMode = editMode;
		return this;
	}

	public static SpotCreationFragment newInstance() {

		return new SpotCreationFragment();
	}

}
