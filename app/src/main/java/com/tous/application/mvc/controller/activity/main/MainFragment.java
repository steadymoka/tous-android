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
import com.tous.application.mvc.controller.activity.image.ImagePickerDialogFragment;
import com.tous.application.mvc.controller.activity.plancreation.PlanCreationActivity;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainActivityListener;
import com.tous.application.mvc.view.main.MainFragmentLayout;
import com.tous.application.mvc.view.main.MainFragmentLayoutListener;
import com.tous.application.util.ImageFileUtil;
import com.tous.application.util.TousPreference;

import java.util.ArrayList;


public class MainFragment extends BaseFragment implements MainFragmentLayoutListener, ImagePickerDialogFragment.OnImagePickedListener {

	private MainFragmentLayout fragmentLayout;
	private MainActivityListener mainActivityListener;

	private Plan plan;
	private boolean isExistImage;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

		fragmentLayout = new MainFragmentLayout( this, this, inflater, container );
		fragmentLayout.setMainActivityListener( mainActivityListener );
		setHasOptionsMenu( true );

		refreshView();

		return fragmentLayout.getRootView();
	}

	private void refreshView() {

		String imageName = TousPreference.getInstance( getActivity() ).getPlanImageName();
		isExistImage = ( imageName != null );
		fragmentLayout.setPlanImage( ImageFileUtil.from( getActivity() ).getFilePath( imageName ) );
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

		Intent intent = new Intent( getActivity(), DetailPlanActivity.class );
		intent.putExtra( DetailPlanActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
	}

	@Override
	public void onClickToSettingImage() {

		ImagePickerDialogFragment.newInstance()
				.setExistImage( isExistImage )
				.setMaxImageCount( 1 )
				.setCropEnable( true )
				.setAspect( 20, 10 )
				.showDialog( getFragmentManager(), this );
	}

	@Override
	public void onImagePicked( ArrayList<String> imageNames ) {

		TousPreference.getInstance( getActivity() ).setPlanImageName( imageNames.get( 0 ) );
		fragmentLayout.setPlanImage( ImageFileUtil.from( getActivity() ).getFilePath( imageNames.get( 0 ) ) );

		isExistImage = true;
	}

	@Override
	public void onDeleteImage() {

		TousPreference.getInstance( getActivity() ).setPlanImageName( null );
		fragmentLayout.setPlanImage( null );
		isExistImage = false;
	}

	@Override
	public void onEditPlanMenuItemSelected() {

		Intent intent = new Intent( getActivity(), PlanCreationActivity.class );
		intent.putExtra( PlanCreationActivity.KEY_PLAN_ID, plan.getId() );
		startActivity( intent );
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
