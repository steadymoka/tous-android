package com.tous.application.mvc.model.itemdata;


import com.moka.framework.widget.adapter.ItemData;
import com.tous.application.mvc.model.plan.Plan;


public class NavigationListItemData extends ItemData {

	public static final int NO_DETAIL_ID = -1;
	public static final int DETAIL_ID_NAVIGATION_PLAN_LIST = 0;
	public static final int DETAIL_ID_NAVIGATION_SNS_SERVICE = 1;
	public static final int DETAIL_ID_NAVIGATION_EXCHANGE_RATE = 2;
	public static final int DETAIL_ID_NAVIGATION_SIGN_OUT = 4;

	public static final int DETAIL_ID_NAVIGATION_PLAN_ID_PLUS_100 = 100;

	public static final int NO_ICON_ID = -1;

	private int detailId = NO_DETAIL_ID;

	private DetailBuilder detailBuilder;
	private int detailIcon;
	private CharSequence detailTitle;
	private boolean isCurrentPlan;
	private Plan plan;

	public NavigationListItemData( DetailBuilder detailBuilder ) {

		setDetailBuilder( detailBuilder );
	}

	public int getDetailIcon() {

		return detailIcon;
	}

	public CharSequence getDetailTitle() {

		return detailTitle;
	}

	public boolean isCurrentPlan() {

		return isCurrentPlan;
	}

	public boolean isPlanList() {

		return null != plan;
	}

	public Plan getPlan() {

		return plan;
	}

	public int getDetailId() {

		return detailId;
	}

	public void setDetailId( int detailId ) {

		this.detailId = detailId;
	}

	public NavigationListItemData setDetailBuilder( DetailBuilder detailBuilder ) {

		this.detailBuilder = detailBuilder;

		detailIcon = detailBuilder.getDetailIcon();
		detailTitle = detailBuilder.getDetailTitle();
		isCurrentPlan = detailBuilder.isCurrentPlan();
		plan = detailBuilder.getPlan();

		return this;
	}

	public static class DetailBuilder {

		private int detailIcon = NO_ICON_ID;
		private CharSequence detailTitle = null;
		private boolean isCurrentPlan = false;
		private Plan plan;

		public int getDetailIcon() {

			return detailIcon;
		}

		public DetailBuilder setDetailIcon( int detailIcon ) {

			this.detailIcon = detailIcon;
			return this;
		}

		public CharSequence getDetailTitle() {

			return detailTitle;
		}

		public DetailBuilder setDetailTitle( CharSequence detailTitle ) {

			this.detailTitle = detailTitle;
			return this;
		}

		public DetailBuilder setIsCurrentPlan( boolean isCurrentPlan ) {

			this.isCurrentPlan = isCurrentPlan;
			return this;
		}

		public boolean isCurrentPlan() {

			return isCurrentPlan;
		}

		public DetailBuilder setPlan( Plan plan ) {

			this.plan = plan;
			return this;
		}

		public Plan getPlan() {

			return plan;
		}

		public static DetailBuilder newInstance() {

			return new DetailBuilder();
		}
	}

}
