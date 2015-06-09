package com.moka.framework.widget.calendar.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.moka.framework.widget.calendar.model.DefaultCalendarCellData;
import com.tous.application.R;


public class DefaultCalendarCellView extends CalendarCellView<DefaultCalendarCellData> {

	private TextView textView_day;

	public DefaultCalendarCellView( Context context ) {

		super( context );
	}

	public DefaultCalendarCellView( Context context, AttributeSet attrs ) {

		super( context, attrs );
	}

	@Override
	protected int getCellViewResId() {

		return R.layout.view_default_calendar_cell;
	}

	@Override
	protected void onLayoutInflated() {

		textView_day = (TextView) findViewById( R.id.textView_day );
	}

	@Override
	protected void refreshView( DefaultCalendarCellData data ) {

		if ( data.isSelected() ) {

			textView_day.setTextColor( 0xFF666666 );
		}
		else {

			if ( isBelongToMonthView() ) {

				if ( data.isCurrentMonth() ) {

					if ( data.isToday() )
						textView_day.setTextColor( 0xFFFF3971 );
					else
						textView_day.setTextColor( 0xFFB3B3B3 );
				}
				else {

					if ( data.isToday() )
						textView_day.setTextColor( 0x80FF3971 );
					else
						textView_day.setTextColor( 0x80B3B3B3 );
				}
			}
			else {

				if ( data.isToday() )
					textView_day.setTextColor( 0xFFFF3971 );
				else
					textView_day.setTextColor( 0xFFB3B3B3 );
			}
		}

		textView_day.setText( String.format( "%d", data.getDayOfMonth() ) );
	}

}
