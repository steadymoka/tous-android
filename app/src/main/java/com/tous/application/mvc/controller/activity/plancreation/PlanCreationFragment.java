package com.tous.application.mvc.controller.activity.plancreation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plancreation.PlanCreationFragmentLayout;
import com.tous.application.mvc.view.plancreation.PlanCreationFragmentLayoutListener;


public class PlanCreationFragment extends BaseFragment implements PlanCreationFragmentLayoutListener {

	private PlanCreationFragmentLayout fragmentLayout;
	private long planId;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new PlanCreationFragmentLayout( this, this, inflater, container );
		setHasOptionsMenu( true );

		if ( -1 != planId ) {

			fragmentLayout.setPlanName( PlanTable.from( getActivity() ).find( planId ).getName() );
		}
		return fragmentLayout.getRootView();
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
	public void onSavePlan() {

		Plan plan = new Plan(); // TODO 땜빵
		plan.setName( fragmentLayout.getPlanName() );
		plan.setStartDate( 20150531 );
		plan.setEndDate( 20150605 );
		PlanTable.from( getActivity() ).insert( plan );

		OttoUtil.getInstance().post( new OnRefreshViewEvent() );
		getActivity().finish();
	}

	public PlanCreationFragment setPlanId( long planId ) {

		this.planId = planId;
		return this;
	}

	public static PlanCreationFragment newInstance() {

		return new PlanCreationFragment();
	}

}
