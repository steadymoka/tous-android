package com.tous.application.mvc.controller.activity.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.moka.framework.util.CrashlyticsUtil;
import com.tous.application.util.ImageFileUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class SaveImageUtil extends Thread {

	private ArrayList<String> selectedImagePaths;
	private Bitmap imageBitmap;

	private Context context;
	private Handler handler;
	private OnSaveImageListener onSaveImageListener;

	private boolean flag;

	private ArrayList<String> imageNames = new ArrayList<>();

	private SaveImageUtil( ArrayList<String> selectedImagePaths, Context context ) {

		this.selectedImagePaths = selectedImagePaths;
		this.context = context;
		this.flag = true;
		handler = new Handler();
	}

	private SaveImageUtil( Bitmap imageBitmap, Context context ) {

		this.imageBitmap = imageBitmap;
		this.context = context;
		this.flag = false;
		handler = new Handler();
	}

	public void start( OnSaveImageListener onSaveImageListener ) {

		super.start();
		this.onSaveImageListener = onSaveImageListener;
	}

	public ArrayList<String> performSync() {

		run();

		return imageNames;
	}

	@Override
	public void run() {

		super.run();

		if ( flag ) {

			for ( int i = 0; i < selectedImagePaths.size(); i++ ) {

				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 2; // TODO 메모리 문제가 없다면 없애는게 좋을듯
				Bitmap imageBitmap = BitmapFactory.decodeFile( selectedImagePaths.get( i ), option );

				String filePath = ImageFileUtil.from( context ).getParentPath();
				String imageName = ImageFileUtil.from( context ).generateFileName();

				File selectedImageFile = storeEditedImage( imageBitmap, filePath, imageName );
				imageNames.add( selectedImageFile.getName() );
			}

			handler.post( new Runnable() {

				@Override
				public void run() {

					if ( null != onSaveImageListener )
						onSaveImageListener.onSaveImage( imageNames );
				}

			} );
		}
		else {

			String filePath = ImageFileUtil.from( context ).getParentPath();
			String imageName = ImageFileUtil.from( context ).generateFileName();

			final File cropedImageFile = storeEditedImage( imageBitmap, filePath, imageName );
			imageNames.add( cropedImageFile.getName() );

			handler.post( new Runnable() {

				@Override
				public void run() {

					if ( null != onSaveImageListener )
						onSaveImageListener.onSaveImage( imageNames );
				}

			} );
		}
	}

	private File storeEditedImage( Bitmap bitmap, String filePath, String fileName ) {

		File directory = new File( filePath );

		if ( !directory.exists() )
			directory.mkdir();

		File file = new File( directory, fileName );
		BufferedOutputStream out;

		try {

			file.createNewFile();
			out = new BufferedOutputStream( new FileOutputStream( file ) );

			bitmap.compress( Bitmap.CompressFormat.JPEG, 100, out );
			bitmap.recycle();

			out.flush();
			out.close();
		}
		catch ( Exception e ) {

			CrashlyticsUtil.s( e );
		}

		return file;
	}

	public static SaveImageUtil from( ArrayList<String> selectedImagePaths, Context context ) {

		return new SaveImageUtil( selectedImagePaths, context );
	}

	public static SaveImageUtil from( Bitmap imageBitmap, Context context ) {

		return new SaveImageUtil( imageBitmap, context );
	}

	public interface OnSaveImageListener {

		public void onSaveImage( ArrayList<String> imageNames );

	}

}
