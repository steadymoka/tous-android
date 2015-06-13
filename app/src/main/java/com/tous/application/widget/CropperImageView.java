package com.tous.application.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.edmodo.cropper.CropImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class CropperImageView extends CropImageView implements Target {

	private OnImageSetListener onImageSetListener;

	public CropperImageView( Context context ) {

		super( context );
	}

	public CropperImageView( Context context, AttributeSet attrs ) {

		super( context, attrs );
	}

	@Override
	public void onBitmapLoaded( Bitmap bitmap, Picasso.LoadedFrom from ) {

		setImageBitmap( bitmap );

		if ( null != onImageSetListener )
			onImageSetListener.onImageSet();
	}

	@Override
	public void onBitmapFailed( Drawable errorDrawable ) {

	}

	@Override
	public void onPrepareLoad( Drawable placeHolderDrawable ) {

	}

	public void setOnImageSetListener( OnImageSetListener onImageSetListener ) {

		this.onImageSetListener = onImageSetListener;
	}

	public interface OnImageSetListener {

		public void onImageSet();

	}

}
