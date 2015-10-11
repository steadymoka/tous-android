package com.tous.application.mvc.view.plandetail.lodgment;


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
import com.tous.application.mvc.controller.activity.plandetail.lodgment.LodgmentFragment;


public class LodgmentFragmentLayout extends FragmentLayout<LodgmentFragment, LodgmentFragmentLayoutListener> implements View.OnClickListener {

	private ObservableRecyclerView observableRecyclerView_lodgment;
	private FloatingActionButton floatingActionButton_add_lodgment;

	private TextView textView_empty;

	private OnScrollDelegate onScrollDelegate;

	public LodgmentFragmentLayout( LodgmentFragment fragment, LodgmentFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_plan_lodgment;
	}

	@Override
	protected void onLayoutInflated() {

		initView();
		bindView();
	}

	private void initView() {

		observableRecyclerView_lodgment = (ObservableRecyclerView) findViewById( R.id.observableRecyclerView_lodgment );
		floatingActionButton_add_lodgment = (FloatingActionButton) findViewById( R.id.floatingActionButton_add_lodgment );

		textView_empty = (TextView) findViewById( R.id.textView_empty );
	}

	private void bindView() {

		onScrollDelegate = new OnScrollDelegate();

		observableRecyclerView_lodgment.setOnScrollDelegate( onScrollDelegate );
		LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
		observableRecyclerView_lodgment.setLayoutManager( layoutManager );
		observableRecyclerView_lodgment.setAdapter( getLayoutListener().getRecyclerViewAdapter() );

		floatingActionButton_add_lodgment.setOnClickListener( this );
		floatingActionButton_add_lodgment
				.attachToRecyclerView( observableRecyclerView_lodgment, onScrollDelegate );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.floatingActionButton_add_lodgment:

				getLayoutListener().onClickToAddLodgment();
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
