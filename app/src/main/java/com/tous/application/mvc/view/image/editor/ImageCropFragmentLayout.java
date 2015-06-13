package com.tous.application.mvc.view.image.editor;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.moka.framework.controller.BaseFragment;
import com.moka.framework.view.FragmentLayout;
import com.squareup.picasso.Picasso;
import com.tous.application.R;
import com.tous.application.widget.CropperImageView;

import java.io.File;


public class ImageCropFragmentLayout extends FragmentLayout<BaseFragment, ImageCropFragmentLayoutListener> implements CropperImageView.OnImageSetListener {

	private int aspectX;
	private int aspectY;

	private CropperImageView cropImageView_image;

	public ImageCropFragmentLayout( BaseFragment fragment, ImageCropFragmentLayoutListener layoutListener, LayoutInflater inflater, ViewGroup container ) {

		super( fragment, layoutListener, inflater, container );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.fragment_image_editor_crop;
	}

	@Override
	protected void onLayoutInflated() {

		cropImageView_image = (CropperImageView) findViewById( R.id.cropImageView_image );
		cropImageView_image.setOnImageSetListener( this );
		cropImageView_image.setGuidelines( 2 );
	}

	@Override
	public void onImageSet() {

		new Handler().post( new Runnable() {

			@Override
			public void run() {

				cropImageView_image.setAspectRatio( aspectX, aspectY );
				cropImageView_image.setFixedAspectRatio( true );
			}

		} );
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

		inflater.inflate( R.menu.fragment_image_editor, menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case R.id.menuItem_saveCropedImage:

				getLayoutListener().onSaveCropImage();
				return true;

//			case R.id.menuItem_completeCropImage:
//
//				getLayoutListener().onCompleteCropImage();
//				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	public void setCropRatio( float aspectX, float aspectY ) {

		this.aspectX = (int) aspectX;
		this.aspectY = (int) aspectY;
	}

	public void setImageFromRemote( String imagePath, float rate ) {

		Uri imageUri = Uri.parse( imagePath );

		Picasso.with( getContext() )
				.load( imageUri )
				.into( cropImageView_image );
	}

	public void setImageFromLocal( String imagePath, float rate ) {

		Uri imageUri = Uri.fromFile( new File( imagePath ) );
		setImage( imageUri, rate );
	}

	public void setImage( Uri imageUri, float rate ) {

		DisplayMetrics outMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics( outMetrics );
		int targetWidth = outMetrics.widthPixels;
		int targetHeight = (int) ( outMetrics.widthPixels * rate );

		Picasso.with( getContext() )
				.load( imageUri )
				.centerCrop()
				.resize( targetWidth, targetHeight )
				.into( cropImageView_image );
	}

	public Bitmap cropImage() {

		return cropImageView_image.getCroppedImage();
	}

}
