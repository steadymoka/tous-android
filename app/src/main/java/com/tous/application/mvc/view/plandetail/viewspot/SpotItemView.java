package com.tous.application.mvc.view.plandetail.viewspot;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.OttoUtil;
import com.moka.framework.widget.adapter.RecyclerItemView;
import com.tous.application.R;
import com.tous.application.mvc.model.itemdata.SpotItemData;


public class SpotItemView extends RecyclerItemView<SpotItemData> implements View.OnClickListener {

	private LinearLayout linearLayout_spot;
	private TextView textView_spotName;

	public SpotItemView( Context context, View itemView ) {

		super( context, itemView );
		initView();
	}

	private void initView() {

		linearLayout_spot = (LinearLayout) findViewById( R.id.linearLayout_spot );
		linearLayout_spot.setOnClickListener( this );

		textView_spotName = (TextView) findViewById( R.id.textView_spotName );
	}

	@Override
	protected void refreshView( SpotItemData data ) {

		textView_spotName.setText( data.getSpot().getName() );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_spot:

				OttoUtil.getInstance().post( new OnSpotItemClickEvent( getData().getSpotId() ) );
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
