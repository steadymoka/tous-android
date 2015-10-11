package com.tous.application.mvc.controller.activity.image;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moka.framework.controller.BaseDialogFragment;
import com.moka.framework.util.CrashlyticsUtil;
import com.tous.application.R;
import com.tous.application.mvc.controller.activity.image.editor.ImageEditorActivity;
import com.tous.application.util.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class ImagePickerDialogFragment extends BaseDialogFragment implements View.OnClickListener {

	private static final int REQUEST_CODE_PICK_ONE_IMAGE = 0x0001;
	private static final int REQUEST_CODE_PICK_MANY_IMAGE = 0x0002;
	private static final int REQUEST_CODE_CARMERA = 0x0003;
	private static final int REQUEST_CODE_EDIT_IMAGE = 0x0004;

	private static final int DEFAULT_MAX_IMAGE_COUNT = 10;

	private String title = null;
	private boolean existImage = false;
	private int maxImageCount = DEFAULT_MAX_IMAGE_COUNT;
	private boolean cropEnable = false;
	private float aspectX = -1;
	private float aspectY = -1;

	private Uri fileUri;

	private View rootView;
	private TextView textView_title;
	private TextView textView_album;
	private TextView textView_camera;
	private TextView textView_delete;

	private static OnImagePickedListener onImagePickedListener;

	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {

		bindViews();
		initTitle( title );
		initExistImage( existImage );

		return getDialog( rootView );
	}

	private void bindViews() {

		rootView = LayoutInflater.from( getActivity() ).inflate( R.layout.dialog_image_picker, null );

		textView_title = (TextView) rootView.findViewById( R.id.textView_title );

		textView_album = (TextView) rootView.findViewById( R.id.textView_album );
		textView_album.setOnClickListener( this );

		textView_camera = (TextView) rootView.findViewById( R.id.textView_camera );
		textView_camera.setOnClickListener( this );

		textView_delete = (TextView) rootView.findViewById( R.id.textView_delete );
		textView_delete.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.textView_album:

				pickImageFromAlbum();
				break;

			case R.id.textView_camera:

				pickImageFromCamera();
				break;

			case R.id.textView_delete:

				if ( null != onImagePickedListener )
					onImagePickedListener.onDeleteImage();
				if ( isAdded() )
					dismiss();
				break;
		}
	}

	private void pickImageFromAlbum() {

		if ( 1 == maxImageCount )
			pickOneImageFromAlbum();
		else
			pickManyImageFromAlbum( maxImageCount );
	}

	private void pickOneImageFromAlbum() {

		try {

			Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
			intent.setType( "image/*" );
			startActivityForResult( intent, REQUEST_CODE_PICK_ONE_IMAGE );
		}
		catch ( ActivityNotFoundException e ) {

//			Intent intent = new Intent( getActivity(), GalleryActivity.class );
//			intent.putExtra( GalleryActivity.KEY_MAX_IMAGE_COUNT, 1 );
//			startActivityForResult( intent, REQUEST_CODE_PICK_MANY_IMAGE );
		}
	}

	private void pickManyImageFromAlbum( int maxImageCount ) {

//		Intent intent = new Intent( getActivity(), GalleryActivity.class );
//		intent.putExtra( GalleryActivity.KEY_MAX_IMAGE_COUNT, maxImageCount );
//		startActivityForResult( intent, REQUEST_CODE_PICK_MANY_IMAGE );
	}

	private void pickImageFromCamera() {

		try {

			Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
			fileUri = getOutputFileUri();
			intent.putExtra( MediaStore.EXTRA_OUTPUT, fileUri );
			startActivityForResult( intent, REQUEST_CODE_CARMERA );
		}
		catch ( ActivityNotFoundException e ) {

			Toast.makeText( getActivity(), R.string.dialog_image_picker_toast_not_found_activity, Toast.LENGTH_SHORT )
					.show();
		}
	}

	private Uri getOutputFileUri() {

		String directoryDCIM = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM ).getPath() + "/" + getString( R.string.app_name );
		File directory = new File( directoryDCIM );

		if ( !directory.exists() )
			directory.mkdirs();

		String str = DateUtil.formatToTimestamp( new Date() );
		File file = new File( directory, "tous_" + str + ".jpg" );

		return Uri.fromFile( file );
	}

	private void initTitle( String title ) {

		if ( !TextUtils.isEmpty( title ) )
			textView_title.setText( title );
	}

	private void initExistImage( boolean existImage ) {

		if ( existImage )
			textView_delete.setVisibility( View.VISIBLE );
		else
			textView_delete.setVisibility( View.GONE );
	}

	private Dialog getDialog( View view ) {

		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
		builder.setView( view );

		return builder.create();
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {

		switch ( requestCode ) {

			case REQUEST_CODE_PICK_ONE_IMAGE:

				if ( Activity.RESULT_OK == resultCode ) {

						String selectedImagePath = getOriginalImagePath( data.getData() );
					if ( null != selectedImagePath )
						startImageEditorActivity( selectedImagePath );
				}

				break;

			case REQUEST_CODE_PICK_MANY_IMAGE:

				if ( Activity.RESULT_OK == resultCode ) {

//					ArrayList<String> selectedImagePaths = data.getStringArrayListExtra( GalleryActivity.KEY_SELECTED_IMAGES );
//					startImageEditorActivity( selectedImagePaths );
				}

				break;

			case REQUEST_CODE_CARMERA:

				if ( Activity.RESULT_OK == resultCode ) {

					String selectedImagePath = fileUri.getPath();
					startImageEditorActivity( selectedImagePath );
				}

				break;

			case REQUEST_CODE_EDIT_IMAGE:

				if ( Activity.RESULT_OK == resultCode ) {

					ArrayList<String> imageNames = data.getExtras().getStringArrayList( ImageEditorActivity.SAVED_IMAGE_NAMES );
					if ( null != onImagePickedListener )
						onImagePickedListener.onImagePicked( imageNames );
					dismiss();
				}

				break;
		}
	}

	private String getOriginalImagePath( Uri thumbnailImageUri ) {

		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().getContentResolver().query( thumbnailImageUri, filePathColumn, null, null, null );

		String selectedImagePath = null;
		if ( null != cursor ) {

			if ( 0 < cursor.getCount() && cursor.moveToFirst() ) {

				cursor.moveToFirst();
				selectedImagePath = cursor.getString( cursor.getColumnIndex( filePathColumn[0] ) );
			}
			else {

				Exception throwable = new RuntimeException( ( null != thumbnailImageUri ) ? ( thumbnailImageUri.getPath() ) : ( "thumbnailImageUri is null" ) );
				CrashlyticsUtil.s( throwable );
			}

			cursor.close();
		}
		else {

			Exception throwable = new RuntimeException( ( null != thumbnailImageUri ) ? ( thumbnailImageUri.getPath() ) : ( "thumbnailImageUri is null" ) );
			CrashlyticsUtil.s( throwable );
		}

		return selectedImagePath;
	}

	private void startImageEditorActivity( String selectedImagePath ) {

		ArrayList<String> selectedImagePaths = new ArrayList<>();
		selectedImagePaths.add( selectedImagePath );
		startImageEditorActivity( selectedImagePaths );
	}

	// TODO background thread 로 처리할 필요가 있음
	private void startImageEditorActivity( ArrayList<String> selectedImagePaths ) {

		if ( cropEnable ) {

			Intent intent = new Intent( getActivity(), ImageEditorActivity.class );
			intent.putExtra( ImageEditorActivity.IMAGE_PATHES, selectedImagePaths );
			intent.putExtra( ImageEditorActivity.ASPECT_X, aspectX );
			intent.putExtra( ImageEditorActivity.ASPECT_Y, aspectY );
			startActivityForResult( intent, REQUEST_CODE_EDIT_IMAGE );
		}
		else {

			SaveImageUtil.from( selectedImagePaths, getActivity() )
					.start( new SaveImageUtil.OnSaveImageListener() {

						@Override
						public void onSaveImage( ArrayList<String> imageNames ) {

							if ( null != onImagePickedListener )
								onImagePickedListener.onImagePicked( imageNames );
						}

					} );
			dismiss();
		}
	}

	public ImagePickerDialogFragment setTitle( String title ) {

		this.title = title;
		return this;
	}

	public ImagePickerDialogFragment setExistImage( boolean existImage ) {

		this.existImage = existImage;
		return this;
	}

	public ImagePickerDialogFragment setMaxImageCount( int maxImageCount ) {

		if ( 0 < maxImageCount )
			this.maxImageCount = maxImageCount;

		return this;
	}

	public ImagePickerDialogFragment setCropEnable( boolean cropEnable ) {

		this.cropEnable = cropEnable;
		return this;
	}

	public ImagePickerDialogFragment setAspect( float aspectX, float aspectY ) {

		if ( 0f < aspectX && 0f < aspectY ) {

			this.aspectX = aspectX;
			this.aspectY = aspectY;
		}

		return this;
	}

	public void showDialog( FragmentManager fragmentManager, OnImagePickedListener onImagePickedListener ) {

		setOnImagePickedListener( onImagePickedListener );
		show( fragmentManager, "ImagePickerDialogFragment" );
	}

	private void setOnImagePickedListener( OnImagePickedListener onImagePickedListener ) {

		this.onImagePickedListener = onImagePickedListener;
	}

	public static ImagePickerDialogFragment newInstance() {

		return new ImagePickerDialogFragment();
	}

	public interface OnImagePickedListener {

		void onImagePicked( ArrayList<String> imageNames );

		void onDeleteImage();

	}

}
