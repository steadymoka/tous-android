package com.moka.framework.widget.calendar.util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.moka.framework.util.ScreenUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;


public class CalendarAnimator {

	private static final long DEFAULT_DURATION = 300;

	private final float INTERCEPT_THRESHOLD;

	private View container;

	private View weekView;
	private int weekViewHeight;

	private View monthView;
	private int monthViewHeight;

	private long duration = DEFAULT_DURATION;
	private float fraction = 0f;

	private float touchDownY;
	private boolean scrolling;
	private float preMoveY;
	private int direction;

	private ValueAnimator collapseAnimator;
	private ValueAnimator expandAnimator;

	private OnAnimationUpdateListener onAnimationUpdateListener;

	public CalendarAnimator( Context context, View container, View weekView, View monthView ) {

		INTERCEPT_THRESHOLD = (float) ScreenUtil.dipToPixel( context, 12 );

		this.container = container;
		this.weekView = weekView;
		this.monthView = monthView;
	}

	public void setOnAnimationUpdateListener( OnAnimationUpdateListener onAnimationUpdateListener ) {

		this.onAnimationUpdateListener = onAnimationUpdateListener;
	}

	public boolean onInterceptTouchEvent( MotionEvent event, int monthViewOffset ) {

		final int action = MotionEventCompat.getActionMasked( event );

		if ( MotionEvent.ACTION_DOWN != action && scrolling )
			return true;

		switch ( action ) {

			case MotionEvent.ACTION_DOWN:

				touchDown( event.getRawY() );
				return false;

			case MotionEvent.ACTION_MOVE:

				return determineIntercept( event.getRawY(), monthViewOffset );

			default:

				return false;
		}
	}

	public boolean onTouchEvent( @NonNull MotionEvent event, int monthViewOffset ) {

		final int action = MotionEventCompat.getActionMasked( event );

		switch ( action ) {

			case MotionEvent.ACTION_MOVE:

				touchMove( event.getRawY(), monthViewOffset );
				return true;

			case MotionEvent.ACTION_UP:

				touchUp( direction, monthViewOffset );
				return true;

			default:

				return false;
		}
	}

	private void touchDown( float touchDownY ) {

		this.touchDownY = touchDownY;
		initViewInfo();
	}

	private boolean determineIntercept( float moveY, int monthViewOffset ) {

		float deltaY = touchDownY - moveY;

		if ( INTERCEPT_THRESHOLD < Math.abs( deltaY ) ) {

			scrolling = true;
			preMoveY = moveY;
			setFraction( fraction, monthViewOffset );

			if ( null != onAnimationUpdateListener )
				onAnimationUpdateListener.onChangeViewMode( true );

			return true;
		}
		else {

			return false;
		}
	}

	private void touchMove( float moveY, int monthViewOffset ) {

		float diffFraction = computeDiffFraction( preMoveY, moveY );
		setFraction( fraction + diffFraction, monthViewOffset );
		updateDirection( preMoveY, moveY );
		preMoveY = moveY;
	}

	private float computeDiffFraction( float preMoveY, float newMoveY ) {

		return ( newMoveY - preMoveY ) / ( monthViewHeight - weekViewHeight );
	}

	private float computeDiffFraction( float diffY ) {

		return diffY / ( monthViewHeight - weekViewHeight );
	}

	private void updateDirection( float preMoveY, float newMoveY ) {

		if ( preMoveY > newMoveY )
			direction = -1;
		else if ( preMoveY < newMoveY )
			direction = 1;
		else
			direction = 0;
	}

	private void touchUp( int direction, int monthViewOffset ) {

		scrolling = false;
		touchDownY = 0;

		if ( -1 == direction ) {

			if ( 0.85f > fraction )
				startCollapseAnimation( monthViewOffset );
			else
				startExpandAnimation( monthViewOffset );
		}
		else if ( 1 == direction ) {

			if ( 0.15f > fraction )
				startCollapseAnimation( monthViewOffset );
			else
				startExpandAnimation( monthViewOffset );
		}
		else {

			if ( 0.5f > fraction )
				startCollapseAnimation( monthViewOffset );
			else
				startExpandAnimation( monthViewOffset );
		}
	}

	public void touchDownExternal() {

		initViewInfo();
	}

	public boolean touchMoveExternal( float touchDeltaY, int monthViewOffset ) {

		if ( null != onAnimationUpdateListener )
			onAnimationUpdateListener.onChangeViewMode( true );

		scrolling = true;

		float newFraction = fraction + computeDiffFraction( touchDeltaY );
		updateDirection( touchDeltaY );

		return setFraction( newFraction, monthViewOffset );
	}

	private void updateDirection( float diff ) {

		if ( 0 > diff )
			direction = -1;
		else if ( 0 < diff )
			direction = 1;
		else
			direction = 0;
	}

	public void touchUpExternal( int monthViewOffset ) {

		scrolling = false;

		if ( -1 == direction ) {

			if ( 0.85f > fraction )
				startCollapseAnimation( monthViewOffset );
			else
				startExpandAnimation( monthViewOffset );
		}
		else if ( 1 == direction ) {

			if ( 0.15f > fraction )
				startCollapseAnimation( monthViewOffset );
			else
				startExpandAnimation( monthViewOffset );
		}
		else {

			if ( 0.5f > fraction )
				startCollapseAnimation( monthViewOffset );
			else
				startExpandAnimation( monthViewOffset );
		}
	}

	public void collapse( int monthViewOffset ) {

		startCollapseAnimation( monthViewOffset );
	}

	private void startCollapseAnimation( final int monthViewOffset ) {

		if ( isRunningAnimation() )
			return;

		initViewInfo();

		collapseAnimator = ValueAnimator.ofFloat( fraction, 0 );
		collapseAnimator.setDuration( duration );
		collapseAnimator.setInterpolator( new AccelerateDecelerateInterpolator() );
		collapseAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate( ValueAnimator animation ) {

				fraction = (float) animation.getAnimatedValue();
				updateView( monthViewOffset );

				if ( null != onAnimationUpdateListener )
					onAnimationUpdateListener.onUpdateAnimation( fraction );
			}

		} );
		collapseAnimator.addListener( new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd( Animator animation ) {

				updateView( monthViewOffset );

				if ( null != onAnimationUpdateListener )
					onAnimationUpdateListener.onCollapseCalendar();
			}

		} );
		collapseAnimator.start();
	}

	private boolean isRunningAnimation() {

		return ( null != collapseAnimator && collapseAnimator.isRunning() )
				|| ( null != expandAnimator && expandAnimator.isRunning() );
	}

	public void initViewInfo() {

		this.weekViewHeight = weekView.getMeasuredHeight();
		this.monthViewHeight = monthView.getMeasuredHeight();
	}

	private void updateView( int monthViewOffset ) {

		ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
		layoutParams.height = (int) ( ( monthViewHeight - weekViewHeight ) * fraction + weekViewHeight );
		container.setLayoutParams( layoutParams );

		ViewHelper.setTranslationY( monthView, monthViewOffset * ( 1 - fraction ) );
	}

	public void expand( int monthViewOffset ) {

		startExpandAnimation( monthViewOffset );
	}

	private void startExpandAnimation( final int monthViewOffset ) {

		if ( isRunningAnimation() )
			return;

		initViewInfo();

		expandAnimator = ValueAnimator.ofFloat( fraction, 1 );
		expandAnimator.setDuration( duration );
		expandAnimator.setInterpolator( new AccelerateDecelerateInterpolator() );
		expandAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate( ValueAnimator animation ) {

				fraction = (float) animation.getAnimatedValue();
				updateView( monthViewOffset );

				if ( null != onAnimationUpdateListener )
					onAnimationUpdateListener.onUpdateAnimation( fraction );
			}

		} );
		expandAnimator.addListener( new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd( Animator animation ) {

				updateView( monthViewOffset );

				if ( null != onAnimationUpdateListener )
					onAnimationUpdateListener.onExpandCalendar();
			}

		} );
		expandAnimator.start();
	}

	public boolean setFraction( float fraction, int monthViewOffset ) {

		boolean updated = true;

		if ( 0f > fraction ) {

			fraction = 0;
			updated = false;
		}
		else if ( 1f < fraction ) {

			fraction = 1f;
			updated = false;
		}

		this.fraction = fraction;

		updateView( monthViewOffset );

		return updated;
	}

	public boolean isScrolling() {

		return scrolling;
	}

	public interface OnAnimationUpdateListener {

		public void onChangeViewMode( boolean expand );

		public void onUpdateAnimation( float fraction );

		public void onCollapseCalendar();

		public void onExpandCalendar();

	}

}
