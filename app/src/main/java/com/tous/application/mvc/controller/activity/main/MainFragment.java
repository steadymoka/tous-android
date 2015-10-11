package com.tous.application.mvc.controller.activity.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.util.OttoUtil;
import com.squareup.otto.Subscribe;
import com.tous.application.R;
import com.tous.application.database.table.image.ImageTable;
import com.tous.application.database.table.plan.PlanTable;
import com.tous.application.event.OnRefreshViewEvent;
import com.tous.application.mvc.controller.activity.image.ImagePickerDialogFragment;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationActivity;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;
import com.tous.application.mvc.controller.activity.planlist.PlanListActivity;
import com.tous.application.mvc.model.image.Image;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainActivityListener;
import com.tous.application.mvc.view.main.MainFragmentLayout;
import com.tous.application.mvc.view.main.MainFragmentLayoutListener;
import com.tous.application.util.DateUtil;
import com.tous.application.util.ImageFileUtil;
import com.tous.application.util.TousPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainFragment extends BaseFragment implements MainFragmentLayoutListener, ImagePickerDialogFragment.OnImagePickedListener {

	private MainFragmentLayout fragmentLayout;
	private MainActivityListener mainActivityListener;

	private Plan plan;
	private boolean isExistImage;
	private Image image;

	private SimpleDateFormat dateFormat;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new MainFragmentLayout( this, this, inflater, container );
		fragmentLayout.setMainActivityListener( mainActivityListener );
		setHasOptionsMenu( true );
		OttoUtil.getInstance().register( this );

		loadData();
		refreshView();

		return fragmentLayout.getRootView();
	}

	private void loadData() {

		if ( null != plan )
			plan = PlanTable.from( getActivity() ).find( plan.getId() );
	}

	private void refreshView() {

		setImage();
		setUI();
	}

	private void setImage() {

		if ( null != plan ) {

			image = ImageTable.from( getActivity() ).findImageFrom( "plan", plan.getId() );

			String imageName;
			if ( null != image )
				imageName = image.getImageName();
			else
				imageName = null;

			if ( imageName != null && imageName.isEmpty() )
				imageName = null;

			isExistImage = ( imageName != null );
			fragmentLayout.setPlanImage( ImageFileUtil.from( getActivity() ).getFilePath( imageName ) );
		}
		else {

			fragmentLayout.setPlanImage( null );
		}
	}

	private void setUI() {

		if ( null != plan ) {

			fragmentLayout.setTextView_planName( plan.getName() );

			String startDateString = formatToString( DateUtil.parseDate( plan.getStartDate() ) );
			String endDateString = formatToString( DateUtil.parseDate( plan.getEndDate() ) );
			int dayCount = plan.getPlanDayCount();

			String planPeroid = startDateString + " - " + endDateString + " | " + ( dayCount - 1 ) + "박" + dayCount + "일";

			fragmentLayout.setTextView_planPeriod( planPeroid );

			int startDate = plan.getStartDate();
			int dDay = DateUtil.getDiffDayCount( DateUtil.formatToInt( DateUtil.getTodayDate() ), startDate );

			if ( 0 < dDay )
				fragmentLayout.setTextView_dDay( "D - " + dDay );
			else if ( plan.getPlanDayCount() > ( 0 - dDay ) )
				fragmentLayout.setTextView_dDay( ( 0 - dDay ) + 1 + " 일차" );
			else
				fragmentLayout.setTextView_dDay( "" );

			fragmentLayout.setTextView_planContent( plan.getContent() );
		}
		else {

			fragmentLayout.setNoPlan();
		}
	}

	private String formatToString( Date date ) {

		if ( null == dateFormat )
			dateFormat = new SimpleDateFormat( getString( R.string.fragment_plan_create_button_date_format ), Locale.getDefault() );

		return dateFormat.format( date );
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

	@Override
	public void onResume() {

		fragmentLayout.setMainActivityListener( mainActivityListener );
		super.onResume();
	}

	@Override
	public void onClickToDetailPlan() {

		if ( null != plan ) {

			Intent intent = new Intent( getActivity(), DetailPlanActivity.class );
			intent.putExtra( DetailPlanActivity.KEY_PLAN_ID, plan.getId() );
			startActivity( intent );
		}
		else {

			getActivity().startActivity( new Intent( getActivity(), PlanListActivity.class ) );
			fragmentLayout.showToast( "여행일정을 추가해 주세요" );
		}
	}

	@Override
	public void onClickToSettingImage() {

		if ( null != plan )
			ImagePickerDialogFragment.newInstance()
					.setExistImage( isExistImage )
					.setMaxImageCount( 1 )
					.setCropEnable( true )
					.setAspect( 5, 3 )
					.showDialog( getFragmentManager(), this );
	}

	@Override
	public void onImagePicked( ArrayList<String> imageNames ) {

		String imageName = imageNames.get( 0 );

		if ( null == image ) {

			image = new Image();
			image.setImageType( "plan" );
			image.setImageId( plan.getId() );
			image.setImageName( imageName );

			ImageTable.from( getActivity() ).insert( image );
		}
		else {

			image.setImageType( "plan" );
			image.setImageId( plan.getId() );
			image.setImageName( imageName );

			ImageTable.from( getActivity() ).update( image );
		}

		fragmentLayout.setPlanImage( ImageFileUtil.from( getActivity() ).getFilePath( imageNames.get( 0 ) ) );

		isExistImage = true;
	}

	@Override
	public void onDeleteImage() {

		image.setImageType( "plan" );
		image.setImageId( plan.getId() );
		image.setImageName( "" );

		ImageTable.from( getActivity() ).update( image );

		fragmentLayout.setPlanImage( null );
		isExistImage = false;
	}

	@Override
	public void onEditPlanMenuItemSelected() {

		if ( null != plan ) {

			Intent intent = new Intent( getActivity(), PlanCreationActivity.class );
			intent.putExtra( PlanCreationActivity.KEY_PLAN_ID, plan.getId() );
			startActivity( intent );
		}
		else {

			fragmentLayout.showToast( "여행일정을 추가해 주세요" );
		}
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

	public MainFragment setPlan( Plan plan ) {

		this.plan = plan;
		return this;
	}

	public MainFragment setMainActivityListener( MainActivityListener mainActivityListener ) {

		this.mainActivityListener = mainActivityListener;
		if ( null != fragmentLayout )
			fragmentLayout.setMainActivityListener( mainActivityListener );

		return this;
	}

	public static MainFragment newInstance() {

		return new MainFragment();
	}

}
