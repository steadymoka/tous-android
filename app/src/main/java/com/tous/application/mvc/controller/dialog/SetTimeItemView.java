package com.tous.application.mvc.controller.dialog;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.widget.adapter.RecyclerItemView;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.event.OnClickTimeEvent;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanFragment;
import com.tous.application.mvc.model.spot.Spot;

import java.util.List;


public class SetTimeItemView extends RecyclerItemView<SetTimeItemData> implements View.OnClickListener {

	private LinearLayout linearLayout_setTime_container;
	private TextView textView_setTime_time;
	private TextView textView_setTime_spot_name;

	private View horizontal_set_time_item_divider;
	private View horizontal_set_time_item;

	private OnItemClickListener onItemClickListener;

	private boolean isSelected = false;
	private boolean isAlreadySelected = false;

	public SetTimeItemView( Context context, View itemView ) {

		super( context, itemView );

		bindView();
	}

	private void bindView() {

		linearLayout_setTime_container = (LinearLayout) findViewById( R.id.linearLayout_setTime_container );
		linearLayout_setTime_container.setOnClickListener( this );

		textView_setTime_time = (TextView) findViewById( R.id.textView_setTime_time );
		textView_setTime_spot_name = (TextView) findViewById( R.id.textView_setTime_spot_name );
		textView_setTime_spot_name.setSelected( true );

		horizontal_set_time_item_divider = findViewById( R.id.horizontal_set_time_item_divider );
		horizontal_set_time_item = findViewById( R.id.horizontal_set_time_item );
	}

	@Override
	protected void refreshView( SetTimeItemData data ) {

		textView_setTime_time.setText( data.getTimeOfHour() + ":00" );
		horizontal_set_time_item_divider.setVisibility( View.GONE );

		Spot currentSpot = data.getCurrentSpot();
		Spot selectedSpot = null;
		List<Spot> spotList = data.getSpotList();

		for ( Spot spot : spotList ) {

			if ( null != spot && 0 != spot.getStartTime() && 0 != spot.getEndTime() ) {

				int startTimeOfHour = (int) spot.getStartTime();
				int endTimeOfHour = (int) spot.getEndTime();
				int timeOfHour = data.getTimeOfHour();

				if ( timeOfHour >= startTimeOfHour && timeOfHour <= endTimeOfHour )
					selectedSpot = spot;
			}
		}

		if ( null != selectedSpot ) {

			int startTimeOfHour = (int) selectedSpot.getStartTime();
			int endTimeOfHour = (int) selectedSpot.getEndTime();
			int timeOfHour = data.getTimeOfHour();

			if ( timeOfHour == startTimeOfHour ) {

				textView_setTime_spot_name.setText( selectedSpot.getName() );
				horizontal_set_time_item_divider.setVisibility( View.VISIBLE );
			}
			else
				textView_setTime_spot_name.setText( "" );

			if ( timeOfHour >= startTimeOfHour && timeOfHour <= endTimeOfHour ) {

				if ( selectedSpot.getType().equals( DetailPlanFragment.TYPE_VIEW_SPOT ) )
					linearLayout_setTime_container.setBackgroundColor( 0xFFe6ee9c );
				else if ( selectedSpot.getType().equals( DetailPlanFragment.TYPE_RESTAURANT ) )
					linearLayout_setTime_container.setBackgroundColor( 0xFFa5d6a7 );
				else
					linearLayout_setTime_container.setBackgroundColor( 0xFF9575cd );

				horizontal_set_time_item.setVisibility( View.GONE );
				isAlreadySelected = true;
			}
			else {

				linearLayout_setTime_container.setBackgroundColor( 0xFFFFFFFF );
				horizontal_set_time_item.setVisibility( View.VISIBLE );
				isAlreadySelected = false;

				if ( isSelected )
					linearLayout_setTime_container.setBackgroundColor( 0xFFffccbc );
				else
					linearLayout_setTime_container.setBackgroundColor( 0xFFFFFFFF );
			}
		}
		else {

			linearLayout_setTime_container.setBackgroundColor( 0xFFFFFFFF );
			textView_setTime_spot_name.setText( "" );
			horizontal_set_time_item.setVisibility( View.VISIBLE );
			isAlreadySelected = false;

			if ( isSelected )
				linearLayout_setTime_container.setBackgroundColor( 0xFFffccbc );
			else
				linearLayout_setTime_container.setBackgroundColor( 0xFFFFFFFF );
		}
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_setTime_container:

				if ( !isAlreadySelected ) {

					onClickTime();
					onItemClickListener.onItemClick( getData() );
				}
				break;
		}
	}

	private void onClickTime() {

		if ( isSelected ) {

			linearLayout_setTime_container.setBackgroundColor( 0xFFFFFFFF );
			isSelected = false;
		}
		else {

			linearLayout_setTime_container.setBackgroundColor( 0xFFffccbc );
			isSelected = true;
		}
	}

	@Subscribe
	public void onClickTimeEvent( OnClickTimeEvent onClickTimeEvent ) {


	}

	public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {

		this.onItemClickListener = onItemClickListener;
	}

	public interface OnItemClickListener {

		void onItemClick( SetTimeItemData setTimeItemData );

	}

}
