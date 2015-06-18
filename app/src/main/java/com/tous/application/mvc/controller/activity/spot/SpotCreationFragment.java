package com.tous.application.mvc.controller.activity.spot;


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
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.browser.WebViewActivity;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayout;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayoutListener;


public class SpotCreationFragment extends BaseFragment implements SpotCreationFragmentLayoutListener {

	private SpotCreationFragmentLayout fragmentLayout;

	private String spotType;

	private long planId;
	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new SpotCreationFragmentLayout( this, this, inflater, container );
		setHasOptionsMenu( true );

		refreshView();

		fragmentLayout.setTitle( spotType + " 등록하기" );

		return fragmentLayout.getRootView();
	}

	private void refreshView() {

//		onClickToSpot();
		plan = PlanTable.from( getActivity() ).find( planId );
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
		spot.setContent( fragmentLayout.getContent() );
		spot.setType( spotType );

		if ( isValidSpot( spot ) ) {

			SpotTable.from( getActivity() ).insert( spot );
			OttoUtil.getInstance().post( new OnRefreshViewEvent() );
			getActivity().finish();
		}
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
	public void onSearchSpotInWeb() {

		Intent intent = new Intent( getActivity(), WebViewActivity.class );
		intent.putExtra( WebViewActivity.KEY_URL, "http://www.naver.com" );
		startActivity( intent );
	}

	@Override
	public void onSearchSpotAddressInMap() {

	}

//	@Override
//	public void onClickToSpot() {
//
//		spotType = DetailPlanFragment.TYPE_VIEW_SPOT;
//		fragmentLayout.onClickToSpot();
//	}
//
//	@Override
//	public void onClickToRestaurant() {
//
//		spotType = DetailPlanFragment.TYPE_RESTAURANT;
//		fragmentLayout.onClickToRestaurant();
//	}

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
