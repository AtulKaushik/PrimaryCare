package com.p1emergency.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Atul Kaushik (atul.kaushik@gmail.com)
 *
 */
public class NetworkUtility {
	static ConnectivityManager mConnectivityManager;
	static NetworkInfo mNetworkInfo;
	public static boolean isNetworkAvailable(Context context) {
		mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
	    if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public static enum CONNECTION {
            QUIT("quit"), PROCEED("proceed"),PROCEED_WITHOUT_INTERNET("proceed_without_internet");
            
            private final String connection;
            
            private CONNECTION(String connection) {
                    this.connection = connection;
            }
            
            public String getConnection() {
                    return connection;
            }
    }
}
