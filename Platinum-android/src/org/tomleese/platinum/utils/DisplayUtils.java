package org.tomleese.platinum.utils;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * A set of utilities for working with the display.
 * 
 * @author Tom Leese
 */
public abstract class DisplayUtils {
	
	/**
	 * Provides a quick way of determining whether the display is being held portrait.
	 * 
	 * @param context The context to get the Display from
	 * @return True if the display is portrait
	 */
	public static final boolean isPortrait(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int rotation = display.getRotation();
		
		return rotation == Surface.ROTATION_0
				|| rotation == Surface.ROTATION_180;
	}
	
	/**
	 * Provides a quick way of determining whether the display is being held landscape. This method simples returns the "not-ed" value of isPortrait.
	 * 
	 * @param context The context to get the Display from
	 * @return True if the display is landscape
	 */
	public static final boolean isLandscape(Context context) {
		return !isPortrait(context);
	}
	
}
