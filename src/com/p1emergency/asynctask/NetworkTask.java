package com.p1emergency.asynctask;


import com.p1emergency.utility.NetworkUtility;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * @author Atul Kaushik (kaushik.atul@gmail.com)
 *
 */
public class NetworkTask extends AsyncTask<Void, Void, Void> {

        protected boolean toast;
	protected Context mContext;
	protected boolean isNetworkAvailable;

	protected NetworkTask(Context context) {
		super();
		if (context != null) this.mContext = context.getApplicationContext();
	}

	protected NetworkTask(Context context, boolean toast) {
		this(context);
		this.toast = toast;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (mContext != null) {
			isNetworkAvailable = NetworkUtility.isNetworkAvailable(mContext);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (mContext != null) {
		    if(!NetworkUtility.isNetworkAvailable(mContext) ){
		        if(toast)
		        Toast.makeText(mContext, "NO INTERNET CONNECTION : PLEASE CHECK YOUR CONNECTION", Toast.LENGTH_SHORT).show();
		        
		    }	
		}
		super.onPostExecute(result);
	}
}
