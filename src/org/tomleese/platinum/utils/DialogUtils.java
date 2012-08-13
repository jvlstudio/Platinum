package org.tomleese.platinum.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

/**
 * Provides methods to quickly and easily create dialog boxes with
 * a minimal amount of code.
 * 
 * @author tom
 */
public class DialogUtils {

	/**
	 * A callback for a text dialog.
	 * 
	 * @author tom
	 *
	 */
	public static interface OnTextCallback {
		/**
		 * Called when the OK button is pressed
		 * @param text The text of the input of the dialog
		 * @return True if the text is valid and the dialog should be dismissed
		 */
		public boolean onText(String text);
	}
	
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
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		
		return showOkDialog(context, title, message);
	}
	
	/**
	 * Shows an OK dialog with a text input
	 * 
	 * @param context The context for the dialog box
	 * @param title The title of the dialog box, can be null.
	 * @param message The message of the dialog box, can be null.
	 * @param text The text of the input view, can be null.
	 * @param callback The text callback of the dialog
	 * @return The new dialog box
	 */
	public static AlertDialog getString(Context context, String title, String message, String text, final OnTextCallback callback) {
		final EditText editable = new EditText(context);
		if (text != null) editable.setText(text);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) builder.setTitle(title);
		if (message != null) builder.setMessage(message);
		
		builder.setView(editable);
		builder.setCancelable(false);
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				if (callback.onText(editable.getText().toString())) {
					dialog.dismiss();
				}
			}
			
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
		
		return dialog;
	}
	
	/**
	 * Shows an OK dialog with a text input
	 * 
	 * @param context The context for the dialog box
	 * @param title The title ID of the dialog box, can be null.
	 * @param message The message ID of the dialog box, can be null.
	 * @param text The text ID of the input view, can be null.
	 * @param callback The text callback of the dialog
	 * @return The new dialog box
	 */
	public static AlertDialog getString(Context context, Integer titleId, Integer messageId, Integer textId, OnTextCallback callback) {
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		String text = getStringRes(context, textId);
		
		return getString(context, title, message, text, callback);
	}
}
