package com.tous.application.mvc.controller.activity.planlist;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.ManjongRecyclerAdapter;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.main.MainActivity;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationActivity;
import com.tous.application.mvc.model.itemdata.PlanListItemData;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.planlist.PlanListFragmentLayout;
import com.tous.application.mvc.view.planlist.PlanListFragmentLayoutListener;
import com.tous.application.mvc.view.planlist.PlanListItemView;

import java.util.ArrayList;
import java.util.List;


public class PlanListFragment extends BaseFragment implements PlanListFragmentLayoutListener {

	private PlanListFragmentLayout fragmentLayout;
	private ManjongRecyclerAdapter<PlanListItemData, PlanListItemView> recyclerAdapter;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new PlanListFragmentLayout( this, this, inflater, container );
		OttoUtil.getInstance().register( this );

		refreshView();
		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		recyclerAdapter.setItems( makeItems() );
	}

	private List<PlanListItemData> makeItems() {

		List<PlanListItemData> planListItemDatas = new ArrayList<>();
		for ( Plan plan : getDataFromDB() )
			planListItemDatas.add( new PlanListItemData( plan ) );

		return planListItemDatas;
	}

	private List<Plan> getDataFromDB() {

		return PlanTable.from( getActivity() ).findAll();
	}

	@Override
	public RecyclerView.Adapter getRecyclerViewAdapter() {

		if ( null == recyclerAdapter )
			recyclerAdapter = new ManjongRecyclerAdapter<>( getActivity(), PlanListItemView.class, R.layout.view_plan_list_item );

		return recyclerAdapter;
	}

	@Subscribe
	public void onRefreshViewEvent( OnRefreshViewEvent onRefreshViewEvent ) {

		refreshView();
	}

	/**
	 * onClickListener
	 */

	@Override
	public void onClickToAddPlan() {

		startActivity( new Intent( getActivity(), PlanCreationActivity.class ) );
	}

	@Subscribe
	public void onPlanListItemClick( PlanListItemView.OnPlanListItemClickEvent onPlanListItemClickEvent ) {

		// TODO : 이미 떠있는 activity 를 새로 고침 할 방법 ?? activity 새로 띄우는 수밖에 없는건가.. fragment 에 오토이벤트 먹여서 refreshView 해버려 ???
		try {

			Intent resultIntent = new Intent( getActivity(), MainActivity.class );
			resultIntent.putExtra( MainActivity.KEY_PLAN_ID, onPlanListItemClickEvent.getPlanId() );
			TaskStackBuilder stackBuilder = TaskStackBuilder.create( getActivity() );
			stackBuilder.addParentStack( MainActivity.class );
			stackBuilder.addNextIntent( resultIntent );
			stackBuilder.getPendingIntent( 3, PendingIntent.FLAG_CANCEL_CURRENT ).send();
		}
		catch ( PendingIntent.CanceledException e ) {

			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {

		OttoUtil.getInstance().unregister( this );
		super.onDestroyView();
	}

	public static PlanListFragment newInstance() {

		return new PlanListFragment();
	}

}
