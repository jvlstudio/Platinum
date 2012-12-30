package org.tomleese.platinum.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * A set of utilities for working with intents.
 * 
 * @author tom
 */
public abstract class IntentUtils {

	/**
	 * Convenience function that creates a new, empty, intent object and sets
	 * the data to the {@link Uri} provided.
	 * 
	 * @param data The uri to be used as the data for the intent.
	 * @return A new intent
	 */
	@SuppressLint("NewApi")
	public static Intent createWithData(Uri data) {
		Intent intent = new Intent();
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			intent.setData(data.normalizeScheme());
		} else {
			intent.setData(data);
		}
		
		return intent;
	}
	
	/**
	 * Convenience function that creates a new intent object with the action
	 * provided and sets the data to the {@link Uri} provided.
	 * 
	 * @param action The action to be used to create the intent
	 * @param data The uri to be used as the data for the intent.
	 * @return A new intent
	 */
	public static Intent createWithDataAndAction(String action, Uri data) {
		Intent intent = new Intent(action);
		intent.setData(data);
		return intent;
	}

}
