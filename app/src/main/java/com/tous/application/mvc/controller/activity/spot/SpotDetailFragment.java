package com.tous.application.mvc.controller.activity.spot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.squareup.otto.Subscribe;
import com.tous.application.database.table.image.ImageTable;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.database.table.spot.SpotTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.browser.WebViewActivity;
import com.tous.application.mvc.controller.activity.map.CheckLocationActivity;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationActivity;
import com.tous.application.mvc.model.image.Image;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.model.spot.Spot;
import com.tous.application.mvc.view.spot.SpotDetailFragmentLayout;
import com.tous.application.mvc.view.spot.SpotDetailFragmentLayoutListener;
import com.tous.application.mvc.view.spot.SpotDetailListener;
import com.tous.application.util.DateUtil;
import com.tous.application.util.ImageFileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SpotDetailFragment extends BaseFragment implements SpotDetailFragmentLayoutListener {

	private SpotDetailFragmentLayout fragmentLayout;

	private Plan plan;
	private Spot spot;
	private long spotId;
	private boolean isExistImage;
	private SpotDetailListener spotDetailListener;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new SpotDetailFragmentLayout( this, this, inflater, container );

		setHasOptionsMenu( true );
		OttoUtil.getInstance().register( this );

		loadData();
		refreshView();

		return fragmentLayout.getRootView();
	}

	private void loadData() {

		spot = SpotTable.from( getActivity() ).find( spotId );
		if ( null != spot )
			plan = PlanTable.from( getActivity() ).find( spot.getPlanId() );
	}

	private void refreshView() {

		setImage();
		setSpotNameAndColor();
		setPlanTime();
		setAddressAndWeb();
		setSpotContent();
		initToolbar();
	}

	private void setImage() {

		String imagePath;
		Image image = ImageTable.from( getActivity() ).findImageFrom( "spot", spotId );

		if ( null != image )
			imagePath = ImageFileUtil.from( getActivity() ).getFilePath( image.getImageName() );
		else
			imagePath = null;
		fragmentLayout.setSpotImage( imagePath );
	}

	private void setSpotNameAndColor() {

		String spotType = spot.getType();

		if ( spotType.equals( "명소" ) )
			fragmentLayout.setViewSpotNameBackgroundColor();
		else if ( spotType.equals( "맛집" ) )
			fragmentLayout.setRestaurantNameBackgroundColor();
		else
			fragmentLayout.setLodgmentNameBackgroundColor();

		fragmentLayout.setTexView_spotName( spot.getName() );
	}

	private void setPlanTime() {

		String planDayOfSpot = formatToString( DateUtil.addDate( DateUtil.parseDate( plan.getStartDate() ), spot.getDayCount() ) );
		String planTime = String.valueOf( (int) spot.getStartTime() ) + ":00 - " + String.valueOf( (int) spot.getEndTime() ) + ":00";

		SpannableStringBuilder planTimeBuilder = new SpannableStringBuilder( planDayOfSpot + " " );

		SpannableString dayCountString = new SpannableString( spot.getDayCount() + " 일차\n" );
		dayCountString.setSpan( new ForegroundColorSpan( 0xFFba68c8 ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );
		dayCountString.setSpan( new RelativeSizeSpan( 0.818f ), 0, dayCountString.length(), Spannable.SPAN_COMPOSING );

		planTimeBuilder.append( dayCountString )
				.append( planTime );

		fragmentLayout.setTextView_planTime( planTimeBuilder );
	}

	private String formatToString( Date date ) {

		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy. MM. dd", Locale.getDefault() );

		return dateFormat.format( date );
	}

	private void setAddressAndWeb() {

		fragmentLayout.setTextView_search_spot_map( spot.getAddress() );
		fragmentLayout.setTextView_search_spot_web( spot.getSite() );
	}

	private void setSpotContent() {

		fragmentLayout.setTextView_spotContent( spot.getContent() );
	}

	private void initToolbar() {

		spotDetailListener.hideToolbar();
	}

	/**
	 * onClickEvent
	 */

	@Override
	public void onEditSpotMenuItemSelected() {

		Intent intent = new Intent( getActivity(), SpotCreationActivity.class );
		intent.putExtra( SpotCreationActivity.KEY_PLAN_ID, plan.getId() );
		intent.putExtra( SpotCreationActivity.KEY_SPOT_ID, spot.getId() );
		intent.putExtra( SpotCreationActivity.KEY_SPOT_TYPE, spot.getType() );
		startActivity( intent );
	}

	@Override
	public void onClickWeb() {

		String url = fragmentLayout.getTextView_search_spot_web();

		if ( url.equals( "" ) ) {

			url = "http://www.naver.com";
		}
		else {

			Intent intent = new Intent( getActivity(), WebViewActivity.class );
			intent.putExtra( WebViewActivity.KEY_URL, url );
			startActivity( intent );
		}
	}

	@Override
	public void onClickMap() {

		Intent intent = new Intent( getActivity(), CheckLocationActivity.class );
		intent.putExtra( "SPOT_ID", spotId );
		startActivity( intent );
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		fragmentLayout.onCreateOptionsMenu( menu, inflater );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		if ( fragmentLayout.onOptionsItemSelected( item ) )
			return true;
		else
			return super.onOptionsItemSelected( item );
	}

	@Subscribe
	public void onRefreshViewEvent( OnRefreshViewEvent onRefreshViewEvent ) {

		loadData();
		refreshView();
	}

	@Override
	public void onDestroyView() {

		OttoUtil.getInstance().unregister( this );
		super.onDestroyView();
	}

	public SpotDetailFragment setSpotId( long spotId ) {

		this.spotId = spotId;
		return this;
	}

	public SpotDetailFragment setListener( SpotDetailListener spotDetailListener ) {

		this.spotDetailListener = spotDetailListener;
		return this;
	}

	public static SpotDetailFragment newInstance() {

		return new SpotDetailFragment();
	}

}
