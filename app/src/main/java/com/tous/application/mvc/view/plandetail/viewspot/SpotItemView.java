package com.tous.application.mvc.view.plandetail.viewspot;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.OttoUtil;
import com.moka.framework.util.ScreenUtil;
import com.moka.framework.widget.adapter.RecyclerItemView;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.database.table.image.ImageTable;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanFragment;
import com.tous.application.mvc.model.image.Image;
import com.tous.application.mvc.model.itemdata.SpotItemData;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.util.ImageFileUtil;

import java.io.File;
import java.util.Objects;


public class SpotItemView extends RecyclerItemView<SpotItemData> implements View.OnClickListener, View.OnLongClickListener {

	private LinearLayout linearLayout_spot;
	private TextView textView_spotName;
	private TextView textView_content;

	private ImageView imageView_spotImage;
	private ImageView imageView_setTime;

	public SpotItemView( Context context, View itemView ) {

		super( context, itemView );
		initView();
	}

	private void initView() {

		linearLayout_spot = (LinearLayout) findViewById( R.id.linearLayout_spot );
		linearLayout_spot.setOnClickListener( this );
		linearLayout_spot.setOnLongClickListener( this );

		textView_spotName = (TextView) findViewById( R.id.textView_spotName );
		textView_spotName.setSelected( true );

		textView_content = (TextView) findViewById( R.id.textView_dayCount );

		imageView_spotImage = (ImageView) findViewById( R.id.imageView_spotImage );

		imageView_setTime = (ImageView) findViewById( R.id.imageView_setTime );
		imageView_setTime.setOnClickListener( this );
	}

	@Override
	protected void refreshView( SpotItemData data ) {

		setSpotNameAndDayCountText( data );
		setContentText( data );
		setImage( data );
	}

	private void setSpotNameAndDayCountText( SpotItemData data ) {

		SpannableStringBuilder spotNameBuilder = new SpannableStringBuilder( data.getSpot().getName() + " " );

		int dayCount = data.getSpot().getDayCount();

		if ( 0 < dayCount ) {

			SpannableString dayCountString = new SpannableString( dayCount + " 일차 " );
			dayCountString.setSpan( new ForegroundColorSpan( 0xFFba68c8 ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );
			dayCountString.setSpan( new RelativeSizeSpan( 0.618f ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );

			spotNameBuilder.append( dayCountString );
		}
		textView_spotName.setText( spotNameBuilder );
	}

	private void setContentText( SpotItemData data ) {

		String content = data.getSpot().getContent();
		if ( null != content )
			textView_content.setText( content );
	}

	private void setImage( SpotItemData data ) {

		Spot spot = data.getSpot();
		Image image = ImageTable.from( getContext() ).findImageFrom( "spot", spot.getId() );
		if ( null != image ) {

			String imagePath = ImageFileUtil.from( getContext() ).getFilePath( image.getImageName() );

			Picasso.with( getContext() )
					.load( new File( imagePath ) )
					.centerCrop()
					.resize( 500, 325 )
					.into( imageView_spotImage );
		}
		else {

			String type = spot.getType();

			if ( type.equals( DetailPlanFragment.TYPE_RESTAURANT ) ) {

				imageView_spotImage.setImageDrawable( getContext().getResources().getDrawable( R.drawable.ic_tab_restaurant ) );
				imageView_spotImage.setBackgroundColor( 0xFFffcc80 );
			}
			else if ( type.equals( DetailPlanFragment.TYPE_VIEW_SPOT ) ) {

				imageView_spotImage.setImageDrawable( getContext().getResources().getDrawable( R.drawable.ic_tab_spot ) );
				imageView_spotImage.setBackgroundColor( 0xFFb2dfdb );
			}
			else {

				imageView_spotImage.setImageDrawable( getContext().getResources().getDrawable( R.drawable.ic_tab_lodgment ) );
				imageView_spotImage.setBackgroundColor( 0xFFb39ddb );
			}
		}

		float time = data.getSpot().getStartTime();

		if ( 0 != time )
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

	@Override
	public boolean onLongClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_spot:

				OttoUtil.getInstance().post( new OnSpotItemLongClickEvent( getData().getSpotId() ) );
				break;
		}

		return true;
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

	public class OnSpotItemLongClickEvent implements OttoUtil.Event {

		private long spotId;

		public OnSpotItemLongClickEvent( long spotId ) {

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
