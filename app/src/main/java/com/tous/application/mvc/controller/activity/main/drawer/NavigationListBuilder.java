package com.tous.application.mvc.controller.activity.main.drawer;


import com.tous.application.mvc.model.itemdata.NavigationListItemData;

import java.util.ArrayList;
import java.util.List;


public class NavigationListBuilder {

	private NavigationListBuilder() {

	}

	public List<NavigationListItemData> getNavigationList() {

		List<NavigationListItemData> navigationListItemDatas = new ArrayList<>();
		navigationListItemDatas.add( getPlanListMenu() );
		navigationListItemDatas.add( getSnsServiceMenu() );
		navigationListItemDatas.add( getExchangeRateMenu() );
		navigationListItemDatas.add( getSIgnOutMenu() );

		return navigationListItemDatas;
	}

	private NavigationListItemData getPlanListMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( NavigationListItemData.NO_ICON_ID )
				.setDetailTitle( "내여행 일정 목록" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_PLAN_LIST );

		return navigationListItemData;
	}

	private NavigationListItemData getSnsServiceMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( NavigationListItemData.NO_ICON_ID )
				.setDetailTitle( "여행 둘러보기" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_SNS_SERVICE );

		return navigationListItemData;
	}

	private NavigationListItemData getExchangeRateMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( NavigationListItemData.NO_ICON_ID )
				.setDetailTitle( "환율정보" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_EXCHANGE_RATE );

		return navigationListItemData;
	}

	private NavigationListItemData getSIgnOutMenu() {

		NavigationListItemData.DetailBuilder builder = NavigationListItemData.DetailBuilder.newInstance()
				.setDetailIcon( NavigationListItemData.NO_ICON_ID )
				.setDetailTitle( "로그아웃" );
		NavigationListItemData navigationListItemData = new NavigationListItemData( builder );
		navigationListItemData.setDetailId( NavigationListItemData.DETAIL_ID_NAVIGATION_SIGN_OUT );

		return navigationListItemData;
	}

	public static NavigationListBuilder of() {

		return new NavigationListBuilder();
	}

}
