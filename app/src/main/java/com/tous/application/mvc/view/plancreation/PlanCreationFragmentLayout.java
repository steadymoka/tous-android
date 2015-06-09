package com.tous.application.mvc.view.plancreation;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationFragment;

import java.util.Date;


public class PlanCreationFragmentLayout extends FragmentLayout<PlanCreationFragment, PlanCreationFragmentLayoutListener> implements View.OnClickListener {

	private EditText editText_plan_name;

	private Button button_startDate;
	private Button button_endDate;

	private EditText editText_content;

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

		button_startDate = (Button) findViewById( R.id.button_startDate );
		button_startDate.setOnClickListener( this );

		button_endDate = (Button) findViewById( R.id.button_endDate );
		button_endDate.setOnClickListener( this );

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

			case R.id.button_startDate:

				getLayoutListener().onShowStartDatePicker();
				break;

			case R.id.button_endDate:

				getLayoutListener().onShowEndDatePicker();
				break;
		}
	}

	public String getPlanName() {

		return editText_plan_name.getText().toString();
	}

	public void setPlanName( String name ) {

		editText_plan_name.setText( name );
	}

	public void setStartDate( String startDateString ) {

		button_startDate.setText( startDateString );
	}

	public void setEndDate( String endDateString ) {

		button_endDate.setText( endDateString );
	}

	public String getPlanContent() {

		return editText_content.getText().toString();
	}

	public void setPlanContent( String content ) {

		editText_content.setText( content );
	}

}
