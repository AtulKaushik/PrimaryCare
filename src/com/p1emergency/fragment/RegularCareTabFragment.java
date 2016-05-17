package com.p1emergency.fragment;

import com.p1emergency.root.R;

import java.util.ArrayList;
import java.util.List;

import com.p1emergency.adapter.EMHAdapter;
import com.p1emergency.adapter.SeparatedListAdapter;
import com.p1emergency.fragmentsupport.AbstractBaseFragment;
import com.p1emergency.model.EMH;
import com.p1emergency.view.SectionedListView;


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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;

public class RegularCareTabFragment extends AbstractBaseFragment {

	Spinner dropdown_category_list;
	Spinner dropdown;
	SeparatedListAdapter sepListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		return setupViews(inflater.inflate(R.layout.regular_care_tab_fragment, null,
				false));
	}

	private View setupViews(View container) {
		
		//Setting up the category list
		dropdown_category_list = (Spinner)container.findViewById(R.id.spinner_regular_care_category_list);
		String[] categories = getResources().getStringArray(R.array.sort_categories);
		ArrayAdapter<String> adapter_category_list = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
		dropdown_category_list.setAdapter(adapter_category_list);
		
		
		//Setting up the category preferences
		dropdown = (Spinner)container.findViewById(R.id.spinner_regular_care_category);
		String[] items = getResources().getStringArray(R.array.specialties);;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
		dropdown.setAdapter(adapter);
		dropdown.setVisibility(View.GONE);
		
		SpannableString text = styleText();
		((TextView) container.findViewById(R.id.textView4)).setText(text,
				BufferType.SPANNABLE);

		// Creating our separated list adapter
		sepListAdapter = new SeparatedListAdapter(
				getActivity(), R.layout.sectioned_list_header,
				SeparatedListAdapter.DISABLE_ALL_ROWS);

		EMHAdapter adapter1 = new EMHAdapter(getActivity(),
				getCityHospital_Info1(dropdown_category_list.getSelectedItem()));
		sepListAdapter.addSection("Houston", adapter1);
		

		EMHAdapter adapter2 = new EMHAdapter(getActivity(),
				getCityHospital_Info2(dropdown_category_list.getSelectedItem()));
		sepListAdapter.addSection("Dallas", adapter2);

		// Setting the sectioned list adapter to a custom list view
		SectionedListView list = (SectionedListView) container
				.findViewById(R.id.customlist);
		list.setAdapter(sepListAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		return container;
	}

	private SpannableString styleText() {
		SpannableString text = new SpannableString("TEXAS");
		text.setSpan(new ForegroundColorSpan(Color.parseColor("#E06666")), 0,
				5, 0);
		text.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);
		text.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 5, 0);
		return text;
	}

	private List<EMH> getCityHospital_Info2(Object object) {
		List<EMH> l = new ArrayList<EMH>();
		dropdown_category_list.setOnItemSelectedListener(new CustomOnItemSelectedListener(l));
		
		return l;
	}

	private List<EMH> getCityHospital_Info1(Object object) {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("Houston Methodist Hospital", "Selection", "6565 Fannin Street\nHouston, TX 77030-2707\n(713) 790Ð3311", null , new Float(4.5), true));
		l.add(new EMH("St. Luke's Episcopal Hospital", "Selection", "6720 Bertner Avenue \nHouston, TX 77030-2697\n(832) 355Ð1000", null , new Float(1.5), true));
		l.add(new EMH("MD Anderson Cancer Center,\nUniversity of Texas", "Selection", "1515 Holcombe Boulevard, Box 91\nHouston, TX 77030-4000\n(713) 792Ð2121", null ,null, true));
		return l;
	}

	@Override
	public void setActionBarTitle() {
		super.setActionBarTitle(R.string.action_bar_title_appointments);

	}
	
	 class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 List<EMH> l;
		 public CustomOnItemSelectedListener(List<EMH> l) {
			 this.l = l;
		}
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			  l.clear();
			/*Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
				Toast.LENGTH_SHORT).show();*/
			sepListAdapter.notifyDataSetChanged();
			switch (pos) {
			case 0:
				l.add(new EMH("Baylor University Medical Center", parent.getItemAtPosition(pos).toString(),"3500 Gaston Avenue\nDallas, TX 75246-2088\n(214) 820Ð0111", "1" , null, true));
				l.add(new EMH("UT Southwestern Medical Center", parent.getItemAtPosition(pos).toString(), "5323 Harry Hines Boulevard\nDallas, TX 75390-9265\n(214) 645Ð5555", "2" , null, true));
				l.add(new EMH("Medical City Dallas Hospital", parent.getItemAtPosition(pos).toString(), "7777 Forest Lane\nDallas, TX 75230-2598\n(972) 566Ð7000", "3" , null, true));
				l.add(new EMH("Texas Health Presbyterian Hospital", parent.getItemAtPosition(pos).toString(), "8200 Walnut Hill Lane\nDallas, TX 75231-4426\n(214) 345Ð6789", "4" , null, true));
				l.add(new EMH("Baylor Institute for Rehabilitation", parent.getItemAtPosition(pos).toString(), "909 North Washington Avenue\nDallas, TX 75246-1520\n(214) 820Ð9300", "5" , null, true));	
				break;
			case 1:
				l.add(new EMH("Baylor University Medical Center", parent.getItemAtPosition(pos).toString(),"3500 Gaston Avenue\nDallas, TX 75246-2088\n(214) 820Ð0111", "0.82 Miles" , null, true));
				l.add(new EMH("UT Southwestern Medical Center", parent.getItemAtPosition(pos).toString(), "5323 Harry Hines Boulevard\nDallas, TX 75390-9265\n(214) 645Ð5555", "1.70 Miles" , null, true));
				l.add(new EMH("Medical City Dallas Hospital", parent.getItemAtPosition(pos).toString(), "7777 Forest Lane\nDallas, TX 75230-2598\n(972) 566Ð7000", "10.69 Miles" , null, true));
				l.add(new EMH("Texas Health Presbyterian Hospital", parent.getItemAtPosition(pos).toString(), "8200 Walnut Hill Lane\nDallas, TX 75231-4426\n(214) 345Ð6789", "32.14 Miles" , null, true));
				l.add(new EMH("Baylor Institute for Rehabilitation", parent.getItemAtPosition(pos).toString(), "909 North Washington Avenue\nDallas, TX 75246-1520\n(214) 820Ð9300", "50.00 Miles" , null, true));
			
				break;
			case 2:
				dropdown.setVisibility(View.VISIBLE);
				l.add(new EMH("Baylor University Medical Center", dropdown.getSelectedItem().toString(),"3500 Gaston Avenue\nDallas, TX 75246-2088\n(214) 820Ð0111", "Nationally Ranked" ,null, true));
				l.add(new EMH("UT Southwestern Medical Center", dropdown.getSelectedItem().toString(), "5323 Harry Hines Boulevard\nDallas, TX 75390-9265\n(214) 645Ð5555", "High-Performing" , null, true));
				l.add(new EMH("Medical City Dallas Hospital", dropdown.getSelectedItem().toString(), "7777 Forest Lane\nDallas, TX 75230-2598\n(972) 566Ð7000", "High-Performing" , null, true));
				l.add(new EMH("Texas Health Presbyterian Hospital", dropdown.getSelectedItem().toString(), "8200 Walnut Hill Lane\nDallas, TX 75231-4426\n(214) 345Ð6789", "Unranked" , null, true));
				l.add(new EMH("Baylor Institute for Rehabilitation", dropdown.getSelectedItem().toString(), "909 North Washington Avenue\nDallas, TX 75246-1520\n(214) 820Ð9300", "Unranked" , null, true));
			
				break;
			case 3:
				l.add(new EMH("Baylor University Medical Center", parent.getItemAtPosition(pos).toString(),"3500 Gaston Avenue\nDallas, TX 75246-2088\n(214) 820Ð0111", null , new Float(4.5), true));
				l.add(new EMH("UT Southwestern Medical Center", parent.getItemAtPosition(pos).toString(), "5323 Harry Hines Boulevard\nDallas, TX 75390-9265\n(214) 645Ð5555", null , new Float(3.0), true));
				l.add(new EMH("Medical City Dallas Hospital", parent.getItemAtPosition(pos).toString(), "7777 Forest Lane\nDallas, TX 75230-2598\n(972) 566Ð7000", null , new Float(2.0), true));
				l.add(new EMH("Texas Health Presbyterian Hospital", parent.getItemAtPosition(pos).toString(), "8200 Walnut Hill Lane\nDallas, TX 75231-4426\n(214) 345Ð6789", null , new Float(1.0), true));
				l.add(new EMH("Baylor Institute for Rehabilitation", parent.getItemAtPosition(pos).toString(), "909 North Washington Avenue\nDallas, TX 75246-1520\n(214) 820Ð9300", null , new Float(0.0), true));
			
				break;

			}
		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		  }
		 
		}

}
