package com.p1emergency.utility;

import com.p1emergency.asynctask.NetworkTask;
import com.p1emergency.utility.NetworkUtility.CONNECTION;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


/**
 * @author Atul Kaushik (kaushik.atul@gmail.com)
 *
 */
public class SpanUtility extends NetworkTask {

	private Handler networkAvailabilityHandler;

	public SpanUtility(Context appContext, Handler networkAvailabilityHandler) {
		super(appContext);
		this.networkAvailabilityHandler = networkAvailabilityHandler;
	}

	@Override
	protected Void doInBackground(Void... params) {
		super.doInBackground(params);
		if (!isNetworkAvailable) return null;
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		CONNECTION applicationState = null;
		if (!NetworkUtility.isNetworkAvailable(mContext)) {
			//applicationState = CONNECTION.QUIT;
			applicationState = CONNECTION.PROCEED_WITHOUT_INTERNET;
		}
		else {
			applicationState = CONNECTION.PROCEED;
		}
		Message message = networkAvailabilityHandler.obtainMessage();
		message.obj = applicationState;
		networkAvailabilityHandler.sendMessage(message);

	}	
}
