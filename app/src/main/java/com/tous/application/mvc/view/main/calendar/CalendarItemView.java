package com.tous.application.mvc.view.main.calendar;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.moka.framework.util.ScreenUtil;
import com.moka.framework.widget.calendar.view.CalendarCellView;
import com.tous.application.mvc.controller.activity.main.calendar.CalendarItemData;

import java.util.Calendar;


public class CalendarItemView extends CalendarCellView<CalendarItemData> implements CalendarItemData.Listener {

	private static final int OTHER_MONTH_COLOR = 0x20666666;
	private static final int UNSELECTED_COLOR = 0x50666666;
	private static final int SELECTED_COLOR = 0xFF666666;
	private static final int OTHER_MONTH_TODAY_COLOR = 0x20FF3971;
	private static final int UNSELECTED_TODAY_COLOR = 0x50FF3971;
	private static final int SELECTED_TODAY_COLOR = 0xFFFF3971;
	private static final int UNSELECTED_PLANNING_DAY_COLOR = 0xFFAEC5ED;
	private static final int SELECTED_PLANNING_DAY_COLOR = 0xFF0043EA;

	private static Handler handler;

	private int padding;

	private RectF labelRect;
	private float labelHeight;
	private Paint backgroundPaint;

	private RectF dateRect;
	private TextPaint dateTextPaint;
	private Rect textBound;

	private String dateString = "";
	private int countOfOnDay = 0;

	private boolean needRefreshView = false;

	public CalendarItemView( Context context ) {

		super( context );
		initView( context );
	}

	public CalendarItemView( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView( context );
	}

	private void initView( Context context ) {

		padding = (int) ScreenUtil.dipToPixel( context, 6 );

		labelRect = new RectF();

		labelHeight = padding / 2.0f;

		backgroundPaint = new Paint();
		backgroundPaint.setColor( 0x20000000 );

		dateRect = new RectF();

		dateTextPaint = new TextPaint();
		dateTextPaint.setAntiAlias( true );
		dateTextPaint.setTextAlign( Paint.Align.CENTER );

		textBound = new Rect();
	}

	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh ) {

		labelRect.left = padding;
		labelRect.right = w - padding;
		labelRect.bottom = h - padding;
		labelRect.top = labelRect.bottom - labelHeight;

		dateRect.left = padding;
		dateRect.right = w - padding;
		dateRect.top = padding;
		dateRect.bottom = labelRect.top - 1;

		dateTextPaint.setTextSize( w / 2.3f );
		dateTextPaint.getTextBounds( "1234567890", 0, "1234567890".length(), textBound );
	}

	@Override
	protected void dispatchDraw( @NonNull Canvas canvas ) {

		super.dispatchDraw( canvas );

		if ( needRefreshView ) {

			if ( null == handler )
				handler = new Handler();

			getData().makeData( handler, this );
		}

		drawText( canvas );
//		drawLabel( canvas );
	}

	@Override
	public void onDataMade( final CalendarItemData calendarItemData ) {

		setLabel( calendarItemData );
		needRefreshView = false;
		invalidate();
	}

	private void setDate( CalendarItemData data ) {

		if ( isBelongToMonthView() )
			setTextColorBelongToMonthView( data );
		else
			setTextColorBelongToWeekView( data );

		dateString = String.valueOf( data.getDayOfMonth() );
	}

	private void setTextColorBelongToMonthView( CalendarItemData data ) {

		int textColor;

		if ( data.isSelected() ) {

			if ( data.isToday() )
				textColor = SELECTED_TODAY_COLOR;
			else if ( data.isPlanningDay() )
				textColor = SELECTED_PLANNING_DAY_COLOR;
			else
				textColor = SELECTED_COLOR;
		}
		else {

			if ( data.isToday() )
				textColor = UNSELECTED_TODAY_COLOR;
			else if ( data.isPlanningDay() )
				textColor = UNSELECTED_PLANNING_DAY_COLOR;
			else
				textColor = UNSELECTED_COLOR;
		}

		dateTextPaint.setColor( textColor );

//		int textColor;
//
//		if ( data.isSelected() ) {
//
//			if ( data.isToday() )
//				textColor = SELECTED_TODAY_COLOR;
//			else
//				textColor = SELECTED_COLOR;
//		}
//		else {
//
//			if ( data.isCurrentMonth() ) {
//
//				if ( data.isToday() )
//					textColor = UNSELECTED_TODAY_COLOR;
//				else
//					textColor = UNSELECTED_COLOR;
//			}
//			else {
//
//				if ( data.isToday() )
//					textColor = OTHER_MONTH_TODAY_COLOR;
//				else
//					textColor = OTHER_MONTH_COLOR;
//			}
//		}

		dateTextPaint.setColor( textColor );
	}

	private void setTextColorBelongToWeekView( CalendarItemData data ) {

		int textColor;

		if ( data.isSelected() ) {

			if ( data.isToday() )
				textColor = SELECTED_TODAY_COLOR;
			else if ( data.isPlanningDay() )
				textColor = SELECTED_PLANNING_DAY_COLOR;
			else
				textColor = SELECTED_COLOR;
		}
		else {

			if ( data.isToday() )
				textColor = UNSELECTED_TODAY_COLOR;
			else if ( data.isPlanningDay() )
				textColor = UNSELECTED_PLANNING_DAY_COLOR;
			else
				textColor = UNSELECTED_COLOR;
		}

		dateTextPaint.setColor( textColor );
	}

	private void setLabel( CalendarItemData data ) {

	}

	private void drawText( Canvas canvas ) {

		canvas.save();
		canvas.translate( dateRect.left, dateRect.top );

		float x = dateRect.width() / 2;
		float y = dateRect.height() / 2 + textBound.height() / 2;
		canvas.drawText( dateString, x, y, dateTextPaint );

		canvas.restore();
	}

	private void drawLabel( Canvas canvas ) {

		canvas.save();
		canvas.translate( labelRect.left, labelRect.top );

		if ( 0 < countOfOnDay ) {

			float width = labelRect.width();
			float height = labelRect.height();
			canvas.drawRect( 0, 0, width, height, backgroundPaint );
		}

		canvas.restore();
	}

	@Override
	protected void refreshView( CalendarItemData data ) {

		needRefreshView = true;
		setDate( data );
		invalidate();
	}

}
