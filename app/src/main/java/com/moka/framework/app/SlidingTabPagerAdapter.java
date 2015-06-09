package com.moka.framework.app;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.tous.application.widget.PagerSlidingTabStrip;

import java.util.ArrayList;


public class SlidingTabPagerAdapter extends FragmentViewPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

	private ArrayList<TabInfo> items = new ArrayList<>();
	private boolean isIconMode = false;

	public SlidingTabPagerAdapter( FragmentManager fragmentManager ) {

		super( fragmentManager );
	}

	@Override
	public int getCount() {

		return items.size();
	}

	@Override
	public Fragment getItem( int position ) {

		return items.get( position ).getFragment();
	}

	@Override
	public CharSequence getPageTitle( int position ) {

		return ( items.get( position ) ).getTitle();
	}

	@Override
	public boolean isIconMode() {

		return isIconMode;
	}

	@Override
	public int getPageIconResId( int position ) {

		return items.get( position ).getIconResId();
	}

	public void setIconMode( boolean isIconMode ) {

		this.isIconMode = isIconMode;
	}

	public void addItem( TabInfo tabInfo ) {

		items.add( tabInfo );
		notifyDataSetChanged();
	}

	public boolean removeItem( TabInfo tabInfo ) {

		if ( items.remove( tabInfo ) ) {

			notifyDataSetChanged();
			return true;
		}
		else {

			return false;
		}
	}

	public static class TabInfo {

		private Fragment fragment;
		private String title;
		private int iconResId;

		public TabInfo( Fragment fragment, String title ) {

			this.fragment = fragment;
			this.title = title;
			this.iconResId = 0;
		}

		public Fragment getFragment() {

			return fragment;
		}

		public String getTitle() {

			return title;
		}

		public int getIconResId() {

			return iconResId;
		}

		public void setIconResId( int iconResId ) {

			this.iconResId = iconResId;
		}

	}

}
