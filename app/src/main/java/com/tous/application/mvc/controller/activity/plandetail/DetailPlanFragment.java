package com.tous.application.mvc.controller.activity.plandetail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.app.SlidingTabPagerAdapter;
import com.moka.framework.app.SlidingTabPagerAdapter.TabInfo;
import com.moka.framework.controller.BaseFragment;
import com.moka.framework.support.toolbar.ToolbarLayout;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.plandetail.lodgment.LodgmentFragment;
import com.tous.application.mvc.controller.activity.plandetail.restaurant.RestaurantFragment;
import com.tous.application.mvc.controller.activity.plandetail.schedule.ScheduleFragment;
import com.tous.application.mvc.controller.activity.plandetail.transport.TransportFragment;
import com.tous.application.mvc.controller.activity.plandetail.viewspot.ViewSpotFragment;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.plandetail.DetailPlanActivityListener;
import com.tous.application.mvc.view.plandetail.DetailPlanFragmentLayout;
import com.tous.application.mvc.view.plandetail.DetailPlanFragmentLayoutListener;


public class DetailPlanFragment extends BaseFragment implements DetailPlanFragmentLayoutListener {

	public static final String TYPE_VIEW_SPOT = "명소";
	public static final String TYPE_RESTAURANT = "맛집";
	public static final String TYPE_LODGMENT = "숙소";

	private DetailPlanFragmentLayout fragmentLayout;
	private DetailPlanActivityListener detailPlanActivityListener;

	private ScheduleFragment scheduleFragment;
	private RestaurantFragment restaurantFragment;
	private TransportFragment transportFragment;
	private ViewSpotFragment viewSpotFragment;
	private LodgmentFragment lodgmentFragment;

	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new DetailPlanFragmentLayout( this, this, inflater, container );

		return fragmentLayout.getRootView();
	}

	@Override
	public void onResume() {

		fragmentLayout.setDetailPlanActivityListener( detailPlanActivityListener );
		super.onResume();
	}

	@Override
	public SlidingTabPagerAdapter getSlidingTabPagerAdapter() {

		SlidingTabPagerAdapter slidingTabPagerAdapter = new SlidingTabPagerAdapter( getFragmentManager() );
//		slidingTabPagerAdapter.setIconMode( true );

		if ( null == scheduleFragment )
			scheduleFragment = ScheduleFragment.newInstance().setPlan( plan );
		SlidingTabPagerAdapter.TabInfo scheduleTabInfo = new SlidingTabPagerAdapter.TabInfo( scheduleFragment, getString( R.string.activity_task_detail_tab_schedule ) );
		scheduleTabInfo.setIconResId( R.drawable.selector_fragment_detail_plan_tab_schedule );
		slidingTabPagerAdapter.addItem( scheduleTabInfo );

//		if ( null == transportFragment )
//			transportFragment = TransportFragment.newInstance().setPlan( plan );
//		SlidingTabPagerAdapter.TabInfo transportTabInfo = new TabInfo( transportFragment, getString( R.string.activity_task_detail_tab_transport ) );
//		transportTabInfo.setIconResId( R.drawable.selector_fragment_detail_plan_tab_transport );
//		slidingTabPagerAdapter.addItem( transportTabInfo );

		if ( null == viewSpotFragment )
			viewSpotFragment = ViewSpotFragment.newInstance().setPlan( plan );
		SlidingTabPagerAdapter.TabInfo viewSpotTabInfo = new TabInfo( viewSpotFragment, getString( R.string.activity_task_detail_tab_spot ) );
		viewSpotTabInfo.setIconResId( R.drawable.selector_fragment_detail_plan_tab_spot );
		slidingTabPagerAdapter.addItem( viewSpotTabInfo );

		if ( null == restaurantFragment )
			restaurantFragment = RestaurantFragment.newInstance().setPlan( plan );
		SlidingTabPagerAdapter.TabInfo restaurantTabInfo = new TabInfo( restaurantFragment, getString( R.string.activity_task_detail_tab_restaurant ) );
		restaurantTabInfo.setIconResId( R.drawable.selector_fragment_detail_plan_tab_restaurant );
		slidingTabPagerAdapter.addItem( restaurantTabInfo );

		if ( null == lodgmentFragment )
			lodgmentFragment = LodgmentFragment.newInstance().setPlan( plan );
		SlidingTabPagerAdapter.TabInfo lodgmentTabInfo = new TabInfo( lodgmentFragment, getString( R.string.activity_task_detail_tab_lodgment ) );
		lodgmentTabInfo.setIconResId( R.drawable.selector_fragment_detail_plan_tab_lodgment );
		slidingTabPagerAdapter.addItem( lodgmentTabInfo );

		return slidingTabPagerAdapter;
	}

	public DetailPlanFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	public DetailPlanFragment setDetailPlanActivityListener( DetailPlanActivityListener detailPlanActivityListener ) {

		this.detailPlanActivityListener = detailPlanActivityListener;
		if ( null != fragmentLayout )
			fragmentLayout.setDetailPlanActivityListener( detailPlanActivityListener );

		return this;
	}

	public static DetailPlanFragment newInstance() {

		return new DetailPlanFragment();
	}

}
