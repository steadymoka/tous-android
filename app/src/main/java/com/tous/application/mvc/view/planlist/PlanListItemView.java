package com.tous.application.mvc.view.planlist;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.RecyclerItemView;
import com.tous.application.R;
import com.tous.application.mvc.model.itemdata.PlanListItemData;


public class PlanListItemView extends RecyclerItemView<PlanListItemData> implements View.OnClickListener {

	private LinearLayout linearLayout_plan;
	private TextView textView_planName;

	public PlanListItemView( Context context, View itemView ) {

		super( context, itemView );
		initView();
	}

	private void initView() {

		linearLayout_plan = (LinearLayout) findViewById( R.id.linearLayout_plan );
		linearLayout_plan.setOnClickListener( this );

		textView_planName = (TextView) findViewById( R.id.textView_planName );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_plan:

				OttoUtil.getInstance().post( new OnPlanListItemClickEvent( getData().getPlanId() ) );
				break;
		}
	}

	@Override
	protected void refreshView( PlanListItemData data ) {

		setplanName( data.getPlanName() );
	}

	public void setplanName( CharSequence planName ) {

		textView_planName.setText( planName );
	}

	public interface OnItemClickListener {

		void onItemClick( PlanListItemData planListItemData );

	}

	public class OnPlanListItemClickEvent implements OttoUtil.Event {

		private long planId;

		public OnPlanListItemClickEvent( long planId ) {

			this.planId = planId;
		}

		public long getPlanId() {

			return planId;
		}

	}

}
