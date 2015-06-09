package com.tous.application.mvc.view.main.shedule;


import android.content.Context;
import android.view.View;

import com.moka.framework.widget.adapter.RecyclerItemView;
import com.tous.application.mvc.model.itemdata.ScheduleItemData;


public class ScheduleItemView extends RecyclerItemView<ScheduleItemData> {

	public ScheduleItemView( Context context, View itemView ) {

		super( context, itemView );
	}

	@Override
	protected void refreshView( ScheduleItemData data ) {

	}

}
