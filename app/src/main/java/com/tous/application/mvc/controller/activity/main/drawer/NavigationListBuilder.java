package com.tous.application.mvc.controller.activity.main.drawer;


import android.content.Context;

import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.mvc.model.itemdata.NavigationListItemData;
import com.tous.application.mvc.model.plan.Plan;

import java.util.ArrayList;
import java.util.List;


public class NavigationListBuilder {

	private Context context;
	private Plan currentPlan = new Plan();

	private NavigationListBuilder( Context context ) {

		this.context = context;
	}

	public List<NavigationListItemData> getNavigationList() {

		List<NavigationListItemData> navigationListItemDatas = new ArrayList<>();
		navigationListItemDatas.add( getPlanListMenu() );
		getPlanListItem( navigationListItemDatas );

		// TODO: 서버 넣으면 다시 넣기
//		navigationListItemDatas.add( getSnsServiceMenu() );
//		navigationListItemDatas.add( getExchangeRateMenu() );
//		navigationListItemDatas.add( getSIgnOutMenu() );

		return navigationListItemDatas;
	}

	private NavigationListItemData getPlanListMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( R.drawable.ic_drawer_list_black )
				.setDetailTitle( "내여행 일정 목록" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_PLAN_LIST );

		return navigationListItemData;
	}

	public void getPlanListItem( List<NavigationListItemData> navigationListItemDatas ) {

		List<Plan> planList = PlanTable.from( context ).findAllOfUser( -1 );

		if ( 0 < planList.size() ) {

			for ( Plan plan : planList ) {

				NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
						.setDetailIcon( R.drawable.ic_navigation_list_dot_black )
						.setDetailTitle( plan.getName() )
						.setPlan( plan );

				if ( null != currentPlan && currentPlan.getId() == plan.getId() )
					builder.setIsCurrentPlan( true );

				NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
				navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_PLAN_ID_PLUS_100 );

				navigationListItemDatas.add( navigationListItemData );
			}
		}
	}

	private NavigationListItemData getSnsServiceMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( R.drawable.ic_drawer_timeline_black )
				.setDetailTitle( "여행 둘러보기" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_SNS_SERVICE );

		return navigationListItemData;
	}

	private NavigationListItemData getExchangeRateMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( R.drawable.ic_drawer_exchange_black )
				.setDetailTitle( "환율정보" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_EXCHANGE_RATE );

		return navigationListItemData;
	}

	private NavigationListItemData getSIgnOutMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( R.drawable.ic_drawer_logout_black )
				.setDetailTitle( "로그아웃" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_SIGN_OUT );

		return navigationListItemData;
	}

	public NavigationListBuilder setCurrentPlan( Plan currentPlan ) {

		this.currentPlan = currentPlan;
		return this;
	}

	public static NavigationListBuilder of( Context context ) {

		return new NavigationListBuilder( context );
	}

}
