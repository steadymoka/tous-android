package com.tous.application.mvc.view.plancreation;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationFragment;


public class PlanCreationFragmentLayout extends FragmentLayout<PlanCreationFragment, PlanCreationFragmentLayoutListener> {

	private EditText editText_plan_name;

	public PlanCreationFragmentLayout( PlanCreationFragment fragment, PlanCreationFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_creation;
	}

	@Override
	protected void onLayoutInflated() {

		editText_plan_name = (EditText) findViewById( R.id.editText_plan_name );
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

	public String getPlanName() {

		return editText_plan_name.getText().toString();
	}

	public void setPlanName( String name ) {

		editText_plan_name.setText( name );
	}

}
