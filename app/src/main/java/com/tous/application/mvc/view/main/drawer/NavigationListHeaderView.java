package com.tous.application.mvc.view.main.drawer;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moka.framework.util.ScreenUtil;
import com.squareup.picasso.Picasso;
import com.tous.application.R;

import java.io.File;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class NavigationListHeaderView extends FrameLayout implements View.OnClickListener {

	private LinearLayout linearLayout_profile;
//	private CircleImageView circleiamgeView_profileImage;

	private TextView textView_userName;
	private TextView textView_userEmail;

	private HeaderViewListener headerViewListener;

	public NavigationListHeaderView( Context context ) {

		this( context, null );
	}

	public NavigationListHeaderView( Context context, AttributeSet attrs ) {

		super( context, attrs );

		initView();
	}

	private void initView() {

		inflate( getContext(), R.layout.view_navigation_list_header, this );

		linearLayout_profile = (LinearLayout) findViewById( R.id.linearLayout_profile );
		initBackground();

//		circleiamgeView_profileImage = (CircleImageView) findViewById( R.id.circleiamgeView_profileImage );

		textView_userName = (TextView) findViewById( R.id.textView_userName );
		textView_userEmail = (TextView) findViewById( R.id.textView_userEmail );
	}

	private void initBackground() {

		int[] images = { R.drawable.ic_default_image_02, R.drawable.ic_default_image_03, R.drawable.ic_default_image_04, R.drawable.ic_default_image_05 };
		linearLayout_profile.setBackgroundResource( images[new Random().nextInt( images.length )] );
	}

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

		widthMeasureSpec = MeasureSpec
				.makeMeasureSpec( ScreenUtil.getWidthPixels( getContext() ), MeasureSpec.EXACTLY );
		super.onMeasure( widthMeasureSpec, heightMeasureSpec );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.linearLayout_profile:

				if ( null != headerViewListener )
					headerViewListener.onClickToProfile();
				break;
		}
	}

	public void setProfileImage( String imagePath, Activity activity ) {

//		DisplayMetrics displayMetrics = new DisplayMetrics();
//		activity.getWindowManager().getDefaultDisplay().getMetrics( displayMetrics );
//		int headerWidth = displayMetrics.widthPixels;
//		int headerHeight = (int) ( displayMetrics.widthPixels / 1.9f );
//
//		if ( null != imagePath ) {
//
//			Picasso.with( getContext() )
//					.load( new File( imagePath ) )
//					.centerCrop()
//					.resize( headerWidth, headerHeight )
//					.into( circleiamgeView_profileImage );
//		}
//		else {
//
//			int image = R.drawable.ic_sample_profile_image;
//
//			Picasso.with( getContext() )
//					.load( image )
//					.centerCrop()
//					.resize( headerWidth, headerHeight )
//					.into( circleiamgeView_profileImage );
//		}
	}

	public void setProfile( String userName, String userEmail ) {

		textView_userName.setText( userName );
		textView_userEmail.setText( userEmail );
	}

	public void setHeaderViewListener( HeaderViewListener headerViewListener ) {

		this.headerViewListener = headerViewListener;
	}

	public interface HeaderViewListener {

		void onClickToProfile();

	}

}
