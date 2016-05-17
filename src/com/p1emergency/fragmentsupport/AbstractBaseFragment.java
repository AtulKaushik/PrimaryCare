package com.p1emergency.fragmentsupport;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

import android.app.Activity;
import android.app.ProgressDialog;

public abstract class AbstractBaseFragment extends SherlockFragment  {

	protected ProgressDialog getProgressDialog() {
		Activity activity = getActivity();
		if (activity == null) { return null; }
		ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Please wait");
		return progressDialog;
	}

	  
	protected void setActionBarTitle(int stringResource){	    
	    ActionBar ab = getSherlockActivity().getSupportActionBar();
	    ab.setTitle(stringResource);
	}
	
	public abstract void setActionBarTitle();
	
}
