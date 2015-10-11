package com.tous.application.mvc.view.planlist;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moka.framework.util.OttoUtil;
import com.moka.framework.util.ScreenUtil;
import com.moka.framework.widget.adapter.RecyclerItemView;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.database.table.image.ImageTable;
import com.tous.application.mvc.model.image.Image;
import com.tous.application.mvc.model.itemdata.PlanListItemData;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.util.DateUtil;
import com.tous.application.util.ImageFileUtil;

import java.io.File;
import java.util.Random;


public class PlanListItemView extends RecyclerItemView<PlanListItemData> implements View.OnClickListener, View.OnLongClickListener {

	private RelativeLayout linearLayout_plan;
	private ImageView imageView_plan_background;
	private TextView textView_planName;
	private TextView textView_period;

	public PlanListItemView( Context context, View itemView ) {

		super( context, itemView );
		initView();
	}

	private void initView() {

		linearLayout_plan = (RelativeLayout) findViewById( R.id.linearLayout_plan );
		linearLayout_plan.setOnClickListener( this );
		linearLayout_plan.setOnLongClickListener( this );

		imageView_plan_background = (ImageView) findViewById( R.id.imageView_plan_background );

		textView_planName = (TextView) findViewById( R.id.textView_planName );
		textView_period = (TextView) findViewById( R.id.textView_period );
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
	public boolean onLongClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_plan:

				OttoUtil.getInstance().post( new OnPlanListItemLongClickEvent( getData().getPlanId() ) );
				break;
		}

		return true;
	}

	@Override
	protected void refreshView( PlanListItemData data ) {

		setImage( data );
		setplanName( data.getPlanName() );
		setPeriod( data.getPlan() );
	}

	private void setImage( PlanListItemData data ) {

		String imagePath = null;

		Image image = ImageTable.from( getContext() ).findImageFrom( "plan", data.getPlan().getId() );

		if ( null != image )
			imagePath = ImageFileUtil.from( getContext() ).getFilePath( image.getImageName() );

		int[] images = { R.drawable.ic_default_image_02, R.drawable.ic_default_image_03, R.drawable.ic_default_image_04, R.drawable.ic_default_image_05, R.drawable.ic_default_image_01 };

		int headerWidth = ScreenUtil.getWidthPixels( getContext() );
		int headerHeight = ScreenUtil.getHeightPixels( getContext() );

		if ( null != imagePath ) {

			Picasso.with( getContext() )
					.load( new File( imagePath ) )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 2.5 ) )
					.into( imageView_plan_background );
		}
		else {

			Picasso.with( getContext() )
					.load( images[new Random().nextInt( images.length )] )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 2.5 ) )
					.into( imageView_plan_background );
		}
	}

	public void setplanName( CharSequence planName ) {

		textView_planName.setText( planName );
	}

	private void setPeriod( Plan plan ) {

		String startDateString = DateUtil.formatToString( DateUtil.parseDate( plan.getStartDate() ) );
		String endDateString = DateUtil.formatToString( DateUtil.parseDate( plan.getEndDate() ) );
		int dayCount = plan.getPlanDayCount();

		String planPeroid = startDateString + " - " + endDateString + " | " + ( dayCount - 1 ) + "박" + dayCount + "일";
		textView_period.setText( planPeroid );
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

	public class OnPlanListItemLongClickEvent implements OttoUtil.Event {

		private long planId;

		public OnPlanListItemLongClickEvent( long planId ) {

			this.planId = planId;
		}

		public long getPlanId() {

			return planId;
		}

	}

}
