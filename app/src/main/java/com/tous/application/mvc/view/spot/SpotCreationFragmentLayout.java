package com.tous.application.mvc.view.spot;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotCreationFragment;


public class SpotCreationFragmentLayout extends FragmentLayout<SpotCreationFragment, SpotCreationFragmentLayoutListener> implements View.OnClickListener {

//	private TextView textView_spot;
//	private TextView textView_restaurant;

	private EditText editText_spot_name;
	private TextView textView_search_spot_map;
	private TextView textView_search_spot_wab;
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

//		textView_spot = (TextView) findViewById( R.id.textView_spot );
//		textView_spot.setOnClickListener( this );
//
//		textView_restaurant = (TextView) findViewById( R.id.textView_restaurant );
//		textView_restaurant.setOnClickListener( this );
//		onClickToSpot();

		editText_spot_name = (EditText) findViewById( R.id.editText_spot_name );

		textView_search_spot_map = (TextView) findViewById( R.id.textView_search_spot_map );
		textView_search_spot_map.setOnClickListener( this );

		textView_search_spot_wab = (TextView) findViewById( R.id.textView_search_spot_web );
		textView_search_spot_wab.setOnClickListener( this );

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

				getLayoutListener().onSavePlan();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

//			case R.id.textView_spot:
//
//				getLayoutListener().onClickToSpot();
//				break;
//
//			case R.id.textView_restaurant:
//
//				getLayoutListener().onClickToRestaurant();
//				break;

			case R.id.textView_search_spot_map:

				getLayoutListener().onSearchSpotAddressInMap();
				break;

			case R.id.textView_search_spot_web:

				getLayoutListener().onSearchSpotInWeb();
				break;
		}
	}

	public void setTitle( String spotType ) {

		getActivity().setTitle( spotType );
	}

	public void setErrorSpotName() {

		editText_spot_name.setError( getActivity().getString( R.string.fragment_spot_creation_error_spot_name ) );
	}

	public String getSpotName() {

		return editText_spot_name.getText().toString();
	}

	public String getContent() {

		return editText_content.getText().toString();
	}

//	public void onClickToSpot() {
//
//		textView_spot.setBackgroundColor( 0xFFffcdd2 );
//		textView_restaurant.setBackgroundColor( 0xFFF4F4F4 );
//	}
//
//	public void onClickToRestaurant() {
//
//		textView_spot.setBackgroundColor( 0xFFF4F4F4 );
//		textView_restaurant.setBackgroundColor( 0xFFffcdd2 );
//	}

}
