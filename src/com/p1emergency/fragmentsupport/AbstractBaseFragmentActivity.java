package com.p1emergency.fragmentsupport;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.p1emergency.androidshared.utils.AbstractApplicationAdapter;



public abstract class AbstractBaseFragmentActivity extends
		SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContext(this.getApplicationContext());
	}

	protected static void setContext(Context appContext) {
		AbstractApplicationAdapter.context = appContext;
	}

	protected ProgressDialog getProgressDialog() {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Please wait");
		return progressDialog;
	}

	protected void setActionBarTitle(int stringResource) {
		ActionBar ab = getSupportActionBar();
		ab.setTitle(stringResource);
	}

	protected void setActionBarTitle(String stringResource) {
		ActionBar ab = getSupportActionBar();
		ab.setTitle(stringResource);
	}

}
