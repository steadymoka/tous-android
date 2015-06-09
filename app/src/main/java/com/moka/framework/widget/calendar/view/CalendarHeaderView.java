package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


public class CalendarHeaderView extends View {

	private TextPaint sundayPaint;
	private TextPaint weekdayPaint;
	private TextPaint saturdayPaint;

	private Rect textBoundRect;

	private float sunX;
	private float monX;
	private float tueX;
	private float wedX;
	private float thuX;
	private float friX;
	private float satX;

	private float dayWidth;
	private float dayY;

	public CalendarHeaderView( Context context ) {

		this( context, null );
	}

	public CalendarHeaderView( Context context, AttributeSet attrs ) {

		super( context, attrs );
		initView();
	}

	private void initView() {

		textBoundRect = new Rect();

		sundayPaint = new TextPaint();
		sundayPaint.setTextAlign( Paint.Align.CENTER );
		sundayPaint.setAntiAlias( true );
		sundayPaint.setColor( 0xFFFF005C );
		sundayPaint.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );

		weekdayPaint = new TextPaint();
		weekdayPaint.setTextAlign( Paint.Align.CENTER );
		weekdayPaint.setAntiAlias( true );
		weekdayPaint.setColor( 0xFF666666 );
		weekdayPaint.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );

		saturdayPaint = new TextPaint();
		saturdayPaint.setTextAlign( Paint.Align.CENTER );
		saturdayPaint.setAntiAlias( true );
		saturdayPaint.setColor( 0xFF0069D1 );
		saturdayPaint.setTypeface( Typeface.create( Typeface.DEFAULT, Typeface.BOLD ) );
	}

	@Override
	protected void onSizeChanged( int w, int h, int oldw, int oldh ) {

		dayWidth = w / 7.0f;
		dayY = h / 2.0f;

		sunX = dayWidth / 2;
		monX = sunX + dayWidth;
		tueX = monX + dayWidth;
		wedX = tueX + dayWidth;
		thuX = wedX + dayWidth;
		friX = thuX + dayWidth;
		satX = friX + dayWidth;

		float textSize = h / 2.0f;
		sundayPaint.setTextSize( textSize );
		weekdayPaint.setTextSize( textSize );
		saturdayPaint.setTextSize( textSize );
	}

	@Override
	protected void onDraw( Canvas canvas ) {

		drawText( canvas, "SUN", sundayPaint, sunX, dayY );
		drawText( canvas, "MON", weekdayPaint, monX, dayY );
		drawText( canvas, "TUE", weekdayPaint, tueX, dayY );
		drawText( canvas, "WED", weekdayPaint, wedX, dayY );
		drawText( canvas, "THU", weekdayPaint, thuX, dayY );
		drawText( canvas, "FRI", weekdayPaint, friX, dayY );
		drawText( canvas, "SAT", saturdayPaint, satX, dayY );
	}

	private void drawText( Canvas canvas, String text, TextPaint textPaint, float centerX, float centerY ) {

		textPaint.getTextBounds( text, 0, text.length(), textBoundRect );
		canvas.drawText( text, centerX, (int) ( centerY + textBoundRect.height() / 2 ), textPaint );
	}

}
