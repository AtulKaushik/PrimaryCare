package com.p1emergency.adapter;

import com.p1emergency.root.R;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class SeparatedListAdapter extends BaseAdapter {

	public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
	public final ArrayAdapter<String> headers;
	public final static int TYPE_SECTION_HEADER = 0;
	private ViewGroup mViewGroup;
	private int mSectionStart = 0;
	private int mSectionEnd;
	private int mSectionNum = 0;
	private int mNotHeaderValue = 1000000;
	public static final int ENABLE_ALL_ROWS = -1;
	public static final int DISABLE_ALL_ROWS = -2;
	private int rowToEnable;

	/**
	 * @param context
	 *            Context
	 * @param layoutId
	 *            Layout id for the header
	 */
	public SeparatedListAdapter(Context context, int layoutId, int rowToEnable) {
		headers = new ArrayAdapter<String>(context, layoutId);
		this.rowToEnable = rowToEnable;
	}

	/**
	 * @param sectionTitle
	 *            Title for the section
	 * @param adapter
	 *            List adapter
	 */
	public void addSection(String sectionTitle, Adapter adapter) {
		this.headers.add(sectionTitle);
		this.sections.put(sectionTitle, adapter);
	}

	public Object getItem(int position) {
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0)
				return section;
			if (position < size)
				return adapter.getItem(position - 1);

			// otherwise jump into next section
			position -= size;
		}
		return null;
	}

	public int getCount() {
		// total together all sections, plus one for each section header
		int total = 0;
		for (Adapter adapter : this.sections.values())
			total += adapter.getCount() + 1;
		return total;
	}

	public int getViewTypeCount() {
		// assume that headers count as one, then total all sections
		int total = 1;
		for (Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		return total;
	}

	public int getItemViewType(int position) {
		int type = 1;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0)
				return TYPE_SECTION_HEADER;
			if (position < size)
				return type + adapter.getItemViewType(position - 1);

			// otherwise jump into next section
			position -= size;
			type += adapter.getViewTypeCount();
		}
		return -1;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isEnabled(int position) {
		Log.i(SeparatedListAdapter.class.getSimpleName(), "Position: " + position);
		if (getItemViewType(position) == TYPE_SECTION_HEADER)
			return false;
		else if (position == rowToEnable)
			return true;
		else if (rowToEnable == ENABLE_ALL_ROWS) // All rows
			return true;
		else
			return false;
		// return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	public Integer getAdapterViewCount(int sectionNum) {
		Adapter adapter = sections.get(headers.getItem(sectionNum));
		return adapter.getCount();
	}

	/*
	 * Maintaing three variable i.e
	 * 
	 * mSectionNum mSectionStart mSectionEnd
	 */
	public Integer getSectionNumber(int position) {
		if (mSectionNum == 0) {
			mSectionEnd = getAdapterViewCount(mSectionNum);
			if (position > mSectionEnd) {
				mSectionNum = mSectionNum + 1;
				mSectionStart = position;
				mSectionEnd = position + getAdapterViewCount(mSectionNum);
			}
			return mSectionNum;
		} else {
			if (position >= mSectionStart && position <= mSectionEnd) {
				return mSectionNum;
			} else if (position > mSectionEnd) {
				mSectionNum = mSectionNum + 1;
				mSectionStart = position;
				mSectionEnd = position + getAdapterViewCount(mSectionNum);
				return mSectionNum;
			} else if (position < mSectionStart) {
				// if scroll is reverse this condition apply
				mSectionNum = mSectionNum - 1;
				mSectionStart = Math.abs(position
						- getAdapterViewCount(mSectionNum));
				mSectionEnd = position;
				return mSectionNum;
			}
		}

		return mNotHeaderValue;
	}

	public View getHeaderView(int mSectionNum, View convertView) {
		return headers.getView(mSectionNum, convertView, mViewGroup);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionNum = 0;
		this.mViewGroup = parent;

		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0) {
				// If position is 0 then set view as header view
				View v = headers.getView(sectionNum, convertView, parent);
				v.setId(R.id.header_view);
				return v;
			}
			if (position < size)
				return adapter.getView(position - 1, convertView, parent);

			// otherwise jump into next section
			position -= size;
			sectionNum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
