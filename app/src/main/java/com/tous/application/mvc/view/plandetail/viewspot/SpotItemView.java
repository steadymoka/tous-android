package com.tous.application.mvc.view.plandetail.viewspot;


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
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanFragment;
import com.tous.application.mvc.model.itemdata.SpotItemData;
import com.tous.application.mvc.model.spot.Spot;

import java.util.Objects;


public class SpotItemView extends RecyclerItemView<SpotItemData> implements View.OnClickListener {

	private LinearLayout linearLayout_spot;
	private TextView textView_spotName;
	private TextView textView_dayCount;

	private ImageView imageView_spotImage;
	private ImageView imageView_setTime;

	public SpotItemView( Context context, View itemView ) {

		super( context, itemView );
		initView();
	}

	private void initView() {

		linearLayout_spot = (LinearLayout) findViewById( R.id.linearLayout_spot );
		linearLayout_spot.setOnClickListener( this );

		textView_spotName = (TextView) findViewById( R.id.textView_spotName );
		textView_dayCount = (TextView) findViewById( R.id.textView_dayCount );

		imageView_spotImage = (ImageView) findViewById( R.id.imageView_spotImage );

		imageView_setTime = (ImageView) findViewById( R.id.imageView_setTime );
		imageView_setTime.setOnClickListener( this );
	}

	@Override
	protected void refreshView( SpotItemData data ) {

		setText( data );
		setImage( data );
	}

	private void setText( SpotItemData data ) {

		textView_spotName.setText( data.getSpot().getName() );

		SpannableStringBuilder dayCountBuilder = new SpannableStringBuilder();

		SpannableString dayCountString = new SpannableString( data.getSpot().getDayCount() + " 일차 " );
		dayCountString.setSpan( new ForegroundColorSpan( 0xFFba68c8 ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );

		dayCountBuilder.append( dayCountString );

		String content = data.getSpot().getContent();
		if ( null != content )
			dayCountBuilder.append( content );

		textView_dayCount.setText( dayCountBuilder );
	}

	private void setImage( SpotItemData data ) {

		String type = data.getSpot().getType();

		if ( Objects.equals( DetailPlanFragment.TYPE_RESTAURANT, type ) ) {

			imageView_spotImage.setImageDrawable( getContext().getResources().getDrawable( R.drawable.ic_tab_restaurant ) );
			imageView_spotImage.setBackgroundColor( 0xFFffe0b2 );
		}
		else {

			imageView_spotImage.setImageDrawable( getContext().getResources().getDrawable( R.drawable.ic_tab_spot ) );
			imageView_spotImage.setBackgroundColor( 0xFFffcdd2 );
		}

		String time = data.getSpot().getStartTime();

		if ( null != time )
			imageView_setTime.setImageDrawable( getContext().getResources().getDrawable( R.drawable.ic_set_time ) );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_spot:

				OttoUtil.getInstance().post( new OnSpotItemClickEvent( getData().getSpotId() ) );
				break;

			case R.id.imageView_setTime:

				OttoUtil.getInstance().post( new OnSpotItemSetTimeClickEvent( getData().getSpot() ) );
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

	public class OnSpotItemSetTimeClickEvent implements OttoUtil.Event {

		private Spot spot;

		public OnSpotItemSetTimeClickEvent( Spot spot ) {

			this.spot = spot;
		}

		public Spot getSpot() {

			return spot;
		}

	}

}
