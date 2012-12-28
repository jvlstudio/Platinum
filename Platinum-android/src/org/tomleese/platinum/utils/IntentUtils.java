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
	 * Convenience function that creates a new, empty, intent object and sets
	 * the data to the {@link android.net.Uri} provided.
	 * 
	 * @param data
	 *            The uri to be used as the data for the intent.
	 * @return A new intent
	 */
	public static Intent createWithData(Uri data) {
		Intent intent = new Intent();
		intent.setData(data);
		return intent;
	}

	/**
	 * Convenience function that creates a new, empty, intent object and sets
	 * the data to the {@link android.net.Uri} provided.
	 * 
	 * @param data
	 *            The uri to be used as the data for the intent.
	 * @return A new intent
	 */
	@Deprecated
	public static Intent createIntentWithData(Uri data) {
		return createWithData(data);
	}
	
	/**
	 * Convenience function that creates a new intent object with the action
	 * provided and sets the data to the {@link android.net.Uri} provided.
	 * 
	 * @param action
	 *            The action to be used to create the intent
	 * @param data
	 *            The uri to be used as the data for the intent.
	 * @return A new intent
	 */
	public static Intent createWithDataAndAction(String action, Uri data) {
		Intent intent = new Intent(action);
		intent.setData(data);
		return intent;
	}

}
