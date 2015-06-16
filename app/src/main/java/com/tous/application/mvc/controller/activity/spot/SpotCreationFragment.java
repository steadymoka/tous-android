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

import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.browser.WebViewActivity;
import com.tous.application.mvc.controller.dialog.SetDayCountDialogFragment;
import com.tous.application.mvc.model.transport.Spot;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayout;
import com.tous.application.mvc.view.spot.SpotCreationFragmentLayoutListener;


public class SpotCreationFragment extends BaseFragment implements SpotCreationFragmentLayoutListener, SetDayCountDialogFragment.OnDoneAmountInputtedListener {

	private SpotCreationFragmentLayout fragmentLayout;

	private RadialTimePickerDialog.OnTimeSetListener startTimeSetListener;
	private RadialTimePickerDialog.OnTimeSetListener endTimeSetListener;

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
	public void onSetPlanDay() {

		SetDayCountDialogFragment.newInstance()
				.setDayCount( 5 )
				.showDialog( getFragmentManager(), this );
	}

	@Override
	public void onDoneAmountInputted( int previousAmount ) {

	}

	@Override
	public void onInputCanceled() {

	}

	@Override
	public void onShowStartTimePicker() {

		if ( null == startTimeSetListener ) {

			startTimeSetListener = new RadialTimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet( RadialTimePickerDialog radialTimePickerDialog, int hourOfDay, int minute ) {

				}

			};
		}

		RadialTimePickerDialog radialTimePickerDialog = RadialTimePickerDialog.newInstance( null, 24, 60, false );
		radialTimePickerDialog.show( getFragmentManager(), "tag" );
	}

	@Override
	public void onShowEndTimePicker() {

		RadialTimePickerDialog radialTimePickerDialog = RadialTimePickerDialog.newInstance( null, 24, 60, true );
		radialTimePickerDialog.show( getFragmentManager(), "tag" );
	}

	@Override
	public void onSearchSpotInWeb() {

		Intent intent = new Intent( getActivity(), WebViewActivity.class );
		intent.putExtra( WebViewActivity.KEY_URL, "http://www.naver.com" );
		startActivity( intent );
	}

	@Override
	public void onSearchSpotAddressInMap() {

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
