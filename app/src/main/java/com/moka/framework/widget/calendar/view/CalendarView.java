package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.moka.framework.widget.calendar.adapter.CalendarViewAdapter;
import com.moka.framework.widget.calendar.adapter.DefaultCalendarViewAdapter;
import com.moka.framework.widget.calendar.adapter.MonthViewPagerAdapter;
import com.moka.framework.widget.calendar.adapter.WeekViewPagerAdapter;
import com.moka.framework.widget.calendar.model.CalendarCellData;
import com.moka.framework.widget.calendar.util.CalendarAnimator;
import com.moka.framework.widget.calendar.util.CalendarDataProvider;
import com.moka.framework.widget.calendar.util.CalendarUtil;
import com.moka.framework.widget.calendar.util.CalendarViewMode;
import com.moka.framework.widget.calendar.util.DateProvider;
import com.moka.framework.widget.calendar.util.DefaultDateProvider;
import com.nineoldandroids.view.ViewHelper;
import com.tous.application.R;
import com.tous.application.util.DateUtil;

import java.util.Calendar;
import java.util.Date;


public class CalendarView extends LinearLayout implements CalendarDataProvider, CalendarAnimator.OnAnimationUpdateListener, CalendarViewAdapter.OnDataChangeListener {

	private DateProvider dateProvider;
	private CalendarListener calendarListener;
	private CalendarViewAdapter calendarViewAdapter;

	private CalendarViewPager monthViewPager;
	private MonthViewPagerAdapter monthViewPagerAdapter;

	private CalendarViewPager weekViewPager;
	private WeekViewPagerAdapter weekViewPagerAdapter;

	private CalendarViewMode viewMode = CalendarViewMode.WEEK;
	private boolean scrollable = true;
	private CalendarAnimator calendarAnimator;

	private Calendar selectedCalendar = CalendarUtil.getCalendar( getTodayDate() );

	public CalendarView( Context context ) {

		this( context, null );
	}

	public CalendarView( Context context, AttributeSet attrs ) {

		super( context, attrs );
		bindView();
	}

	private void bindView() {

		inflate( getContext(), R.layout.view_calendar_new, this );

		monthViewPager = (CalendarViewPager) findViewById( R.id.monthViewPager );
		weekViewPager = (CalendarViewPager) findViewById( R.id.weekViewPager );

		FrameLayout frameLayout_calendarContainer = (FrameLayout) findViewById( R.id.frameLayout_calendarContainer );
		calendarAnimator = new CalendarAnimator( getContext(), frameLayout_calendarContainer, weekViewPager, monthViewPager );
		calendarAnimator.setOnAnimationUpdateListener( this );
	}

	/**
	 * CalendarDataProvider implementation
	 */

	@Override
	public Date getTodayDate() {

		if ( null == dateProvider )
			dateProvider = new DefaultDateProvider();

		return DateUtil.getTodayDate();
	}

	@Override
	public CalendarViewAdapter getCalendarViewAdapter() {

		return calendarViewAdapter;
	}

	/**
	 * animation
	 */

	@Override
	public void onChangeViewMode( boolean expand ) {

		if ( expand )
			setCalendarViewMode( CalendarViewMode.MONTH );
		else
			setCalendarViewMode( CalendarViewMode.WEEK );
	}

	@Override
	public void onUpdateAnimation( float fraction ) {

	}

	@Override
	public void onCollapseCalendar() {

		collapse( false );
	}

	@Override
	public void onExpandCalendar() {

	}

	/**
	 * touch event
	 */

	@SuppressWarnings( "SimplifiableIfStatement" )
	@Override
	public boolean onInterceptTouchEvent( MotionEvent event ) {

		int monthViewOffset = monthViewPagerAdapter.getSelectedRowDistance();
		if ( scrollable && calendarAnimator.onInterceptTouchEvent( event, monthViewOffset ) )
			return true;
		else
			return super.onInterceptTouchEvent( event );
	}

	@SuppressWarnings( "SimplifiableIfStatement" )
	@Override
	public boolean onTouchEvent( @NonNull MotionEvent event ) {

		int monthViewOffset = monthViewPagerAdapter.getSelectedRowDistance();
		if ( scrollable && calendarAnimator.onTouchEvent( event, monthViewOffset ) )
			return true;
		else
			return super.onTouchEvent( event );
	}

	public void setDateProvider( DateProvider dateProvider ) {

		if ( null != dateProvider )
			this.dateProvider = dateProvider;
		else
			this.dateProvider = new DefaultDateProvider();
	}

	public void setCalendarListener( CalendarListener calendarListener ) {

		this.calendarListener = calendarListener;
	}

	public void setShowDivider( boolean showDivider ) {

		monthViewPager.setShowDivider( showDivider );
		weekViewPager.setShowDivider( showDivider );
	}

	public void init( CalendarViewAdapter calendarViewAdapter ) {

		init( calendarViewAdapter, CalendarViewMode.WEEK );
	}

	public void init( CalendarViewAdapter calendarViewAdapter, CalendarViewMode viewMode ) {

		if ( null == calendarViewAdapter )
			calendarViewAdapter = new DefaultCalendarViewAdapter( getContext() );

		setCalendarViewAdapter( calendarViewAdapter );
		this.viewMode = viewMode;

		Date todayDate = getTodayDate();
		selectedCalendar.setTime( todayDate );

		monthViewPagerAdapter = new MonthViewPagerAdapter( getContext(), this );
		weekViewPagerAdapter = new WeekViewPagerAdapter( getContext(), this );

		monthViewPager.setOnPageChangedListener( onMonthPageChangedListener );
		int monthIndex = CalendarUtil.getMonthIndexFrom( todayDate );
		monthViewPager.init( CalendarViewPager.ViewPagerMode.MONTH, monthViewPagerAdapter, monthIndex );

		weekViewPager.setOnPageChangedListener( onWeekPageChangedListener );
		int weekIndex = CalendarUtil.getWeekIndexFrom( todayDate );
		weekViewPager.init( CalendarViewPager.ViewPagerMode.WEEK, weekViewPagerAdapter, weekIndex );

		post( new Runnable() {

			@Override
			public void run() {

				calendarAnimator.initViewInfo();

				if ( CalendarViewMode.MONTH == CalendarView.this.viewMode )
					expand( false );
				else
					collapse( false );

				selectedCalendar.setTime( getTodayDate() );
				monthViewPager.selectDate( selectedCalendar );
				weekViewPager.selectDate( selectedCalendar );
			}

		} );
	}

	private <DATA extends CalendarCellData, VIEW extends CalendarCellView<DATA>> void setCalendarViewAdapter(
			CalendarViewAdapter<DATA, VIEW> calendarViewAdapter ) {

		this.calendarViewAdapter = calendarViewAdapter;

		calendarViewAdapter.setOnDataChangeListener( this );
		calendarViewAdapter.setCalendarDataProvider( this );
		calendarViewAdapter.setOnCalendarCellViewListener(
				new CalendarCellView.OnCalendarCellViewListener<DATA, CalendarCellView<DATA>>() {

					@Override
					public void onClick( CalendarCellView<DATA> cellView ) {

						Date selectedDate = cellView.getData().getDate();
						selectedCalendar.setTime( selectedDate );

						monthViewPager.selectDate( selectedCalendar );
						weekViewPager.selectDate( selectedCalendar );

						notifyDateSelected( selectedDate );
					}

					@Override
					public boolean onLongClick( CalendarCellView<DATA> cellView ) {

						Date selectedDate = cellView.getData().getDate();
						selectedCalendar.setTime( selectedDate );

						monthViewPager.selectDate( selectedCalendar );
						weekViewPager.selectDate( selectedCalendar );

						notifyDateSelected( selectedDate );

						return true;
					}

				} );
	}

	@Override
	public void onDataChanged() {

		monthViewPager.changeData();
		weekViewPager.changeData();
	}

	@SuppressWarnings( "unchecked" )
	private void notifyDateSelected( Date date ) {

		if ( null != calendarListener ) {

			CalendarCellData calendarCellData = calendarViewAdapter.getCalendarCellData( DateUtil.formatToInt( date ) );
			calendarListener.onDateSelected( calendarCellData );
		}
	}

	private CalendarViewPager.OnPageChangedListener onMonthPageChangedListener = new CalendarViewPager.OnPageChangedListener() {

		@Override
		public void onPageChanged( Calendar calendar, boolean fromScroll ) {

			if ( CalendarViewMode.MONTH == viewMode ) {

				Date selectedDate = calendar.getTime();

				if ( fromScroll ) {

					selectedCalendar.setTime( selectedDate );
					notifyDateSelected( selectedDate );
				}

				weekViewPager.selectDate( selectedCalendar );
			}

			int year = selectedCalendar.get( Calendar.YEAR );
			int month = selectedCalendar.get( Calendar.MONTH );

			if ( null != calendarListener )
				calendarListener.onMonthChanged( year, month );
		}

	};

	private CalendarViewPager.OnPageChangedListener onWeekPageChangedListener = new CalendarViewPager.OnPageChangedListener() {

		@Override
		public void onPageChanged( Calendar calendar, boolean fromScroll ) {

			if ( CalendarViewMode.WEEK == viewMode ) {

				Date selectedDate = calendar.getTime();

				if ( fromScroll )
					selectedCalendar.setTime( selectedDate );

				monthViewPager.selectDate( selectedCalendar );
			}
		}

	};

	public void collapse( boolean animation ) {

		if ( animation ) {

			calendarAnimator.collapse( monthViewPagerAdapter.getSelectedRowDistance() );
		}
		else {

			calendarAnimator.setFraction( 0f, monthViewPagerAdapter.getSelectedRowDistance() );
			setCalendarViewMode( CalendarViewMode.WEEK );
		}
	}

	public void expand( boolean animation ) {

		setCalendarViewMode( CalendarViewMode.MONTH );

		if ( animation )
			calendarAnimator.expand( monthViewPagerAdapter.getSelectedRowDistance() );
		else
			calendarAnimator.setFraction( 1f, monthViewPagerAdapter.getSelectedRowDistance() );
	}

	private void setCalendarViewMode( CalendarViewMode calendarViewMode ) {

		viewMode = calendarViewMode;

		if ( CalendarViewMode.MONTH == calendarViewMode ) {

			ViewHelper.setAlpha( weekViewPager, 0 );
			ViewHelper.setAlpha( monthViewPager, 1 );
		}
		else {

			ViewHelper.setAlpha( monthViewPager, 0 );
			ViewHelper.setAlpha( weekViewPager, 1 );
		}
	}

	public void selectDate( Calendar calendar ) {

		selectDate( calendar.getTime() );
	}

	public void selectDate( Date date ) {

		selectedCalendar.setTime( date );

		monthViewPager.selectDate( selectedCalendar );
		weekViewPager.selectDate( selectedCalendar );
	}

	public void nextMonth() {

		addMonth( 1 );
	}

	public void previousMonth() {

		addMonth( -1 );
	}

	public void addMonth( int offset ) {

		if ( CalendarViewMode.MONTH == viewMode ) {

			int oldMonthIndex = monthViewPager.getCurrentItem();
			Calendar calendar = CalendarUtil.getCalendarFromMonthIndex( oldMonthIndex + offset );
			calendar.set( Calendar.DAY_OF_MONTH, 1 );

			selectDate( calendar );
		}
	}

	public void touchDown() {

		if ( scrollable )
			calendarAnimator.touchDownExternal();
	}

	public boolean touchMove( float touchDeltaY ) {

		if ( scrollable )
			return calendarAnimator
					.touchMoveExternal( touchDeltaY, monthViewPagerAdapter.getSelectedRowDistance() );
		else
			return false;
	}

	public void touchUp() {

		if ( scrollable )
			calendarAnimator.touchUpExternal( monthViewPagerAdapter.getSelectedRowDistance() );
	}

	public void setScrollable( boolean scrollable ) {

		this.scrollable = scrollable;
	}

	public interface CalendarListener<DATA extends CalendarCellData> {

		void onMonthChanged( int year, int month );

		void onDateSelected( DATA data );

	}

}
