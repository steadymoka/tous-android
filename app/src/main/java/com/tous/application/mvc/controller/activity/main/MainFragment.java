package com.tous.application.mvc.controller.activity.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.tous.application.mvc.controller.activity.image.ImagePickerDialogFragment;
import com.tous.application.mvc.controller.activity.plandetail.DetailPlanActivity;
import com.tous.application.mvc.model.plan.Plan;
import com.tous.application.mvc.view.main.MainActivityListener;
import com.tous.application.mvc.view.main.MainFragmentLayout;
import com.tous.application.mvc.view.main.MainFragmentLayoutListener;
import com.tous.application.util.ImageFileUtil;

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

		refreshView();

		return fragmentLayout.getRootView();
	}

	private void refreshView() {


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

		fragmentLayout.setPlanBackgroundImage( ImageFileUtil.from( getActivity() ).getFilePath( imageNames.get( 0 ) ) );

		isExistImage = true;
	}

	@Override
	public void onDeleteImage() {

		isExistImage = false;
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
