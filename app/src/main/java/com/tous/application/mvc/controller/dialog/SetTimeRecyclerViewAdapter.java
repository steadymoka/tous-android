package com.tous.application.mvc.controller.dialog;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.moka.framework.widget.adapter.HeaderFooterRecyclerViewAdapter;
import com.tous.application.R;

import java.util.ArrayList;
import java.util.List;


public class SetTimeRecyclerViewAdapter extends HeaderFooterRecyclerViewAdapter {

	private Context context;
	private ArrayList<SetTimeItemData> items;
	private View headerView;
	private View footerView;

	private SetTimeItemView.OnItemClickListener onItemClickListener;

	public SetTimeRecyclerViewAdapter( Context context ) {

		this.context = context;
		items = new ArrayList<>();
	}

	@Override
	protected int getHeaderItemCount() {

		return ( null == headerView ) ? ( 0 ) : ( 1 );
	}

	@Override
	protected int getFooterItemCount() {

		return ( null == footerView ) ? ( 0 ) : ( 1 );
	}

	@Override
	protected int getContentItemCount() {

		return items.size();
	}

	@Override
	protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder( ViewGroup parent, int headerViewType ) {

		return new Header( headerView );
	}

	@Override
	protected RecyclerView.ViewHolder onCreateFooterItemViewHolder( ViewGroup parent, int footerViewType ) {

		return new Header( footerView );
	}

	@Override
	protected RecyclerView.ViewHolder onCreateContentItemViewHolder( ViewGroup parent, int contentViewType ) {

		View view = LayoutInflater.from( context ).inflate( R.layout.view_set_time_item, parent, false );
		SetTimeItemView setTimeItemView = new SetTimeItemView( context, view );
		setTimeItemView.setOnItemClickListener( onItemClickListener );

		return setTimeItemView;
	}

	@Override
	protected void onBindHeaderItemViewHolder( RecyclerView.ViewHolder headerViewHolder, int position ) {

	}

	@Override
	protected void onBindFooterItemViewHolder( RecyclerView.ViewHolder footerViewHolder, int position ) {

	}

	@Override
	protected void onBindContentItemViewHolder( RecyclerView.ViewHolder contentViewHolder, int position ) {

		SetTimeItemView setTimeItemView = (SetTimeItemView) contentViewHolder;
		setTimeItemView.setData( items.get( position ) );
	}

	public void setOnItemClickListener( SetTimeItemView.OnItemClickListener onItemClickListener ) {

		this.onItemClickListener = onItemClickListener;
	}

	public void setItems( List<SetTimeItemData> items ) {

		this.items.clear();

		if ( null != items )
			this.items.addAll( items );

		notifyDataSetChanged();
	}

	public void setHeaderView( View headerView ) {

		this.headerView = headerView;
		notifyDataSetChanged();
	}

	public void setFooterView( View footerView ) {

		this.footerView = footerView;
		notifyDataSetChanged();
	}

	private static class Header extends RecyclerView.ViewHolder {

		public Header( View itemView ) {

			super( itemView );
		}

	}

}
