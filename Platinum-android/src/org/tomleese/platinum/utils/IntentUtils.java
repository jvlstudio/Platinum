package org.tomleese.platinum.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * A set of utilities for working with intents.
 * 
 * @author tom
 */
public abstract class IntentUtils {
	
	/**
	 * Creates a new, empty, intent object and sets the data to the Uri provided. 
	 * 
	 * @param data The uri to be used as the data for the intent.
	 * @return A new intent
	 */
	public static Intent createIntentWithData(Uri data) {
		Intent intent = new Intent();
		intent.setData(data);
		return intent;
	}
	
}
