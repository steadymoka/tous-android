package com.tous.application.mvc.controller.activity.spot;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.model.transport.Spot;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayout;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayoutListener;


public class SpotCreationFragment extends BaseFragment implements SpotCreationFragmentLayoutListener {

	private SpotCreationFragmentLayout fragmentLayout;

	private String spotType;
	private long planId;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new SpotCreationFragmentLayout( this, this, inflater, container );
		setHasOptionsMenu( true );

		fragmentLayout.setTitle( spotType + " 등록하기" );

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

		Spot spot = new Spot();
		spot.setPlanId( planId );
		spot.setName( fragmentLayout.getSpotName() );
		spot.setType( spotType );
		SpotTable.from( getActivity() ).insert( spot );

		OttoUtil.getInstance().post( new OnRefreshViewEvent() );
		getActivity().finish();
	}

	public SpotCreationFragment setType( String spotType ) {

		this.spotType = spotType;
		return this;
	}

	public SpotCreationFragment setPlanId( long planId ) {

		this.planId = planId;
		return this;
	}

	public static SpotCreationFragment newInstance() {

		return new SpotCreationFragment();
	}

}
