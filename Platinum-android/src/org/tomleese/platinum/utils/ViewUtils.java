package org.tomleese.platinum.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Utils for working with views.
 * 
 * @author tom
 */
public abstract class ViewUtils {
	
	private ViewUtils() {

	}
	
	/**
	 * Recursively enables/disables all the childs group, and any children of
	 * any {@link ViewGroup} in that {@link ViewGroup}.
	 * 
	 * @param group The {@link ViewGroup} to enable/disable
	 * @param enabled Whether the {@link ViewGroup} should be enabled/disabled
	 */
	public static void setViewGroupEnabled(ViewGroup group, boolean enabled) {
		group.setEnabled(enabled);
		
		for (int i = 0; i < group.getChildCount(); i++) {
			View view = group.getChildAt(i);
			view.setEnabled(enabled);
			
			if (view instanceof ViewGroup) {
				setViewGroupEnabled((ViewGroup) view, enabled);
			}
		}
	}
	
}
