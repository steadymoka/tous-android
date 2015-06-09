package com.tous.application.mvc.view.plandetail;


import com.moka.framework.app.SlidingTabPagerAdapter;
import com.moka.framework.view.LayoutListener;


public interface DetailPlanFragmentLayoutListener extends LayoutListener {

	SlidingTabPagerAdapter getSlidingTabPagerAdapter();

	void onEditPlanMenuItemSelected();

}
