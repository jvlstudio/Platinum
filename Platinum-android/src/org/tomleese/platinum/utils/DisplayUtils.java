package org.tomleese.platinum.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * A set of utilities for working with the display.
 * 
 * @author Tom Leese
 */
public abstract class DisplayUtils {
	
	/**
	 * Provides a quick way of determining whether the display is being held
	 * portrait.
	 * 
	 * @param context The context to get the Display from
	 * @return True if the display is portrait
	 */
	public static final boolean isPortrait(Context context) {
		return context.getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_PORTRAIT;
	}
	
	/**
	 * Provides a quick way of determining whether the display is being held
	 * landscape.
	 * 
	 * @param context The context to get the Display from
	 * @return True if the display is landscape
	 */
	public static final boolean isLandscape(Context context) {
		return context.getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_LANDSCAPE;
	}
	
}
