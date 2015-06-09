package com.tous.application.mvc.view.planlist;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.adapter.OnScrollDelegate;
import com.moka.framework.widget.fab.FloatingActionButton;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.planlist.PlanListFragment;


public class PlanListFragmentLayout extends FragmentLayout<PlanListFragment, PlanListFragmentLayoutListener> implements View.OnClickListener {

	private ObservableRecyclerView observableRecyclerView_plan_list;
	private FloatingActionButton floatingActionButton_add_plan;

	private OnScrollDelegate onScrollDelegate;

	public PlanListFragmentLayout( PlanListFragment fragment, PlanListFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_list;
	}

	@Override
	protected void onLayoutInflated() {

		initView();
		bindView();
	}

	private void initView() {

		observableRecyclerView_plan_list = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_plan_list );
		floatingActionButton_add_plan = (FloatingActionButton) findViewById( R.id.floatingActionButton_add_plan );
	}

	private void bindView() {

		onScrollDelegate = new OnScrollDelegate();

		observableRecyclerView_plan_list.setOnScrollDelegate( onScrollDelegate );
		LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
		observableRecyclerView_plan_list.setLayoutManager( layoutManager );
		observableRecyclerView_plan_list.setAdapter( getLayoutListener().getRecyclerViewAdapter() );

		floatingActionButton_add_plan.setOnClickListener( this );
		floatingActionButton_add_plan
				.attachToRecyclerView( observableRecyclerView_plan_list, onScrollDelegate );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.floatingActionButton_add_plan:

				getLayoutListener().onClickToAddPlan();
				break;
		}
	}

}
