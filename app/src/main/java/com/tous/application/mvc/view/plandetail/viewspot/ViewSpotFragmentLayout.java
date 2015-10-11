package com.tous.application.mvc.view.plandetail.viewspot;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.adapter.OnScrollDelegate;
import com.moka.framework.widget.fab.FloatingActionButton;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.viewspot.ViewSpotFragment;


public class ViewSpotFragmentLayout extends FragmentLayout<ViewSpotFragment, ViewSpotFragmentLayoutListener> implements View.OnClickListener {

	private ObservableRecyclerView observableRecyclerView_spots;
	private FloatingActionButton floatingActionButton_add_spot;
	private TextView textView_empty;

	private OnScrollDelegate onScrollDelegate;

	public ViewSpotFragmentLayout( ViewSpotFragment fragment, ViewSpotFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_spot;
	}

	@Override
	protected void onLayoutInflated() {

		initView();
		bindView();
	}

	private void initView() {

		observableRecyclerView_spots = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_spots );
		floatingActionButton_add_spot = (FloatingActionButton) findViewById( R.id.floatingActionButton_add_spot );
		textView_empty = (TextView) findViewById( R.id.textView_empty );
	}

	private void bindView() {

		onScrollDelegate = new OnScrollDelegate();

		observableRecyclerView_spots.setOnScrollDelegate( onScrollDelegate );
		LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
		observableRecyclerView_spots.setLayoutManager( layoutManager );
		observableRecyclerView_spots.setAdapter( getLayoutListener().getRecyclerViewAdapter() );

		floatingActionButton_add_spot.setOnClickListener( this );
		floatingActionButton_add_spot
				.attachToRecyclerView( observableRecyclerView_spots, onScrollDelegate );

	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.floatingActionButton_add_spot:

				getLayoutListener().onClickToAddSpot();
				break;
		}
	}

	public void setEmptyMessage( boolean isEmpty ) {

		if ( isEmpty )
			textView_empty.setVisibility( View.VISIBLE );
		else
			textView_empty.setVisibility( View.GONE );
	}

}
