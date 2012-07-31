package org.tomleese.platinum.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Provides methods to quickly and easily create dialog boxes with
 * a minimal amount of code.
 * 
 * @author tom
 */
public class DialogUtils {

	/**
	 * Shows an OK dialog box using the android OK string.
	 * 
	 * @param context The context for the dialog box
	 * @param title The title of the dialog box, can be null.
	 * @param message The message of the dialog box, can be null.
	 * @return The new dialog box
	 */
	public static AlertDialog showOkDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		if (title != null) { builder.setTitle(title); }
		if (message != null) { builder.setMessage(message); }
		
		builder.setCancelable(false);
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
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
	 * @return The new dialog box
	 */
	public static AlertDialog showOkDialog(Context context, Integer titleId, Integer messageId) {
		String title = null;
		if (titleId != null) { 
			title = context.getString(titleId);
		};
		
		String message = null;
		if (messageId != null) {
			message = context.getString(messageId);
		}
		
		return showOkDialog(context, title, message);
	}
	
}
