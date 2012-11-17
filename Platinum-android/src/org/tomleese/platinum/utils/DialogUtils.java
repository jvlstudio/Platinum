package org.tomleese.platinum.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import org.tomleese.platinum.callbacks.DismissOnClickListener;

/**
 * Provides methods to quickly and easily create dialog boxes with
 * a minimal amount of code.
 * 
 * @author Tom Leese
 */
public abstract class DialogUtils {
    
	private static String getStringRes(Context context, Integer id) {
		if (id == null) {
			return null;
		}
		
		return context.getString(id);
	}
	
	/**
	 * Shows an OK dialog box using the android OK string.
	 * 
	 * @param context The context for the dialog box
	 * @param title The title of the dialog box, can be null.
	 * @param message The message of the dialog box, can be null.
	 * @param callback The callback of the dialog box, can be null.
	 * @return The new dialog box
	 */
	public static AlertDialog showOkDialog(Context context, String title,
			String message, OnClickListener callback) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) { builder.setTitle(title); }
		if (message != null) { builder.setMessage(message); }
		
		builder.setCancelable(false);
		
		if (callback == null) {
		    callback = new DismissOnClickListener();
		}
		
		builder.setPositiveButton(android.R.string.ok, callback);
		
		AlertDialog dialog = builder.create();
		dialog.show();
		
		return dialog;
	}
	
	/**
	 * Shows an OK dialog box using the android OK string.
	 * Takes the IDs of string resources for localisation.
	 * 
	 * @param context The context for the dialog box
	 * @param titleId The title ID of the dialog box, can be null.
	 * @param messageId The message ID of the dialog box, can be null.
	 * @param callback The callback of the dialog box, can be null.
	 * @return The new dialog box
	 */
	public static AlertDialog showOkDialog(Context context, Integer titleId,
			Integer messageId, OnClickListener callback) {
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		
		return showOkDialog(context, title, message, callback);
	}
	
	/**
	 * Shows a simple progress dialog
	 * 
	 * @param context The context for the progress dialog
	 * @param title The title of the progress dialog, can be null
	 * @param message The message of the progress dialog, can be null
	 * @return The new progress dialog
	 */
	public static ProgressDialog showProgressDialog(Context context,
			String title, String message) {
		if (title == null) {
			title = "";
		}
		
		if (message == null) {
			message = "";
		}
		
		return ProgressDialog.show(context, title, message);
	}
	
	/**
	 * Shows a simple progress dialog
	 * 
	 * @param context The context for the progress dialog
	 * @param title The title ID of the progress dialog, can be null
	 * @param message The message ID of the progress dialog, can be null
	 * @return The new progress dialog
	 */
	public static ProgressDialog showProgressDialog(Context context,
			Integer titleId, Integer messageId) {
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		
		return showProgressDialog(context, title, message);
	}
	
}
