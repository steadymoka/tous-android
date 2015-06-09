package com.tous.application.mvc.view.main.shedule;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.schedule.ScheduleItemFragment;


public class ScheduleItemFragmentLayout extends FragmentLayout<ScheduleItemFragment, ScheduleFragmentLayoutListener> implements View.OnClickListener {

	//	private ObservableRecyclerView observableRecyclerView_schedule;
//	private OnScrollDelegate onScrollDelegate;
	private ImageView tempId; // TODO 땜빵
	private TextView textView_no;

	public ScheduleItemFragmentLayout( ScheduleItemFragment fragment, ScheduleFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_schedule;
	}

	@Override
	protected void onLayoutInflated() {

		tempId = (ImageView) findViewById( R.id.tempId ); // TODO 땜빵
		tempId.setOnClickListener( this );

		textView_no = (TextView) findViewById( R.id.textView_no );
		textView_no.setOnClickListener( this );
//		initView();
//		bindView();
	}

//	private void initView() {
//
//		observableRecyclerView_schedule = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_spots );
//	}
//
//	private void bindView() {
//
//		onScrollDelegate = new OnScrollDelegate();
//
//		observableRecyclerView_schedule.setOnScrollDelegate( onScrollDelegate );
//		LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
//		observableRecyclerView_schedule.setLayoutManager( layoutManager );
//		observableRecyclerView_schedule.setAdapter( getLayoutListener().getRecyclerViewAdapter() );
//		observableRecyclerView_schedule.setOnClickListener( this );
//	}

	@Override
	public void onClick( View view ) {

		getLayoutListener().onClickToDetail();
	}

	// TODO 땜빵
	public void setNoMode() {

		tempId.setVisibility( View.GONE );
		textView_no.setVisibility( View.VISIBLE );
	}

	// TODO 땜빵
	public void setYesMode() {

		tempId.setVisibility( View.VISIBLE );
		textView_no.setVisibility( View.GONE );
	}

}
