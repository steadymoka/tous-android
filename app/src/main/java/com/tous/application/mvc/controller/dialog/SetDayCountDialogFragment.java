package com.tous.application.mvc.controller.dialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;

import com.moka.framework.controller.BaseDialogFragment;
import com.tous.application.R;


public class SetDayCountDialogFragment extends BaseDialogFragment implements OnClickListener {

	private View rootView;
	private NumberPicker numberPicker_dayCount;
	private Button button_save;

	private OnDoneAmountInputtedListener onDaySavedListener;

	private int initialAmount = 0;

	private int minValue = 0;
	private int maxValue = 0;
	private boolean isCanceled = true;

	public static SetDayCountDialogFragment newInstance() {

		return new SetDayCountDialogFragment();
	}


	public static void showDialog( FragmentManager fragmentManager, OnDoneAmountInputtedListener onDaySavedListener ) {

		SetDayCountDialogFragment dialogFragment = new SetDayCountDialogFragment();

		dialogFragment.setOnDaySavedListener( onDaySavedListener );
		dialogFragment.show( fragmentManager, "DoneAmountInputDialogFragment" );
	}

	private void setOnDaySavedListener( OnDoneAmountInputtedListener onDaySavedListener ) {

		this.onDaySavedListener = onDaySavedListener;
	}

	@SuppressLint( "InflateParams" )
	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {

		rootView = getActivity().getLayoutInflater().inflate( R.layout.dialog_daily_studying_amount_input, null );

		numberPicker_dayCount = (NumberPicker) rootView.findViewById( R.id.numberPicker_dayCount );
		numberPicker_dayCount.setMinValue( 1 );
		numberPicker_dayCount.setMaxValue( maxValue );

		button_save = (Button) rootView.findViewById( R.id.button_save );

		button_save.setOnClickListener( this );

		Builder builder = new Builder( getActivity() );
		builder.setView( rootView );

		return builder.create();
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.button_save:

				Activity activity = getActivity();
				if ( null != activity ) {

					InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
					inputMethodManager.hideSoftInputFromWindow( numberPicker_dayCount.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
				}

				onDaySavedListener.onDoneAmountInputted( initialAmount );

				isCanceled = false;

				dismiss();
				break;
		}
	}

	private int getDayCount() {

		return numberPicker_dayCount.getValue();
	}

	@Override
	public void onDismiss( DialogInterface dialog ) {

		if ( null != onDaySavedListener && isCanceled )
			onDaySavedListener.onInputCanceled();

		super.onDismiss( dialog );
	}

	public SetDayCountDialogFragment setOnDismissDayCountDialogListener( OnDismissDialogListener onDismissDialogListener ) {

		setOnDismissDialogListener( onDismissDialogListener );
		return this;
	}

	public SetDayCountDialogFragment setDayCount( int dayCount ) {

		maxValue = dayCount;
		return this;
	}

	public interface OnDoneAmountInputtedListener {

		public void onDoneAmountInputted( int previousAmount );

		public void onInputCanceled();

	}

}
