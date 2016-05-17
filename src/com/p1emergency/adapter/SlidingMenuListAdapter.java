package com.p1emergency.adapter;

import com.p1emergency.root.R;

import java.util.List;

import com.p1emergency.model.CategoryViewHolder;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SlidingMenuListAdapter extends BaseAdapter {
	private CategoryViewHolder data = new CategoryViewHolder();
	private Context context;
	private String[] catergoryListData = null;
	private List<Drawable> drawableitems = null;

	public SlidingMenuListAdapter(Context context, String[] items,
			List<Drawable> drawablesList) {
		super();
		this.context = context;
		this.catergoryListData = items;
		this.drawableitems = drawablesList;
	}

	@Override
	public int getCount() {
		return catergoryListData.length;
	}

	@Override
	public Object getItem(int position) {
		return catergoryListData[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// // Disabling all items expect last one
	// @Override
	// public boolean isEnabled(int position) {
	// return (position == 8) ? true : false;
	// }
	private int mItemSelected = 0;

	public void setItemSelected(int position) {
		mItemSelected = position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.menu_listitem, parent, false);
		data.image = (ImageView) convertView.findViewById(R.id.menu_list_image);
		data.tv = (TextView) convertView.findViewById(R.id.menu_list_text);
		data.image.setImageDrawable((drawableitems.get(position)));
		data.tv.setText(catergoryListData[position]);
		if (mItemSelected == position) {
			convertView.setActivated(true);
		} else {
			convertView.setActivated(false);
		}
		return convertView;
	}
}
