package com.p1emergency.actionbar;

import com.p1emergency.root.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.p1emergency.fragmentsupport.AbstractBaseFragment;
import com.p1emergency.fragmentsupport.AbstractBaseFragmentActivity;


public class ActionBarListener<T extends AbstractBaseFragment> implements
		ActionBar.TabListener {
	private final AbstractBaseFragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final Bundle mArgs;
	private Fragment mFragment;

	public ActionBarListener(AbstractBaseFragmentActivity activity, int tag,
			Class<T> clz) {
		this(activity, tag, clz, null);
	}

	public ActionBarListener(AbstractBaseFragmentActivity activity, int tag,
			Class<T> clz, Bundle args) {
		mActivity = activity;
		mTag = mActivity.getString(tag);
		mClass = clz;
		mArgs = args;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		mFragment = mActivity.getSupportFragmentManager().findFragmentByTag(
				mTag);
		if (mFragment == null) {
			mFragment = Fragment
					.instantiate(mActivity, mClass.getName(), mArgs);
			ft.add(R.id.fragmentMainBody, mFragment, mTag);
		} else {
			ft.attach(mFragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (mFragment != null) {
			try {
				ft.detach(mFragment);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} finally {
				mFragment = null;
			}
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// no op
	}

}
