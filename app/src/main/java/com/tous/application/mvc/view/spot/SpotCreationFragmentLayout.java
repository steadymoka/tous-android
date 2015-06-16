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

	private EditText editText_spot_name;
	private LinearLayout linearLayout_plan_day;
	private Button button_startTime;
	private Button button_endTime;
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

		editText_spot_name = (EditText) findViewById( R.id.editText_spot_name );

		linearLayout_plan_day = (LinearLayout) findViewById( R.id.linearLayout_plan_day );
		linearLayout_plan_day.setOnClickListener( this );

		button_startTime = (Button) findViewById( R.id.button_startTime );
		button_startTime.setOnClickListener( this );

		button_endTime = (Button) findViewById( R.id.button_endTime );
		button_endTime.setOnClickListener( this );

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

			case R.id.linearLayout_plan_day:

				getLayoutListener().onSetPlanDay();
				break;

			case R.id.textView_search_spot_map:

				getLayoutListener().onSearchSpotAddressInMap();
				break;

			case R.id.textView_search_spot_web:

				getLayoutListener().onSearchSpotInWeb();
				break;

			case R.id.button_startTime:

				getLayoutListener().onShowStartTimePicker();
				break;

			case R.id.button_endTime:

				getLayoutListener().onShowEndTimePicker();
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

}
