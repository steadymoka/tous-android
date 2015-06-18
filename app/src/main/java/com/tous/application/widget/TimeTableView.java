//package com.tous.application.widget;
//
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Paint.Align;
//import android.graphics.Paint.Style;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.text.TextPaint;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.view.View;
//
//
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//
//public class TimeTableView extends View {
//
//	private RectF timeTableRect = new RectF();
//	private float pixelPerMinute = 0.0f;
//
//	private RectF timeRect = new RectF();
//	private Paint timePaint = new Paint();
//
//	private RectF pausedTimeRect = new RectF();
//	private Paint pausedTimePaint = new Paint();
//
//	private RectF timeLabelRect = new RectF();
//	private Paint timeLabelPaint = new Paint();
//
//	private Rect textBoundRect = new Rect();
//
//	private TextPaint titleTextPaint = new TextPaint();
//
//	private TextPaint doneTimeTextPaint = new TextPaint();
//
//	private RectF timeLabelTextRect = new RectF();
//	private TextPaint timeLabelTextPaint = new TextPaint();
//	private float timeLabelTextSize = 0;
//	private int timeLabelTextColor = 0xFF666666;
//
//	private Paint hourLinePaint = new Paint();
//	private int hourLineColor = 0xFFE6E6E6;
//
//	private GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
//
//	private int finishHour = 0;
//	private int finishMinute = 0;
//
////	private List<TaskDate> taskDates;
//
//	private ViewMode viewMode = ViewMode.TASK;
//
//	public TimeTableView( Context context ) {
//
//		this( context, null );
//	}
//
//	public TimeTableView( Context context, AttributeSet attrs ) {
//
//		super( context, attrs );
//		parseAttributes( attrs );
//		initView();
//	}
//
//	private void parseAttributes( AttributeSet attrs ) {
//
//		if ( null == attrs )
//			return;
//	}
//
//	private void initView() {
//
//		timePaint.setColor( 0xFFF2F2F2 );
//		timePaint.setAntiAlias( true );
//		timePaint.setStyle( Style.FILL );
//
//		pausedTimePaint.setAntiAlias( true );
//		pausedTimePaint.setColor( 0xFFFFFFFF );
//
//		timeLabelPaint.setAntiAlias( true );
//		timeLabelPaint.setStyle( Style.FILL );
//
//		titleTextPaint.setAntiAlias( true );
//		titleTextPaint.setTextAlign( Align.CENTER );
//		titleTextPaint.setTextSize( timeLabelTextSize );
//		titleTextPaint.setColor( 0xFF999999 );
//
//		doneTimeTextPaint.setAntiAlias( true );
//		doneTimeTextPaint.setTextAlign( Align.CENTER );
//		doneTimeTextPaint.setTextSize( timeLabelTextSize );
//		doneTimeTextPaint.setColor( 0xFFCCCCCC );
//
//		timeLabelTextPaint.setAntiAlias( true );
//		timeLabelTextPaint.setTextAlign( Align.CENTER );
//		timeLabelTextPaint.setTextSize( timeLabelTextSize );
//		timeLabelTextPaint.setColor( timeLabelTextColor );
//
//		hourLinePaint.setAntiAlias( true );
//		hourLinePaint.setStrokeWidth( 1 );
//		hourLinePaint.setColor( hourLineColor );
//	}
//
//	@Override
//	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
//
//		setMeasuredDimension( MeasureSpec.getSize( widthMeasureSpec ), (int) dipToPixel( getContext(), 1000 ) );
//	}
//
//	@Override
//	protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
//
//		timeLabelTextRect.left = getPaddingLeft();
//		timeLabelTextRect.right = w * ( 1.0f / 4.0f );
//		timeLabelTextRect.top = (float) ( getPaddingTop() + 1 + dipToPixel( getContext(), 10 ) );
//		timeLabelTextRect.bottom = (float) ( h - getPaddingBottom() - 1 - dipToPixel( getContext(), 10 ) );
//
//		timeTableRect.left = timeLabelTextRect.right + 1;
//		timeTableRect.right = w - getPaddingRight();
//		timeTableRect.top = timeLabelTextRect.top;
//		timeTableRect.bottom = timeLabelTextRect.bottom;
//
//		timeRect.left = timeTableRect.left;
//		timeRect.right = timeTableRect.right;
//
//		pausedTimeRect.left = timeRect.left;
//		pausedTimeRect.right = timeRect.right;
//
//		timeLabelRect.left = timeTableRect.left;
//		timeLabelRect.right = (float) ( timeTableRect.left + dipToPixel( getContext(), 5 ) );
//
//		pixelPerMinute = timeTableRect.height() / ( 24.0f * 60.0f );
//
//		timeLabelTextSize = pixelPerMinute * 15;
//
//		titleTextPaint.setTextSize( timeLabelTextSize );
//		doneTimeTextPaint.setTextSize( timeLabelTextSize );
//		timeLabelTextPaint.setTextSize( timeLabelTextSize );
//
//		super.onSizeChanged( w, h, oldw, oldh );
//	}
//
//	@Override
//	protected void onDraw( Canvas canvas ) {
//
//		// TODO
//
//		drawTimeTable( canvas );
//		drawHourLine( canvas );
//		drawTimeLabelText( canvas );
//	}
//
//	private void drawTimeTable( Canvas canvas ) {
//
//		if ( null != taskDates ) {
//
//			for ( TaskDate taskDate : taskDates ) {
//
//				int color;
//				if ( ViewMode.TASK == viewMode ) {
//
//					color = taskDate.getColor();
//				}
//				else {
//
//					Category category = taskDate.getTask().getCategory();
//					if ( null != category )
//						color = category.getFakeColorIfDefault();
//					else
//						color = 0xFFC1C1C1;
//				}
//				ArrayList<TimeHistory> savedTimeHistories = taskDate.getSavedTimeHistories();
//
//				for ( TimeHistory timeHistory : savedTimeHistories ) {
//
//					float top = getYCoordinate( timeHistory.getStartedAt() );
//					float bottom = getYCoordinate( timeHistory.getEndedAt() );
//
//					drawTimeTableRect( canvas, color, top, bottom );
//					drawTimeLabel( canvas, color, top, bottom );
//
//					if ( ViewMode.TASK == viewMode ) {
//
//						drawTitleAndTime( canvas, top, bottom, taskDate.getTask().getName(), timeHistory );
//					}
//					else {
//
//						String title;
//						Category category = taskDate.getTask().getCategory();
//						if ( null != category )
//							title = category.getFakeNameIfDefault( getContext() );
//						else
//							title = getContext().getString( R.string.default_category_name );
//
//						drawTitleAndTime( canvas, top, bottom, title, timeHistory );
//					}
//				}
//			}
//		}
//	}
//
//	private float getYCoordinate( long timeInMillis ) {
//
//		calendar.setTimeInMillis( timeInMillis * 1000 );
//		int startedHour = calendar.get( GregorianCalendar.HOUR_OF_DAY );
//		int startedMinute = calendar.get( GregorianCalendar.MINUTE );
//
//		return computeLogYCoordinate( startedHour, startedMinute );
//	}
//
//	private float computeLogYCoordinate( int hour, int minute ) {
//
//		int minutesOfDay = hour * 60 + minute;
//		int revisedHour = minutesOfDay + getLogFinishRevisionMinutesOfDay( finishHour, finishMinute );
//
//		return timeTableRect.top + revisedHour * pixelPerMinute;
//	}
//
//	private int getLogFinishRevisionMinutesOfDay( int finishHour, int finishMinute ) {
//
//		if ( 0 <= finishHour && 12 > finishHour )
//			return -( finishHour * 60 + finishMinute );
//		else
//			return ( 24 * 60 - ( finishHour * 60 + finishMinute ) );
//	}
//
//	private void drawTimeTableRect( Canvas canvas, int color, float top, float bottom ) {
//
//		timeRect.top = top;
//		timeRect.bottom = bottom;
//
//		canvas.drawRect( timeRect, timePaint );
//	}
//
//	private void drawPausedTimeRect( Canvas canvas, ArrayList<TimeHistory> pausedTimeHistories ) {
//
//		if ( null != pausedTimeHistories && 0 < pausedTimeHistories.size() ) {
//
//			for ( TimeHistory pausedTimeHistory : pausedTimeHistories ) {
//
//				float top = getYCoordinate( pausedTimeHistory.getStartedAt() );
//				float bottom = getYCoordinate( pausedTimeHistory.getEndedAt() );
//
//				drawPausedTimeRect( canvas, top, bottom );
//			}
//		}
//	}
//
//	private void drawPausedTimeRect( Canvas canvas, float top, float bottom ) {
//
//		pausedTimeRect.top = top;
//		pausedTimeRect.bottom = bottom;
//
//		canvas.drawRect( pausedTimeRect, pausedTimePaint );
//	}
//
//	private void drawTimeLabel( Canvas canvas, int color, float top, float bottom ) {
//
//		timeLabelRect.top = top;
//		timeLabelRect.bottom = bottom;
//
//		timeLabelPaint.setColor( color );
//
//		canvas.drawRect( timeLabelRect, timeLabelPaint );
//	}
//
//	private void drawTitleAndTime( Canvas canvas, float top, float bottom, String taskTitle, TimeHistory timeHistory ) {
//
//		titleTextPaint.getTextBounds( taskTitle, 0, taskTitle.length(), textBoundRect );
//		int titleTextWidth = textBoundRect.width();
//		int titleTextHeight = textBoundRect.height();
//
//		int doneTime = (int) ( timeHistory.getDoneMillis() / 1000 );
//		int hour = doneTime / 3600;
//		int minute = ( doneTime % 3600 ) / 60;
//		String doneTimeString = String.format( getContext().getString( R.string.view_time_table_text_format_done_time ), hour, minute );
//		doneTimeTextPaint.getTextBounds( doneTimeString, 0, doneTimeString.length(), textBoundRect );
//		int doneTimeTextWidth = textBoundRect.width();
//		int doneTimeTextHeight = textBoundRect.height();
//
//		int width = (int) ( timeRect.width() - timeLabelRect.width() * 3 );
//		int height = (int) ( bottom - top );
//		float centerY = ( top + bottom ) / 2;
//
//		if ( titleTextHeight + titleTextHeight * 0.5f + doneTimeTextHeight + titleTextHeight * 0.5f < height ) {
//
//			float revisionX = titleTextWidth / 2.0f + timeLabelRect.width() * 2;
//			float textX = ( width > titleTextWidth ) ? ( timeRect.right - revisionX ) : ( timeRect.right - revisionX + ( titleTextWidth - width ) );
//			float textY = centerY - titleTextHeight * 0.5f;
//			canvas.drawText( taskTitle, textX, textY, titleTextPaint );
//
//			revisionX = doneTimeTextWidth / 2.0f + timeLabelRect.width() * 2;
//			textX = ( width > doneTimeTextWidth ) ? ( timeRect.right - revisionX ) : ( timeRect.right - revisionX + ( titleTextWidth - width ) );
//			textY = centerY + doneTimeTextHeight * 1.2f;
//			canvas.drawText( doneTimeString, textX, textY, doneTimeTextPaint );
//		}
//		else if ( titleTextHeight + titleTextHeight * 0.2f < height ) {
//
//			float revisionX = titleTextWidth / 2.0f + timeLabelRect.width() * 2;
//			float textX = ( width > titleTextWidth ) ? ( timeRect.right - revisionX ) : ( timeRect.right - revisionX + ( titleTextWidth - width ) );
//			float textY = centerY + titleTextHeight * 0.5f;
//			canvas.drawText( taskTitle, textX, textY, titleTextPaint );
//		}
//	}
//
//	private void drawHourLine( Canvas canvas ) {
//
//		for ( int i = finishHour; i < finishHour + 25; i++ ) {
//
//			float y = computeYCoordinate( i, 0 );
//
//			if ( timeTableRect.top <= y )
//				canvas.drawLine( timeTableRect.left, y, timeTableRect.right, y, hourLinePaint );
//		}
//	}
//
//	private float computeYCoordinate( int hour, int minute ) {
//
//		int minutesOfDay = hour * 60 + minute;
//		int revisedHour = minutesOfDay + getFinishRevisionMinutesOfDay( finishHour, finishMinute );
//
//		return timeTableRect.top + revisedHour * pixelPerMinute;
//	}
//
//	private int getFinishRevisionMinutesOfDay( int finishHour, int finishMinute ) {
//
//		return -( finishHour * 60 + finishMinute );
//	}
//
//	private void drawTimeLabelText( Canvas canvas ) {
//
//		float x = ( timeLabelTextRect.left + timeLabelTextRect.right ) / 2;
//
//		for ( int i = finishHour; i < finishHour + 25; i++ ) {
//
//			int hour = i % 12;
//			hour = ( 0 == hour ) ? ( 12 ) : ( hour );
//
//			String amPmMarker;
//			if ( ( 0 <= i && 12 >= i ) || ( 24 <= i && 36 >= i ) )
//				amPmMarker = "am";
//			else
//				amPmMarker = "pm";
//
//			String hourString = String.format( "%02d %s", hour, amPmMarker );
//			timeLabelTextPaint.getTextBounds( ( hourString ), 0, ( hourString ).length(), textBoundRect );
//
//			float y = computeYCoordinate( i, 0 );
//
//			if ( timeTableRect.top <= y ) {
//
//				y += textBoundRect.height() / 2;
//				canvas.drawText( hourString, x, y, timeLabelTextPaint );
//			}
//		}
//	}
//
//	public int getFinishHour() {
//
//		return finishHour;
//	}
//
//	public int getFinishMinute() {
//
//		return finishMinute;
//	}
//
//	public void setDailyFinishTime( String dailyFinishTime ) {
//
//		String[] finishTime = dailyFinishTime.split( ":" );
//		finishHour = Integer.parseInt( finishTime[0] );
//		finishMinute = Integer.parseInt( finishTime[1] );
//
//		invalidate();
//	}
//
//	public void setDailyTimeTableData( List<TaskDate> taskDates ) {
//
//		this.taskDates = taskDates;
//		invalidate();
//	}
//
//	public void setViewMode( ViewMode viewMode ) {
//
//		this.viewMode = viewMode;
//		invalidate();
//	}
//
//	private static double dipToPixel( Context context, double dip ) {
//
//		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//		return dip * ( displayMetrics.densityDpi / 160.0 );
//	}
//
//	public enum ViewMode {
//
//		TASK, CATEGORY
//
//	}
//
//}
