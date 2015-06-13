package com.tous.application.mvc.view.image.editor;


import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.moka.framework.controller.BaseActivity;
import com.moka.framework.view.SupportActivityLayout;
import com.tous.application.R;


public class ImageEditorActivityLayout extends SupportActivityLayout<BaseActivity, ImageEditorActivityLayoutListener> {

	private ActionBar actionBar;

	public ImageEditorActivityLayout( BaseActivity activity, ImageEditorActivityLayoutListener layoutListener ) {

		super( activity, layoutListener );
	}

	@Override
	protected int getLayoutResId() {

		return R.layout.activity_image_editor;
	}

	@Override
	protected void onLayoutInflated() {

		actionBar = getActivity().getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled( true );
		actionBar.setDisplayHomeAsUpEnabled( true );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		switch ( item.getItemId() ) {

			case android.R.id.home:

				getLayoutListener().onHomeMenuItemSelected();
				return true;
		}

		return super.onOptionsItemSelected( item );
	}

	public int getFragmentContainerID() {

		return R.id.frameLayout_container_image_editor;
	}

}
