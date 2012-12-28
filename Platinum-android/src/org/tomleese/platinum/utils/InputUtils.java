package org.tomleese.platinum.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class InputUtils {
	
	private InputUtils() {
		
	}
	
	/**
	 * Causes any soft input window to be hidden from the user.
	 * 
	 * @param context The context of the application
	 * @param view The view to remove the input window from
	 */
	public static void hideSoftInput(Context context, View view) {
		InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
}
