package com.tous.application.mvc.controller.activity.plandetail.schedule;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.tous.application.mvc.model.plan.Plan;


public class ScheduleAdapter extends FragmentStatePagerAdapter {

	private long planId;
	private Plan plan;
	private int dayCount = 0;

	public ScheduleAdapter( FragmentManager fragmentManager ) {

		super( fragmentManager );
	}

	@Override
	public int getCount() {

		return dayCount;
	}

	@Override
	public Fragment getItem( int position ) {

		return ScheduleItemItemFragment.newInstance()
				.setPlanId( planId )
				.setDayCount( position );
	}

	@Override
	public Object instantiateItem( ViewGroup container, int position ) {

		return super.instantiateItem( container, position );
	}

	@Override
	public void destroyItem( ViewGroup container, int position, Object object ) {

		super.destroyItem( container, position, object );
	}

	public void setPlanId( long planId ) {

		this.planId = planId;
	}

	public void setPlan( Plan plan ) {

		this.plan = plan;
		dayCount = plan.getPlanDayCount();
		notifyDataSetChanged();
	}

}
