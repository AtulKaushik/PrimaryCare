package com.p1emergency.actionbar;

import com.p1emergency.root.R;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.p1emergency.fragment.EmergencyCareTabFragment;
import com.p1emergency.fragment.RegularCareTabFragment;
import com.p1emergency.fragmentsupport.AbstractBaseFragmentActivity;


public class ActionBarHelper {

	public Tab addTab(AbstractBaseFragmentActivity activity, int title_res_id) {
		Tab tab = activity.getSupportActionBar().newTab();
		tab.setText(title_res_id).setTabListener(
				getActionBarListener(activity, title_res_id));
		return tab;
	}

	@SuppressWarnings("rawtypes")
	private ActionBarListener getActionBarListener(
			AbstractBaseFragmentActivity activity, int title_res_id) {

		if (title_res_id == R.string.swipe_tab_main_text) {
			return new ActionBarListener<RegularCareTabFragment>(activity,
					R.string.swipe_tab_main_text, RegularCareTabFragment.class);
		} else if (title_res_id == R.string.swipe_tab_two_text) {
			return new ActionBarListener<EmergencyCareTabFragment>(activity,
					R.string.swipe_tab_two_text, EmergencyCareTabFragment.class);
		}
		return null;
	}

}
