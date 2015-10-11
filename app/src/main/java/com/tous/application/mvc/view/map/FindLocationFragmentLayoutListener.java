package com.tous.application.mvc.view.map;


import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.moka.framework.view.LayoutListener;
import com.tous.application.mvc.controller.activity.map.PlaceAutocompleteAdapter;


public interface FindLocationFragmentLayoutListener extends LayoutListener {

	AdapterView.OnItemClickListener getAutocompleteClickListener();

	PlaceAutocompleteAdapter getAdapter();

}
