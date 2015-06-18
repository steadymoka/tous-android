package com.tous.application.mvc.controller.dialog;


import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.moka.framework.controller.BaseDialogFragment;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.mvc.model.spot.Spot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SetDayCountAndTimeDialogFragment extends BaseDialogFragment implements OnClickListener, SetTimeItemView.OnItemClickListener {

	private ObservableRecyclerView observableRecyclerView_setTime;
	private View rootView;
	private Button button_save;
	private TextView textView_dayCount;
	private TextView textView_preDayCount;
	private TextView textView_nextDayCount;

	private SetTimeRecyclerViewAdapter setTimeRecyclerViewAdapter;
	private OnDayCountSelectedListener onDaySavedListener;

	private Spot spot;
	private int currentDayCount = 1;
	private int maxValue = 1;
	private boolean isCanceled = true;

	private ArrayList<Integer> timeOfHourList;

	@NonNull
	@SuppressLint( "InflateParams" )
	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {

		rootView = getActivity().getLayoutInflater().inflate( R.layout.dialog_set_day_count_and_time, null );

		timeOfHourList = new ArrayList<>();
		initView();

		Builder builder = new Builder( getActivity() );
		builder.setView( rootView );

		return builder.create();
	}

	private void initView() {

		textView_dayCount = (TextView) rootView.findViewById( R.id.textView_dayCount );
		if ( 0 != spot.getDayCount() )
			textView_dayCount.setText( spot.getDayCount() + "일차" );

		textView_preDayCount = (TextView) rootView.findViewById( R.id.textView_preDayCount );
		textView_preDayCount.setOnClickListener( this );
		textView_nextDayCount = (TextView) rootView.findViewById( R.id.textView_nextDayCount );
		textView_nextDayCount.setOnClickListener( this );

		observableRecyclerView_setTime = (ObservableRecyclerView) rootView.findViewById( R.id.observableRecyclerView_setTime );

		LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
		observableRecyclerView_setTime.setLayoutManager( layoutManager );
		observableRecyclerView_setTime.setAdapter( getRecyclerAdapter() );

		button_save = (Button) rootView.findViewById( R.id.button_save );
		button_save.setOnClickListener( this );
	}

	private RecyclerView.Adapter getRecyclerAdapter() {

		setTimeRecyclerViewAdapter = new SetTimeRecyclerViewAdapter( getActivity() );
		setAdapter();

		return setTimeRecyclerViewAdapter;
	}

	private void setAdapter() {

		if ( null == setTimeRecyclerViewAdapter )
			setTimeRecyclerViewAdapter = new SetTimeRecyclerViewAdapter( getActivity() );

		List<SetTimeItemData> setTimeItemDatas = new ArrayList<>();
		List<Spot> spotList = SpotTable.from( getActivity() ).findSpotListOfDayCountAndPlan( getCurrentDayCount(), spot.getPlanId() );

		for ( int i = 6; i < 25; i++ )
			setTimeItemDatas.add( new SetTimeItemData( spotList, spot, i ) );

		setTimeRecyclerViewAdapter.setItems( setTimeItemDatas );
		setTimeRecyclerViewAdapter.setOnItemClickListener( this );

		timeOfHourList.clear();
	}

	@Override
	public void onItemClick( SetTimeItemData setTimeItemData ) {

		int timeOfHour = setTimeItemData.getTimeOfHour();

		if ( !timeOfHourList.contains( timeOfHour ) )
			timeOfHourList.add( setTimeItemData.getTimeOfHour() );
		else
			timeOfHourList.remove( Integer.valueOf( timeOfHour ) );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.textView_preDayCount:

				if ( getCurrentDayCount() > 1 ) {

					textView_dayCount.setText( getCurrentDayCount() - 1 + "일차" );
					setAdapter();
				}
				break;

			case R.id.textView_nextDayCount:

				if ( getCurrentDayCount() < maxValue ) {

					textView_dayCount.setText( getCurrentDayCount() + 1 + "일차" );
					setAdapter();
				}
				break;

			case R.id.button_save:

				if ( isValid() ) {

					onDaySavedListener.onDayCountSelected( getCurrentDayCount() );

					saveSpot();
					isCanceled = false;

					dismiss();
				}
				else {

					onDaySavedListener.onInvalid();
				}
				break;
		}
	}

	private boolean isValid() {

		if ( timeOfHourList.isEmpty() )
			return false;

		Collections.sort( timeOfHourList );
		for ( int i = 0; i < timeOfHourList.size(); i++ ) {

			if ( 0 < i ) {

				if ( timeOfHourList.get( i - 1 ) + 1 != timeOfHourList.get( i ) )
					return false;
			}
		}
		return true;
	}

	private void saveSpot() {

		spot.setDayCount( getCurrentDayCount() );
		spot.setStartTime( timeOfHourList.get( 0 ).toString() + ":00" );
		spot.setEndTime( timeOfHourList.get( timeOfHourList.size() - 1 ).toString() + ":00" );
		SpotTable.from( getActivity() ).update( spot );
	}

	private int getCurrentDayCount() {

		return Integer.valueOf( textView_dayCount.getText().toString().substring( 0, 1 ) );
	}

	@Override
	public void onDismiss( DialogInterface dialog ) {

		super.onDismiss( dialog );
	}

	public SetDayCountAndTimeDialogFragment setOnDismissDayCountDialogListener( OnDismissDialogListener onDismissDialogListener ) {

		setOnDismissDialogListener( onDismissDialogListener );
		return this;
	}

	public SetDayCountAndTimeDialogFragment setDayCount( int dayCount ) {

		maxValue = dayCount;
		return this;
	}

	public SetDayCountAndTimeDialogFragment setCurrentDayCount( int currentDayCount ) {

		this.currentDayCount = currentDayCount;
		return this;
	}

	public SetDayCountAndTimeDialogFragment setOnDaySavedListener( OnDayCountSelectedListener onDaySavedListener ) {

		this.onDaySavedListener = onDaySavedListener;
		return this;
	}

	public SetDayCountAndTimeDialogFragment setSpot( Spot spot ) {

		this.spot = spot;
		return this;
	}

	public void showDialog( FragmentManager fragmentManager, OnDayCountSelectedListener onDaySavedListener ) {

		setOnDaySavedListener( onDaySavedListener );
		show( fragmentManager, "SetDayCountDialogFragment" );
	}

	public static SetDayCountAndTimeDialogFragment newInstance() {

		return new SetDayCountAndTimeDialogFragment();
	}

	public interface OnDayCountSelectedListener {

		void onDayCountSelected( int dayCount );

		void onInvalid();

	}

}
