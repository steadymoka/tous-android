package com.tous.application.mvc.view.spot;


import android.app.ActionBar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.support.toolbar.ToolbarLayout;
import com.moka.framework.util.ScreenUtil;
import com.moka.framework.view.FragmentLayout;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.spot.SpotDetailFragment;

import org.w3c.dom.Text;

import java.io.File;


public class SpotDetailFragmentLayout extends FragmentLayout<SpotDetailFragment, SpotDetailFragmentLayoutListener> implements View.OnClickListener {

	private ImageView imageView_spotImage;

	private LinearLayout linearLayout_spotName;
	private TextView textView_spotName;
	private TextView textView_planTime;
	private TextView textView_search_spot_map;
	private TextView textView_search_spot_web;
	private TextView textView_spotContent;

	public SpotDetailFragmentLayout( SpotDetailFragment fragment, SpotDetailFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_spot_deatail;
	}

	@Override
	protected void onLayoutInflated() {

		imageView_spotImage = (ImageView) findViewById( R.id.imageView_spotImage );

		linearLayout_spotName = (LinearLayout) findViewById( R.id.linearLayout_spotName );
		textView_spotName = (TextView) findViewById( R.id.textView_spotName );
		textView_spotName.setSelected( true );

		textView_planTime = (TextView) findViewById( R.id.textView_planTime );

		textView_search_spot_map = (TextView) findViewById( R.id.textView_search_spot_map );
		textView_search_spot_map.setOnClickListener( this );
		textView_search_spot_web = (TextView) findViewById( R.id.textView_search_spot_web );
		textView_search_spot_web.setOnClickListener( this );

		textView_spotContent = (TextView) findViewById( R.id.textView_spotContent );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.textView_search_spot_web:

				getLayoutListener().onClickWeb();
				break;

			case R.id.textView_search_spot_map:

				getLayoutListener().onClickMap();
				break;
		}
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		inflater.inflate( R.menu.fragment_detail_plan, menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case R.id.menuItem_editPlan:

				getLayoutListener().onEditSpotMenuItemSelected();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	public void setViewSpotNameBackgroundColor() {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
			getActivity().getWindow().setStatusBarColor( getActivity().getResources().getColor( R.color.spot_type_view_spot ) );
		linearLayout_spotName.setBackgroundResource( R.color.spot_type_view_spot );
	}

	public void setRestaurantNameBackgroundColor() {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
			getActivity().getWindow().setStatusBarColor( getActivity().getResources().getColor( R.color.spot_type_restaurant ) );
		linearLayout_spotName.setBackgroundResource( R.color.spot_type_restaurant );
	}

	public void setLodgmentNameBackgroundColor() {

		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
			getActivity().getWindow().setStatusBarColor( getActivity().getResources().getColor( R.color.spot_type_lodgment ) );
		linearLayout_spotName.setBackgroundResource( R.color.spot_type_lodgment );
	}

	public void setTexView_spotName( CharSequence name ) {

		textView_spotName.setText( name );
	}

	public void setTextView_planTime( CharSequence planTime ) {

		textView_planTime.setText( planTime );
	}

	public void setTextView_search_spot_map( CharSequence address ) {

		textView_search_spot_map.setText( address );
	}

	public void setTextView_search_spot_web( CharSequence webSiteAdrress ) {

		textView_search_spot_web.setText( webSiteAdrress );
	}

	public String getTextView_search_spot_web() {

		return textView_search_spot_web.getText().toString();
	}

	public void setTextView_spotContent( CharSequence spotContent ) {

		textView_spotContent.setText( spotContent );
	}

	public void setSpotImage( String imagePath ) {

		int defaultImage = R.drawable.image_add_image;

		int headerWidth = ScreenUtil.getWidthPixels( getContext() );
		int headerHeight = ScreenUtil.getHeightPixels( getContext() );

		if ( null != imagePath ) {

			Picasso.with( getContext() )
					.load( new File( imagePath ) )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 1.7 ) )
					.into( imageView_spotImage );
		}
		else {

			Picasso.with( getContext() )
					.load( defaultImage )
					.centerCrop()
					.resize( headerWidth, (int) ( headerWidth / 1.7 ) )
					.into( imageView_spotImage );
		}
	}

}
