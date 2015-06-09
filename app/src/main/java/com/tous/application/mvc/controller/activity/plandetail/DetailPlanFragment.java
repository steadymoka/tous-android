package com.tous.application.mvc.controller.activity.plandetail;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.app.SlidingTabPagerAdapter;
import com.moka.framework.app.SlidingTabPagerAdapter.TabInfo;
import com.moka.framework.controller.BaseFragment;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationActivity;
import com.tous.application.mvc.controller.activity.plandetail.lodgment.LodgmentFragment;
import com.tous.application.mvc.controller.activity.plandetail.restaurant.RestaurantFragment;
import com.tous.application.mvc.controller.activity.plandetail.viewspot.ViewSpotFragment;
import com.tous.application.mvc.controller.activity.plandetail.transport.TransportFragment;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.DetailPlanFragmentLayout;
import com.tous.application.mvc.view.plandetail.DetailPlanFragmentLayoutListener;


public class DetailPlanFragment extends BaseFragment implements DetailPlanFragmentLayoutListener {

	public static final String TYPE_VIEW_SPOT = "명소";
	public static final String TYPE_RESTAURANT = "맛집";
	public static final String TYPE_LODGMENT = "숙소";

	private DetailPlanFragmentLayout fragmentLayout;

	private RestaurantFragment restaurantFragment;
	private TransportFragment transportFragment;
	private ViewSpotFragment viewSpotFragment;
	private LodgmentFragment lodgmentFragment;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new DetailPlanFragmentLayout( this, this, inflater, container );
		setHasOptionsMenu( true );

		return fragmentLayout.getRootView();
	}

	@Override
	public SlidingTabPagerAdapter getSlidingTabPagerAdapter() {

		SlidingTabPagerAdapter slidingTabPagerAdapter = new SlidingTabPagerAdapter( getFragmentManager() );

		if ( null == transportFragment )
			transportFragment = TransportFragment.newInstance().setPlan( plan );
		slidingTabPagerAdapter.addItem( new TabInfo( transportFragment, getString( R.string.activity_task_detail_tab_transport ) ) );

		if ( null == viewSpotFragment )
			viewSpotFragment = ViewSpotFragment.newInstance().setPlan( plan );
		slidingTabPagerAdapter.addItem( new TabInfo( viewSpotFragment, getString( R.string.activity_task_detail_tab_spot ) ) );

		if ( null == restaurantFragment )
			restaurantFragment = RestaurantFragment.newInstance().setPlan( plan );
		slidingTabPagerAdapter.addItem( new TabInfo( restaurantFragment, getString( R.string.activity_task_detail_tab_restaurant ) ) );

		if ( null == lodgmentFragment )
			lodgmentFragment = LodgmentFragment.newInstance().setPlan( plan );
		slidingTabPagerAdapter.addItem( new TabInfo( lodgmentFragment, getString( R.string.activity_task_detail_tab_lodgment ) ) );

		return slidingTabPagerAdapter;
	}

	@Override
	public void onEditPlanMenuItemSelected() {

		Intent intent = new Intent( getActivity(), PlanCreationActivity.class );
		intent.putExtra( PlanCreationActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
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

	public DetailPlanFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	public static DetailPlanFragment newInstance() {

		return new DetailPlanFragment();
	}

}
