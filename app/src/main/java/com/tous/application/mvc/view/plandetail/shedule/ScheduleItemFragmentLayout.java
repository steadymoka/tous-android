package com.tous.application.mvc.view.plandetail.shedule;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.adapter.OnScrollDelegate;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.schedule.ScheduleItemItemFragment;


public class ScheduleItemFragmentLayout extends FragmentLayout<ScheduleItemItemFragment, ScheduleItemFragmentLayoutListener> implements View.OnClickListener {

	private ObservableRecyclerView observableRecyclerView_schedule;
	private OnScrollDelegate onScrollDelegate;
	private TextView textView_empty;

	public ScheduleItemFragmentLayout( ScheduleItemItemFragment fragment, ScheduleItemFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_schedule_item;
	}

	@Override
	protected void onLayoutInflated() {

		initView();
		bindView();
	}

	private void initView() {

		observableRecyclerView_schedule = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_schedule );
		textView_empty = (TextView) findViewById( R.id.textView_empty );
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

	}

	public void setEmptyMessage( boolean isEmpty ) {

		if ( isEmpty )
			textView_empty.setVisibility( View.VISIBLE );
		else
			textView_empty.setVisibility( View.GONE );
	}

}
