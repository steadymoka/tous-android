package com.tous.application.mvc.model.itemdata;


import com.moka.framework.widget.adapter.ItemData;
import com.tous.application.mvc.model.plan.Plan;


public class PlanListItemData extends ItemData {

	private Plan plan;

	public PlanListItemData( Plan plan ) {

		this.plan = plan;
	}

	public CharSequence getPlanName() {

		return plan.getName();
	}

	public long getPlanId() {

		return plan.getId();
	}

}
