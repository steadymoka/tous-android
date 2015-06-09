package com.tous.application.mvc.view.main.drawer;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moka.framework.view.FragmentLayout;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.main.drawer.NavigationDrawerFragment;
import com.tous.application.mvc.model.itemdata.NavigationListItemData;


public class NavigationDrawerFragmentLayout extends FragmentLayout<NavigationDrawerFragment, NavigationFragmentLayoutListener> implements NavigationListItemView.OnItemClickListener, NavigationListHeaderView.HeaderViewListener {

	private NavigationListHeaderView navigationListHeaderView;
	private ObservableRecyclerView recyclerView;

	public NavigationDrawerFragmentLayout( NavigationDrawerFragment fragment, NavigationFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_navigation_drawer;
	}

	@Override
	protected void onLayoutInflated() {

		navigationListHeaderView = new NavigationListHeaderView( getActivity() );
		navigationListHeaderView.setHeaderViewListener( this );

		recyclerView = (ObservableRecyclerView) findViewById( R.id.recyclerView );
		recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
		recyclerView.setAdapter( getLayoutListener().getRecyclerAdapter( this, navigationListHeaderView ) );
	}

	@Override
	public void onClickToProfile() {


	}

	@Override
	public void onItemClick( NavigationListItemData settingListItemData ) {

		switch ( settingListItemData.getDetailId() ) {

			case NavigationListItemData.DETAIL_ID_NAVIGATION_PLAN_LIST:

				getLayoutListener().onClickPlanList();
				break;

			case NavigationListItemData.DETAIL_ID_NAVIGATION_SNS_SERVICE:

				getLayoutListener().onClickSnsService();
				break;

			case NavigationListItemData.DETAIL_ID_NAVIGATION_EXCHANGE_RATE:

				getLayoutListener().onClickExchangeRate();
				break;

			case NavigationListItemData.DETAIL_ID_NAVIGATION_SIGN_OUT:

				getLayoutListener().onClickSignOut();
				break;
		}
	}

	public void setProfileImage( String imagePath ) {

		navigationListHeaderView.setProfileImage( imagePath, getActivity() );
	}

	public void setProfileNameAndEmail( String userName, String userEmail ) {

		navigationListHeaderView.setProfile( userName, userEmail );
	}

}
