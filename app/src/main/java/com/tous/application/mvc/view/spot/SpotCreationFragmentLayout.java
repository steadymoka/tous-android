package com.tous.application.mvc.view.spot;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotCreationFragment;


public class SpotCreationFragmentLayout extends FragmentLayout<SpotCreationFragment, SpotCreationFragmentLayoutListener> {

	private EditText editText_spot_name;

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

	public void setTitle( String spotType ) {

		getActivity().setTitle( spotType );
	}

	public String getSpotName() {

		return editText_spot_name.getText().toString();
	}

}
