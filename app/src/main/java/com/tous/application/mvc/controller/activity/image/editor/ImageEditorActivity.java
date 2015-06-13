package com.tous.application.mvc.controller.activity.image.editor;


import android.os.Bundle;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.tous.application.mvc.view.image.editor.ImageEditorActivityLayout;
import com.tous.application.mvc.view.image.editor.ImageEditorActivityLayoutListener;


public class ImageEditorActivity extends BaseActivity implements ImageEditorActivityLayoutListener {

	public static final String SAVED_IMAGE_NAMES = "ImageEditorActivity.SAVED_IMAGE_NAMES";
	public static final String IMAGE_PATHES = "ImageEditorActivity.IMAGE_PATHES";
	public static final String ASPECT_X = "aspectX";
	public static final String ASPECT_Y = "aspectY";

	private ImageEditorActivityLayout activityLayout;
	private ImageCropFragment imageCropFragment;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );

		activityLayout = new ImageEditorActivityLayout( this, this );
		setContentView( activityLayout.getRootView() );

		if ( null == imageCropFragment )
			imageCropFragment = new ImageCropFragment();

		getSupportFragmentManager().beginTransaction()
				.replace( activityLayout.getFragmentContainerID(), imageCropFragment )
				.commit();
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		if ( activityLayout.onOptionsItemSelected( item ) )
			return true;
		else
			return super.onOptionsItemSelected( item );
	}

	@Override
	public void onHomeMenuItemSelected() {

		onBackPressed();
	}

}
