package com.tous.application.util;


import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.UUID;


public class ImageFileUtil {

	private static final String IMAGE_DIRECTORY = "/image/";
	private static final String FILE_FORMAT_JPG = "jpg";

	private static ImageFileUtil intance;

	private String parentPath;

	private ImageFileUtil( Context context ) {

		parentPath = context.getFilesDir().getPath() + IMAGE_DIRECTORY;
	}

	public String getParentPath() {

		return parentPath;
	}

	public String generateFileName() {

		long timestamp = DateUtil.getTimestamp();
		UUID uuid = UUID.randomUUID();

		return String.format( "%d-%s.%s", timestamp, uuid, FILE_FORMAT_JPG );
	}

	public String getFilePath( String fileName ) {

		if ( !TextUtils.isEmpty( fileName ) )
			return parentPath + fileName;
		else
			return null;
	}

	public File getFile( String fileName ) {

		if ( null != fileName )
			return new File( getDirectory(), fileName );
		else
			return new File( getDirectory(), "" );
	}

	public Uri getUri( String fileName ) {

		return Uri.fromFile( getFile( fileName ) );
	}

	public File getDirectory() {

		File directory = new File( getParentPath() );

		if ( !directory.exists() )
			directory.mkdirs();

		return directory;
	}

	public static String getImageName( String imagePath ) {

		return imagePath.substring( imagePath.lastIndexOf( "/" ) + 1 );
	}

	public static ImageFileUtil from( Context context ) {

		if ( null == intance )
			intance = new ImageFileUtil( context );

		return intance;
	}

}
