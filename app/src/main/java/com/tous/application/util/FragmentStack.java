package com.tous.application.util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tous.application.R;


public class FragmentStack {

	private FragmentManager fragmentManager;
	private int containerViewId;
	private Fragment activeBottomFragment;

	private FragmentStack( FragmentManager fragmentManager, int containerViewId, Fragment bottomFragment ) {

		this.fragmentManager = fragmentManager;
		this.containerViewId = containerViewId;

		if ( null != bottomFragment )
			replaceBottomWith( bottomFragment );
	}

	public void add( Fragment fragment ) {

		fragmentManager.beginTransaction()
				.setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right )
				.add( containerViewId, fragment )
				.addToBackStack( fragment.toString() )
				.commit();
	}

	public boolean pop() {

		if ( 0 < fragmentManager.getBackStackEntryCount() ) {

			fragmentManager.popBackStack();
			return true;
		}

		return false;
	}

	public boolean popAll() {

		int size = fragmentManager.getBackStackEntryCount();
		if ( 0 < size ) {

			for ( int i = 0; i < size; i++ )
				fragmentManager.popBackStack();

			return true;
		}

		return false;
	}

	public boolean replaceBottomWith( Fragment fragment ) {

		popAll();

		if ( activeBottomFragment != fragment ) {

			fragmentManager.beginTransaction()
					.setCustomAnimations( R.anim.fade_in, R.anim.fade_out )
					.replace( containerViewId, fragment )
					.commit();
			activeBottomFragment = fragment;

			return true;
		}

		return false;
	}

	public int backStackEntryCount() {

		return fragmentManager.getBackStackEntryCount();
	}

	public static FragmentStack newInstance( FragmentManager fragmentManager, int containerViewId ) {

		return newInstance( fragmentManager, containerViewId, null );
	}

	public static FragmentStack newInstance( FragmentManager fragmentManager, int containerViewId, Fragment bottomFragment ) {

		return new FragmentStack( fragmentManager, containerViewId, bottomFragment );
	}

}
