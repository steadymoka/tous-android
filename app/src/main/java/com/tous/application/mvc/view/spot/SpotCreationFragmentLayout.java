package com.tous.application.mvc.view.spot;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotCreationFragment;


public class SpotCreationFragmentLayout extends FragmentLayout<SpotCreationFragment, SpotCreationFragmentLayoutListener> implements View.OnClickListener {

	private EditText editText_spot_name;
	private Button button_startTime;
	private Button button_endTime;
	private TextView textView_search_spot;
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

		button_startTime = (Button) findViewById( R.id.button_startTime );
		button_startTime.setOnClickListener( this );

		button_endTime = (Button) findViewById( R.id.button_endTime );
		button_endTime.setOnClickListener( this );

		textView_search_spot = (TextView) findViewById( R.id.textView_search_spot );
		SpannableString contractString = new SpannableString( getContext().getString( R.string.fragment_spot_creation_search_spot ) );
		contractString.setSpan( new ForegroundColorSpan( 0xFF666666 ), 0, contractString.length(), Spanned.SPAN_COMPOSING );
		contractString.setSpan( new UnderlineSpan(), 0, contractString.length(), Spanned.SPAN_COMPOSING );
		textView_search_spot.setText( contractString );
		textView_search_spot.setOnClickListener( this );

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


		}
	}

	public void setTitle( String spotType ) {

		getActivity().setTitle( spotType );
	}

	public String getSpotName() {

		return editText_spot_name.getText().toString();
	}

}
