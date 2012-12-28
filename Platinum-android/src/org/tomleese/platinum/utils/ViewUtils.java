package org.tomleese.platinum.utils;

import android.view.View;
import android.view.ViewGroup;

public abstract class ViewUtils {

	private ViewUtils() {

	}

	public static void setViewGroupEnabled(ViewGroup group, boolean enabled) {
		for (int i = 0; i < group.getChildCount(); i++) {
			View view = group.getChildAt(i);
			view.setEnabled(enabled);
			
			if (view instanceof ViewGroup) {
				setViewGroupEnabled((ViewGroup) view, enabled);
			}
		}
	}

}
