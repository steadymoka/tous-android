package com.tous.application.mvc.view.spot;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.ScreenUtil;
import com.moka.framework.view.FragmentLayout;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotCreationFragment;

import java.io.File;


public class SpotCreationFragmentLayout extends FragmentLayout<SpotCreationFragment, SpotCreationFragmentLayoutListener> implements View.OnClickListener {

	private ImageView imageView_addImage;

	private TextView textView_spotType;
	private EditText editText_spot_name;

	private LinearLayout linearLayout_search_spot_map;
	private TextView textView_search_spot_map;

	private LinearLayout linearLayout_search_spot_web;
	private TextView textView_search_spot_web;
	private EditText editText_content;

	public SpotCreationFragmentLayout( SpotCreationFragment fragment, SpotCreationFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_spot_creation;
	}

	@Override
	protected void onLayoutInflated() {

		textView_spotType = (TextView) findViewById( R.id.textView_spotType );

		imageView_addImage = (ImageView) findViewById( R.id.imageView_addImage );
		imageView_addImage.setOnClickListener( this );

		editText_spot_name = (EditText) findViewById( R.id.editText_spot_name );

		linearLayout_search_spot_map = (LinearLayout) findViewById( R.id.linearLayout_search_spot_map );
		linearLayout_search_spot_map.setOnClickListener( this );

		linearLayout_search_spot_web = (LinearLayout) findViewById( R.id.linearLayout_search_spot_web );
		linearLayout_search_spot_web.setOnClickListener( this );

		textView_search_spot_map = (TextView) findViewById( R.id.textView_search_spot_map );
		textView_search_spot_web = (TextView) findViewById( R.id.textView_search_spot_web );

		editText_content = (EditText) findViewById( R.id.editText_content );
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		inflater.inflate( R.menu.fragment_plan_or_spot_creation, menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case R.id.menuItem_save:

				getLayoutListener().onSaveSpot();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.imageView_addImage:

				getLayoutListener().onClickImage();
				break;

			case R.id.linearLayout_search_spot_map:

				getLayoutListener().onSearchSpotAddressInMap();
				break;

			case R.id.linearLayout_search_spot_web:

				getLayoutListener().onSearchSpotInWeb();
				break;
		}
	}

	public void setTitle( String spotType ) {

		getActivity().setTitle( spotType );
	}

	public void setSpotTypeTextColorViewSpot() {

		textView_spotType.setTextColor( 0xFF80cbc4 );
	}

	public void setSpotTypeTextColorRestaurant() {

		textView_spotType.setTextColor( 0xFFffa726 );
	}

	public void setSpotTypeTextColorLogment() {

		textView_spotType.setTextColor( 0xFF512da8 );
	}

	public void setErrorSpotName() {

		editText_spot_name.setError( getActivity().getString( R.string.fragment_spot_creation_error_spot_name ) );
	}

	public void setSpotName( String spotName ) {

		editText_spot_name.setText( spotName );
	}

	public String getSpotName() {

		return editText_spot_name.getText().toString();
	}

	public String getContent() {

		return editText_content.getText().toString();
	}

	public void setContent( String content ) {

		editText_content.setText( content );
	}

	public void setTextView_search_spot_map( String address ) {

		textView_search_spot_map.setText( address );
	}

	public String getTextView_search_spot_map() {

		return textView_search_spot_map.getText().toString();
	}

	public String getSearchSpotWebUrl() {

		return textView_search_spot_web.getText().toString();
	}

	public void setSearchSpotWebUrl( String copyUrl ) {

		textView_search_spot_web.setText( copyUrl );
	}

	public void setImage( String imagePath ) {

		int defaultImage = R.drawable.image_add_image;

		int headerWidth = ScreenUtil.getWidthPixels( getContext() );
		int headerHeight = ScreenUtil.getHeightPixels( getContext() );
//		int headerHeight = (int) ( headerWidth / 1.5f );

		if ( null != imagePath ) {

			Picasso.with( getContext() )
					.load( new File( imagePath ) )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 1.7 ) )
					.into( imageView_addImage );
		}
		else {

			Picasso.with( getContext() )
					.load( defaultImage )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 1.7 ) )
					.into( imageView_addImage );
		}
	}

}
