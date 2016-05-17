package com.p1emergency.fragment;

import com.p1emergency.root.R;

import java.util.ArrayList;
import java.util.List;

import com.p1emergency.activity.POEInfoCenterActivity;
import com.p1emergency.adapter.EMHAdapter;
import com.p1emergency.adapter.SeparatedListAdapter;
import com.p1emergency.fragmentsupport.AbstractBaseFragment;
import com.p1emergency.model.EMH;
import com.p1emergency.view.SectionedListView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class EmergencyCareTabFragment extends AbstractBaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.emergency_care_tab_fragment, null,
				false);
		return setupViews(view);
	}

	@Override
	public void setActionBarTitle() {
		setActionBarTitle(R.string.action_bar_title_appointments);
	}

	private View setupViews(View container) {
		//Setting up emergency call text
		/*SpannableString text = styleText();
		((TextView) container.findViewById(R.id.textView4)).setText(text,
				BufferType.SPANNABLE);*/
		// Creating our separated list adapter
		SeparatedListAdapter sepListAdapter = new SeparatedListAdapter(
				getActivity(), R.layout.sectioned_list_header,
				SeparatedListAdapter.ENABLE_ALL_ROWS);

		EMHAdapter adapter1 = new EMHAdapter(getActivity(),
				getInfo("6565 Fannin Street\nHouston, TX 77030-2707\n(713) 790Ð3311","Distance : 1.07 Miles",null,null,null,true));
		sepListAdapter.addSection("Houston Methodist Hospital", adapter1);

		EMHAdapter adapter2 = new EMHAdapter(getActivity(),
				getInfo("1515 Holcombe Boulevard, Box 91\nHouston, TX 77030-4000\n(713) 792Ð2121","Distance : 2.27 Miles",null,null,null,true));
		sepListAdapter.addSection("MD Anderson Cancer Center, University of Texas", adapter2);
		
		EMHAdapter adapter3 = new EMHAdapter(getActivity(),
				getInfo("7777 Forest Lane\nDallas, TX 75230-2598\n(972) 566Ð7000","Distance : 9.00 Miles",null,null,null,true));
		sepListAdapter.addSection("Medical City Dallas Hospital", adapter3);
		
		EMHAdapter adapter4 = new EMHAdapter(getActivity(),
				getInfo("5323 Harry Hines Boulevard\nDallas, TX 75390-9265\n(214) 645Ð5555","Distance : 11.75 Miles",null,null,null,true));
		sepListAdapter.addSection("UT Southwestern Medical Center", adapter4);

		// Setting the sectioned list adapter to a custom list view
		SectionedListView list = (SectionedListView) container
				.findViewById(R.id.customlist);
		list.setAdapter(sepListAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						POEInfoCenterActivity.class);
				EMH temp = (EMH) parent.getItemAtPosition(position);
				intent.putExtra("Title", temp.info1);
				startActivity(intent);
			}
		});

		return container;
	}

	private SpannableString styleText() {
		SpannableString text = new SpannableString("Call Emergency");
		text.setSpan(new ForegroundColorSpan(Color.parseColor("#E06666")), 0,
				14, 0);
		text.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
		text.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 4, 14, 0);
		return text;
	}

	private List<EMH> getInfo(String str1, String str2, String str3, String str4, Float rating, Boolean checkBox) {
		List<EMH> l = new ArrayList<EMH>();
		//To add more rows. add another EMH object
		l.add(new EMH(str1, str2, str3, str4, rating, checkBox));
		return l;
	}
	

}
