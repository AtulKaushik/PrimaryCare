package com.p1emergency.activity;

import com.p1emergency.root.R;

import java.util.ArrayList;
import java.util.List;

import com.p1emergency.adapter.SlidingMenuListAdapter;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SideMenuBaseActivity extends SlidingFragmentActivity {

	private ListView mSlidingMenuList;
	private LayoutInflater mInflater;
	private View mSlideMenu;

	protected ValueAnimator mValueAnimator;
	protected boolean slideViewOut = false;
	protected boolean menuViewAdded = true;
	protected List<Drawable> drawablesList;
	View transparentView;

	private int mTitleRes;
	protected ListFragment mFrag;
	private String[] menuItems = { "Wait Times", "Profile", "  Settings", "  About Us", /*"Setting",*/ "  Logout" };

	public SideMenuBaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	private void init() {
		mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSlideMenu = mInflater.inflate(R.layout.slide_menu, null, false);
		mSlidingMenuList = (ListView) mSlideMenu.findViewById(R.id.sidebar_list);
		mSlidingMenuList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mSlidingMenuList.setOnItemClickListener(menuItemClickListener);

		setListviewSelection(mSlidingMenuList, 0);
	}

	/*
	 * Calling setSelection() on a list view doesn't have any visual change because it
	 * goes into touch mode So to set the item as selected, we perform a click on the list item
	 * after a delay. This is important because if you do it immediately, the list will not be
	 * populated.
	 */
	private void setListviewSelection(final ListView list, final int pos) {
		list.post(new Runnable() {
			@Override
			public void run() {
				View v = list.getChildAt(pos);
				if (v != null) {
					list.performItemClick(v, pos, v.getId());
				}
			}
		});
	}

	OnItemClickListener menuItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int arg2, long arg3) {
			if (menuItems[arg2].equalsIgnoreCase("Wait Times")) {
				showAbove();
				//getAppointments();
			}else if (menuItems[arg2].equalsIgnoreCase("Profile")) {
				//showAbove();
				getAppointments();
			}else if(menuItems[arg2].equalsIgnoreCase("Settings")){
				//showAbove();
				getHistory();
			}else if(menuItems[arg2].equalsIgnoreCase("About Us")){
				//showAbove();
				getReports();
			}else if(menuItems[arg2].equalsIgnoreCase("Setting")){
				//showAbove();
				//getSettings();
			}else if(menuItems[arg2].equalsIgnoreCase("Logout")){
				showAbove();
				logout();
			} else{
				showAbove();
			}

		}

	};

	private void logout() {
		finish();
		startActivity(new Intent(this, EMHLockScreenActivity.class));
	}
	
	private void getSettings() {
		//finish();
		//startActivity(new Intent(this, EMHSettingsActivity.class));
	}
	
	private void getReports() {
		//finish();
		startActivity(new Intent(this, EMHReportsActivity.class));
	}
	
	private void getHistory() {
		//finish();
		startActivity(new Intent(this, EMHHistoryActivity.class));
	}
	
	private void getAppointments() {
		//finish();
		//startActivity(new Intent(this, EMHAppointmentsActivity.class));
	}

	private void setSlidingMenuAdapter() {
		// Setting the Sliding Menu list adapter to a list view
		SlidingMenuListAdapter slideAdapter = new SlidingMenuListAdapter(this, menuItems, drawablesList);
		mSlidingMenuList.setAdapter(slideAdapter);
	}

	private void setDrawableList() {
		drawablesList = new ArrayList<Drawable>();
		drawablesList.add(getResources().getDrawable(R.drawable.ic_action_time));
		drawablesList.add(getResources().getDrawable(R.drawable.ic_action_person));
		drawablesList.add(getResources().getDrawable(R.drawable.settings_icon));
		drawablesList.add(getResources().getDrawable(R.drawable.global_icon_info));
		drawablesList.add(getResources().getDrawable(R.drawable.logout_icon));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		// set the Behind View
		init();
		setDrawableList();
		setSlidingMenuAdapter();
		setBehindContentView(mSlideMenu);

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.actionbar_home_width);

		// customize the ActionBar
		if (Build.VERSION.SDK_INT >= 11) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class PagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> mFragments = new ArrayList<Fragment>();
		private ViewPager mPager;

		public PagerAdapter(FragmentManager fm, ViewPager vp) {
			super(fm);
			mPager = vp;
			mPager.setAdapter(this);
		}

		public void addTab(Fragment frag) {
			mFragments.add(frag);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}
	}

}
