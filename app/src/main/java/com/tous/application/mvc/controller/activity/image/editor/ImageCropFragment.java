package com.tous.application.mvc.controller.activity.image.editor;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.moka.framework.controller.BaseFragment;
import com.tous.application.mvc.controller.activity.image.SaveImageUtil;
import com.tous.application.mvc.view.image.editor.ImageCropFragmentLayout;
import com.tous.application.mvc.view.image.editor.ImageCropFragmentLayoutListener;

import java.util.ArrayList;


public class ImageCropFragment extends BaseFragment implements ImageCropFragmentLayoutListener {

	private ImageCropFragmentLayout fragmentLayout;

	private ArrayList<String> selectedImagePaths = new ArrayList<>();

	private float aspectX;
	private float aspectY;

	@Override
	public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

		fragmentLayout = new ImageCropFragmentLayout( this, this, inflater, container );

		setProperty();
		setHasOptionsMenu( true );

		return fragmentLayout.getRootView();
	}

	private void setProperty() {

		selectedImagePaths = getActivity().getIntent().getExtras().getStringArrayList( ImageEditorActivity.IMAGE_PATHES );

		aspectX = getActivity().getIntent().getExtras().getFloat( ImageEditorActivity.ASPECT_X );
		aspectY = getActivity().getIntent().getExtras().getFloat( ImageEditorActivity.ASPECT_Y );

		setImage( selectedImagePaths, aspectX, aspectY );
	}

	private void setImage( ArrayList<String> selectedImagePaths, float aspectX, float aspectY ) {

		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;

		String imagePath = selectedImagePaths.get( 0 );

		BitmapFactory.decodeFile( imagePath, option );
		float rate = (float) option.outHeight / (float) option.outWidth;

		fragmentLayout.setCropRatio( aspectX, aspectY );

//		Bitmap bitmap = null;
//		try {
//
//			final URLConnection conn;
//			URL url = new URL( imagePath );
//			conn = url.openConnection();
//			conn.connect();
//			final BufferedInputStream bis = new BufferedInputStream( conn.getInputStream() );
//			bitmap = BitmapFactory.decodeStream( bis );
//			bis.close();
//
//		}
//		catch ( IOException e ) {
//
//		}
//
//		float remoteRate =bitmap.getHeight() /  bitmap.getWidth();

		if ( imagePath.startsWith( "http" ) )
			fragmentLayout.setImageFromRemote( imagePath, rate );
		else
			fragmentLayout.setImageFromLocal( imagePath, rate );
	}

	@Override
	public void onSaveCropImage() {

		SaveImageUtil.from( fragmentLayout.cropImage(), getActivity() )
				.start( new SaveImageUtil.OnSaveImageListener() {

					@Override
					public void onSaveImage( ArrayList<String> imageNames ) {

						Intent intent = new Intent();
						ArrayList<String> imagesArray = new ArrayList<>();

						imagesArray.add( imageNames.get( 0 ) );

						intent.putStringArrayListExtra( ImageEditorActivity.SAVED_IMAGE_NAMES, imagesArray );

						getActivity().setResult( Activity.RESULT_OK, intent );
						getActivity().finish();
					}

				} );
	}

	@Override
	public void onCompleteCropImage() {

		// TODO 크롭후 바로 저장 안될때
//		galleryEditFragment.refreshGalleryItemDatas( cropedImageFile.getPath(), position, fragmentLayout.cropImage() );
//		getActivity().onBackPressed();
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

}
