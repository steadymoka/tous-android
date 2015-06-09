package com.tous.application.mvc.model.itemdata;


import com.moka.framework.widget.adapter.ItemData;


public class NavigationListItemData extends ItemData {

	public static final int NO_DETAIL_ID = -1;
	public static final int DETAIL_ID_NAVIGATION_PLAN_LIST = 0;
	public static final int DETAIL_ID_NAVIGATION_SNS_SERVICE = 1;
	public static final int DETAIL_ID_NAVIGATION_EXCHANGE_RATE = 2;
	public static final int DETAIL_ID_NAVIGATION_SIGN_OUT = 4;

	public static final int NO_ICON_ID = -1;

	private int detailId = NO_DETAIL_ID;

	private DetailBuilder detailBuilder;
	private int detailIcon;
	private CharSequence detailTitle;

	public NavigationListItemData( DetailBuilder detailBuilder ) {

		setDetailBuilder( detailBuilder );
	}

	public int getDetailIcon() {

		return detailIcon;
	}

	public CharSequence getDetailTitle() {

		return detailTitle;
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

		return this;
	}

	public static class DetailBuilder {

		private int detailIcon = NO_ICON_ID;
		private CharSequence detailTitle = null;

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

		public static DetailBuilder newInstance() {

			return new DetailBuilder();
		}
	}

}
