package org.tomleese.platinum.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * A set of utilities for working with networks.
 * 
 * @author Tom Leese
 */
public abstract class NetworkUtils {
	
	/**
	 * Returns true if the device is connected to the internet via some means.
	 * 
	 * @param context The context of the caller
	 * @return True if online
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        
        return false;
	}
	
}
