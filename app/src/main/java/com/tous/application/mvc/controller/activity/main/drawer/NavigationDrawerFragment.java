package com.tous.application.mvc.controller.activity.main.drawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.squareup.otto.Subscribe;
import com.tous.application.event.OnClickDrawerPlanItem;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.account.SignInActivity;
import com.tous.application.mvc.controller.activity.planlist.PlanListActivity;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.drawer.NavigationDrawerFragmentLayout;
import com.tous.application.mvc.view.main.drawer.NavigationFragmentLayoutListener;
import com.tous.application.mvc.view.main.drawer.NavigationListItemView;


public class NavigationDrawerFragment extends BaseFragment implements NavigationFragmentLayoutListener {

	private NavigationDrawerFragmentLayout fragmentLayout;
	private DrawerLayout drawerLayout;

	private NavigationAdapter navigationRecyclerAdapter;
	private Plan plan;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new NavigationDrawerFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );

		initProfileData();
		initNavigatinList();

		return fragmentLayout.getRootView();
	}

	private void initProfileData() {

		fragmentLayout.setProfileImage( null );
		fragmentLayout.setProfileNameAndEmail( "Tous", "tous와 함께 즐거운 여행 계획!" );
	}

	private void initNavigatinList() {

		navigationRecyclerAdapter.setItems( NavigationListBuilder.of( getActivity() )
				.setCurrentPlan( plan )
				.getNavigationList() );
	}

	@Override
	public RecyclerView.Adapter getRecyclerAdapter( NavigationListItemView.OnItemClickListener onItemClickListener, View headerView ) {

		if ( null == navigationRecyclerAdapter ) {

			navigationRecyclerAdapter = new NavigationAdapter( getActivity() );
			navigationRecyclerAdapter.setOnItemClickListener( onItemClickListener );
			navigationRecyclerAdapter.setHeaderView( headerView );
		}

		return navigationRecyclerAdapter;
	}

	/**
	 * Click Listener
	 */

	@Override
	public void onClickPlanList() {

		getActivity().startActivity( new Intent( getActivity(), PlanListActivity.class ) );
		drawerLayout.closeDrawer( Gravity.LEFT );
	}

	@Override
	public void onCLickPlanItem( Plan plan ) {

		this.plan = plan;
		drawerLayout.closeDrawer( Gravity.LEFT );
		OttoUtil.getInstance().postInMainThread( new OnClickDrawerPlanItem( plan ) );
		initNavigatinList();
	}

	@Override
	public void onClickSnsService() {

		fragmentLayout.showToast( "준비중입니다" );
	}

	@Override
	public void onClickExchangeRate() {

		fragmentLayout.showToast( "준비중입니다" );
	}

	@Override
	public void onClickSignOut() {

		startActivity( new Intent( getActivity(), SignInActivity.class ) );
		getActivity().finish();
	}

	@Subscribe
	public void onRefreshViewEvent( OnRefreshViewEvent onRefreshViewEvent ) {

		initNavigatinList();
	}

	@Override
	public void onDestroyView() {

		OttoUtil.getInstance().unregister( this );
		super.onDestroyView();
	}

	public void setDrawerLayout( DrawerLayout drawerLayout ) {

		this.drawerLayout = drawerLayout;
	}

	public void setPlan( Plan plan ) {

		this.plan = plan;
		initNavigatinList();
	}

}
