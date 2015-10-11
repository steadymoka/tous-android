package com.tous.application.mvc.controller.dialog;


import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.moka.framework.controller.BaseDialogFragment;
import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.scrollobservableview.ObservableRecyclerView;
import com.tous.application.R;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.model.spot.Spot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SpotItemDeleteDialogFragment extends BaseDialogFragment implements OnClickListener {

	private View rootView;

	private TextView textView_cancle;
	private TextView textView_delete;
	private long spotId = -1;
	private long planId = -1;

	private OnDeletSpotListener onDeletSpotListener;

	@NonNull
	@SuppressLint( "InflateParams" )
	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {

		rootView = getActivity().getLayoutInflater().inflate( R.layout.dialog_delete_spot_item, null );

		initView();

		Builder builder = new Builder( getActivity() );
		builder.setView( rootView );

		return builder.create();
	}

	private void initView() {

		textView_cancle = (TextView) rootView.findViewById( R.id.textView_cancle );
		textView_cancle.setOnClickListener( this );

		textView_delete = (TextView) rootView.findViewById( R.id.textView_delete );
		textView_delete.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.textView_cancle:

				dismiss();
				break;

			case R.id.textView_delete:

				if ( -1 != spotId && -1 == planId )
					SpotTable.from( getActivity() ).delete( SpotTable.from( getActivity() ).find( spotId ) );
				else if ( -1 == spotId && -1 != planId )
					PlanTable.from( getActivity() ).delete( PlanTable.from( getActivity() ).find( planId ) );

				OttoUtil.getInstance().postInMainThread( new OnRefreshViewEvent() );
				dismiss();
				break;
		}
	}

	@Override
	public void onDismiss( DialogInterface dialog ) {

		super.onDismiss( dialog );
	}

	public SpotItemDeleteDialogFragment setOnDismissDayCountDialogListener( OnDismissDialogListener onDismissDialogListener ) {

		setOnDismissDialogListener( onDismissDialogListener );
		return this;
	}

	public SpotItemDeleteDialogFragment setOnDeletSpotListener( OnDeletSpotListener onDeletSpotListener ) {

		this.onDeletSpotListener = onDeletSpotListener;
		return this;
	}

	public SpotItemDeleteDialogFragment setSpotId( long spotId ) {

		this.spotId = spotId;
		return this;
	}

	public SpotItemDeleteDialogFragment setPlan( long planId ) {

		this.planId = planId;
		return this;
	}

	public void showDialog( FragmentManager fragmentManager, OnDeletSpotListener onDeletSpotListener ) {

		setOnDeletSpotListener( onDeletSpotListener );
		show( fragmentManager, "setOnDeletSpotListener" );
	}

	public static SpotItemDeleteDialogFragment newInstance() {

		return new SpotItemDeleteDialogFragment();
	}

	public interface OnDeletSpotListener {

		void onDelete();

		void onCancle();

	}

}
