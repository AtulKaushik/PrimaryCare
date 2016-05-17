package com.p1emergency.adapter;


import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.p1emergency.fragmentsupport.AbstractBaseFragmentActivity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class SwipingTabsPagerAdapter extends FragmentPagerAdapter implements ActionBar.TabListener,
    ViewPager.OnPageChangeListener {

  private final Context mContext;
  private final ActionBar mActionBar;
  private final ViewPager mViewPager;
  private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
  
  public final static String TAB_ACTIONBAR_TITLE = "tab_actionbar_title";

  static final class TabInfo {
    private final Class<?> clss;
    private final Bundle args;

    TabInfo(Class<?> _class, Bundle _args) {
      clss = _class;
      args = _args;
    }
  }

  public SwipingTabsPagerAdapter(AbstractBaseFragmentActivity activity, ViewPager pager) {
    
    super(activity.getSupportFragmentManager());
    mContext = activity;
    mActionBar = activity.getSupportActionBar();
    mViewPager = pager;
    mViewPager.setAdapter(this);
    mViewPager.setOnPageChangeListener(this);
  }

  public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
    TabInfo info = new TabInfo(clss, args);
    tab.setTag(info);
    tab.setTabListener(this);
    mTabs.add(info);
    mActionBar.addTab(tab);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return mTabs.size();
  }

  @Override
  public Fragment getItem(int position) {
    TabInfo info = mTabs.get(position);
    return Fragment.instantiate(mContext, info.clss.getName(), info.args);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public void onPageSelected(int position) {
    mActionBar.setSelectedNavigationItem(position);    
    mActionBar.setTitle((mTabs.get(position).args.getString(TAB_ACTIONBAR_TITLE)));
  }

  @Override
  public void onPageScrollStateChanged(int state) {
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    Object tag = tab.getTag();
    for (int i = 0; i < mTabs.size(); i++) {
      if (mTabs.get(i) == tag) {
        mViewPager.setCurrentItem(i);
      }
    }
  }


  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    //no op
  }

  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {
    // no op
  }
      
}
