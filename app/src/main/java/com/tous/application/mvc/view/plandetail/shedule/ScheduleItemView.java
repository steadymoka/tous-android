package com.tous.application.mvc.view.plandetail.shedule;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.RecyclerItemView;
import com.tous.application.R;
import com.tous.application.mvc.model.itemdata.ScheduleItemData;
import com.tous.application.mvc.model.spot.Spot;


public class ScheduleItemView extends RecyclerItemView<ScheduleItemData> implements View.OnClickListener {

	private LinearLayout linearLayout_spot;

	private TextView textView_time;
	private ImageView imageView_dot;
	private TextView textView_spotName;
	private TextView textView_spotContent;

	public ScheduleItemView( Context context, View itemView ) {

		super( context, itemView );
		initView();
	}

	private void initView() {

		linearLayout_spot = (LinearLayout) findViewById( R.id.linearLayout_spot );
		linearLayout_spot.setOnClickListener( this );

		textView_time = (TextView) findViewById( R.id.textView_time );

		imageView_dot = (ImageView) findViewById( R.id.imageView_dot );

		textView_spotName = (TextView) findViewById( R.id.textView_spotName );
		textView_spotName.setSelected( true );

		textView_spotContent = (TextView) findViewById( R.id.textView_spotContent );
	}

	@Override
	protected void refreshView( ScheduleItemData data ) {

		setTime( String.valueOf( (int) data.getSpot().getStartTime() ) );
		setSpotNameFromType( data );
		setDotImage( data );
		setContent( data );
	}

	public void setTime( String timeOfHourString ) {

		textView_time.setText( timeOfHourString + ":00" );
	}

	private void setSpotNameFromType( ScheduleItemData data ) {

		Spot spot = data.getSpot();

		SpannableStringBuilder spotNameBuilder = new SpannableStringBuilder();

		if ( spot.getType().equals( "맛집" ) ) {

			SpannableString spotName = new SpannableString( spot.getName() );
			spotName.setSpan( new ForegroundColorSpan( 0xFFffcc80 ), 0, spotName.length(), Spannable.SPAN_COMPOSING );

			spotNameBuilder.append( spotName );
		}
		else if ( spot.getType().equals( "명소" ) ) {

			SpannableString spotName = new SpannableString( spot.getName() );
			spotName.setSpan( new ForegroundColorSpan( 0xFF80cbc4 ), 0, spotName.length(), Spannable.SPAN_COMPOSING );

			spotNameBuilder.append( spotName );
		}
		else {

			SpannableString spotName = new SpannableString( spot.getName() );
			spotName.setSpan( new ForegroundColorSpan( 0xFF9575cd ), 0, spotName.length(), Spannable.SPAN_COMPOSING );

			spotNameBuilder.append( spotName );
		}

		textView_spotName.setText( spotNameBuilder );
	}

	public void setDotImage( ScheduleItemData data ) {

		Spot spot = data.getSpot();

		if ( spot.getType().equals( "맛집" ) )
			imageView_dot.setImageResource( R.drawable.ic_dot_restaurant );
		else if ( spot.getType().equals( "명소" ) )
			imageView_dot.setImageResource( R.drawable.ic_dot_spot );
		else
			imageView_dot.setImageResource( R.drawable.ic_dot_lodgment );
	}

	private void setContent( ScheduleItemData data ) {

		Spot spot = data.getSpot();
		textView_spotContent.setText( spot.getContent() );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_spot:

				OttoUtil.getInstance().post( new OnSpotItemClickEvent( getData().getSpot().getId() ) );
				break;
		}
	}

	public class OnSpotItemClickEvent implements OttoUtil.Event {

		private long spotId;

		public OnSpotItemClickEvent( long spotId ) {

			this.spotId = spotId;
		}

		public long getSpotId() {

			return spotId;
		}

	}

}
