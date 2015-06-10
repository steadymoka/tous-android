package com.tous.application.mvc.view.main.shedule;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.adapter.OnScrollDelegate;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.schedule.ScheduleItemFragment;


public class ScheduleItemFragmentLayout extends FragmentLayout<ScheduleItemFragment, ScheduleFragmentLayoutListener> implements View.OnClickListener {

	private ObservableRecyclerView observableRecyclerView_schedule;
	private OnScrollDelegate onScrollDelegate;
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

		initView();
		bindView();
	}

	private void initView() {

		textView_no = (TextView) findViewById( R.id.textView_no );
		textView_no.setOnClickListener( this );

		observableRecyclerView_schedule = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_schedule );
	}

	private void bindView() {

		onScrollDelegate = new OnScrollDelegate();

		observableRecyclerView_schedule.setOnScrollDelegate( onScrollDelegate );
		LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
		observableRecyclerView_schedule.setLayoutManager( layoutManager );
		observableRecyclerView_schedule.setAdapter( getLayoutListener().getRecyclerViewAdapter() );
		observableRecyclerView_schedule.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {

		getLayoutListener().onClickToDetail();
	}

	public void setNoMode() {

		observableRecyclerView_schedule.setVisibility( View.GONE );
		textView_no.setVisibility( View.VISIBLE );
	}

	public void setYesMode() {

		observableRecyclerView_schedule.setVisibility( View.VISIBLE );
		textView_no.setVisibility( View.GONE );
	}

}
