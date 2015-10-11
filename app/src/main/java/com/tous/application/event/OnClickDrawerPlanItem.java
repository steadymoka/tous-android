package com.tous.application.event;


import com.moka.framework.util.OttoUtil;
import com.tous.application.mvc.model.plan.Plan;


public class OnClickDrawerPlanItem implements OttoUtil.Event {

	private Plan plan;

	public OnClickDrawerPlanItem( Plan plan ) {

		this.plan = plan;
	}

	public Plan getPlan() {

		return plan;

	}
}
