package com.moka.framework.widget.calendar.util;


import android.support.v4.view.ViewPager;


public abstract class OnPageChangedListenerAdapter implements ViewPager.OnPageChangeListener {

	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

	}

	@Override
	public void onPageSelected( int position ) {

		onPageChanged( position );
	}

	@Override
	public void onPageScrollStateChanged( int state ) {

	}

	protected abstract void onPageChanged( int position );

}
