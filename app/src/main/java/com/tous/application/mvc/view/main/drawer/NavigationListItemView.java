package com.tous.application.mvc.view.main.drawer;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.widget.adapter.RecyclerItemView;
import com.tous.application.R;
import com.tous.application.mvc.model.itemdata.NavigationListItemData;


public class NavigationListItemView extends RecyclerItemView<NavigationListItemData> implements View.OnClickListener {

	private LinearLayout linearLayout_settingDetail;
	private ImageView imageView_detailIcon;
	private TextView textView_detailTitle;

	private View horizontalDivider_top;
	private View horizontalDivider_bottom;

	private OnItemClickListener onItemClickListener;

	public NavigationListItemView( Context context, View itemView ) {

		super( context, itemView );

		linearLayout_settingDetail = (LinearLayout) findViewById( R.id.linearLayout_settingDetail );
		linearLayout_settingDetail.setOnClickListener( this );

		imageView_detailIcon = (ImageView) findViewById( R.id.imageView_detailIcon );
		textView_detailTitle = (TextView) findViewById( R.id.textView_detailTitle );

		horizontalDivider_top = findViewById( R.id.horizontalDivider_top );
		horizontalDivider_bottom = findViewById( R.id.horizontalDivider_bottom );
//		horizontalDivider_bottom.setVisibility( View.VISIBLE );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_settingDetail:

				if ( null != onItemClickListener )
					onItemClickListener.onItemClick( getData() );
				break;
		}
	}

	@Override
	protected void refreshView( NavigationListItemData data ) {

		setDetail( data );
	}

	private void setDetail( NavigationListItemData settingListItemData ) {

		if ( NavigationListItemData.NO_ICON_ID != settingListItemData.getDetailIcon() ) {

			imageView_detailIcon.setVisibility( View.VISIBLE );
			imageView_detailIcon.setImageResource( settingListItemData.getDetailIcon() );
		}
		else {

			imageView_detailIcon.setVisibility( View.GONE );
		}

		if ( null != settingListItemData.getDetailTitle() ) {

			textView_detailTitle.setVisibility( View.VISIBLE );
			textView_detailTitle.setText( settingListItemData.getDetailTitle() );
		}
		else {

			textView_detailTitle.setVisibility( View.INVISIBLE );
		}

		if ( settingListItemData.isPlanList() ) {

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
			lp.setMargins( 120, 0, -30, 0 );
			imageView_detailIcon.setLayoutParams( lp );
			textView_detailTitle.setTextColor( 0xFF666666 );

			if ( settingListItemData.isCurrentPlan() )
				linearLayout_settingDetail.setBackgroundResource( R.drawable.selector_background_drawer_listitem_current_plan_function );
			else
				linearLayout_settingDetail.setBackgroundResource( R.drawable.selector_background_drawer_listitem_function );
		}
		else {

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
			lp.setMargins( 0, 0, -0, 0 );
			imageView_detailIcon.setLayoutParams( lp );
			textView_detailTitle.setTextColor( 0xFF000000 );
			linearLayout_settingDetail.setBackgroundResource( R.drawable.selector_background_drawer_listitem_function );
		}
	}

	public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {

		this.onItemClickListener = onItemClickListener;
	}

	public void setPosition( int position ) {

//		if ( 0 == position )
//			horizontalDivider_top.setVisibility( View.VISIBLE );
//		else
//			horizontalDivider_top.setVisibility( View.GONE );
	}

	public interface OnItemClickListener {

		void onItemClick( NavigationListItemData settingListItemData );

	}

}
