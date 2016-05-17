package com.p1emergency.adapter;

import com.p1emergency.root.R;

import java.util.List;

import com.p1emergency.model.EMH;
import com.p1emergency.model.ViewHolder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * Adapter for the objects of type <EMH>
 * 
 */
public class EMHAdapter extends BaseAdapter {
	private ViewHolder data = new ViewHolder();
	private Context mContext;
	List<EMH> hospital_list = null;

	public EMHAdapter(Context ctx, List<EMH> dataList) {

		mContext = ctx;
		this.hospital_list = dataList;
		data.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return hospital_list.size();
	}

	@Override
	public Object getItem(int position) {
		return hospital_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = data.inflater.inflate(
					R.layout.sectioned_list_item_detailed, null);
			holder = new ViewHolder();
			holder.textview1 = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.textview2 = (TextView) convertView
					.findViewById(R.id.textView2);
			holder.textview3 = (TextView) convertView
					.findViewById(R.id.textView3);
			holder.textview4 = (TextView) convertView
					.findViewById(R.id.textView4);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.hospital_rating);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
			
		EMH hospital = hospital_list.get(position);
		holder.textview1.setText(hospital.info1);
		//holder.textview2.setText(hospital.info2);
		//holder.textview3.setText(hospital.info3);
		//holder.textview4.setText(hospital.info4);
		//holder.ratingBar.setRating(hospital.info5);
		
		if (hospital.info2 != null) {
			holder.textview2.setVisibility(View.VISIBLE);
			holder.textview2.setText(hospital.info2);
		} else {
			holder.textview2.setVisibility(View.GONE);
		}
		
		if (hospital.info3 != null) {
			holder.textview3.setVisibility(View.VISIBLE);
			holder.textview3.setText(hospital.info3);
		} else {
			holder.textview3.setVisibility(View.GONE);
		}
		
		if (hospital.info4 != null) {
			holder.textview4.setVisibility(View.VISIBLE);
			holder.textview4.setText(hospital.info4);
		} else {
			holder.textview4.setVisibility(View.GONE);
		}
		
		if (hospital.info5 != null) {
			holder.ratingBar.setVisibility(View.VISIBLE);
			holder.ratingBar.setRating(hospital.info5);
		} else {
			holder.ratingBar.setVisibility(View.GONE);
		}
		
		if (hospital.info6 != null) {
			holder.checkBox.setVisibility(View.VISIBLE);
		} else {
			holder.checkBox.setVisibility(View.GONE);
		}
			
		return convertView;
	}
}
