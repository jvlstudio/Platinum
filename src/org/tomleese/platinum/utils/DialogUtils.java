package org.tomleese.platinum.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
	 * A generic callback for all dialog boxes.
	 *
	 */
	public static interface OnDialogCallback {
		/**
		 * Called when a button of the dialog is pressed.
		 * @param button The button ID pressed, will be the index of the buttons in the dialog.
		 */
		public void onButtonPressed(int button);
	}
	
	/**
	 * A callback for a text dialog.
	 */
	public static interface OnTextCallback extends OnDialogCallback {
		/**
		 * Called when the OK button is pressed
		 * @param text The text of the input of the dialog
		 */
		public void onText(String text);
	}
	
	/**
	 * A callback for a boolean dialog.
	 */
	public static interface OnBooleanCallback extends OnDialogCallback {
		/**
		 * Called when the Yes/No button is pressed
		 * @param value True or False depending on the button pressed
		 */
		public abstract void onBoolean(boolean value);
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
	 * @param callback The callback of the dialog box, can be null.
	 * @return The new dialog box
	 */
	public static AlertDialog showOkDialog(Context context, String title,
			String message, final OnDialogCallback callback) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) { builder.setTitle(title); }
		if (message != null) { builder.setMessage(message); }
		
		builder.setCancelable(false);
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				if (callback != null) {
					callback.onButtonPressed(0);
				}
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
	 * @param callback The callback of the dialog box, can be null.
	 * @return The new dialog box
	 */
	public static AlertDialog showOkDialog(Context context, Integer titleId,
			Integer messageId, OnDialogCallback callback) {
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		
		return showOkDialog(context, title, message, callback);
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
	public static AlertDialog getString(Context context, String title,
			String message, String text, final OnTextCallback callback) {
		final EditText editable = new EditText(context);
		if (text != null) editable.setText(text);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) builder.setTitle(title);
		if (message != null) builder.setMessage(message);
		
		builder.setView(editable);
		builder.setCancelable(false);
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				callback.onText(editable.getText().toString());
				callback.onButtonPressed(0);
			}
			
		});
		
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				callback.onText(null);
				callback.onButtonPressed(1);
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
	
	/**
	 * Shows a Yes/No dialog with a message
	 * 
	 * @param context The context for the dialog box
	 * @param title The title of the dialog box, can be null.
	 * @param message The message of the dialog box, can be null.
	 * @param callback The text callback of the dialog
	 * @return The new dialog box
	 */
	public static AlertDialog getBoolean(Context context, String title, String message, final OnBooleanCallback callback) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) builder.setTitle(title);
		if (message != null) builder.setMessage(message);
		
		builder.setCancelable(false);
		
		builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				callback.onBoolean(true);
				callback.onButtonPressed(0);
			}
			
		});
		
		builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				callback.onBoolean(false);
				callback.onButtonPressed(0);
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
		
		return dialog;
	}
	
	/**
	 * Shows a Yes/No dialog with a message
	 * 
	 * @param context The context for the dialog box
	 * @param title The title ID of the dialog box, can be null.
	 * @param message The message ID of the dialog box, can be null.
	 * @param callback The text callback of the dialog
	 * @return The new dialog box
	 */
	public static AlertDialog getBoolean(Context context, Integer titleId, Integer messageId, final OnBooleanCallback callback) {
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		
		return getBoolean(context, title, message, callback);
	}
	
	/**
	 * Shows a simple progress dialog
	 * 
	 * @param context The context for the progress dialog
	 * @param title The title of the progress dialog, can be null
	 * @param message The message of the progress dialog, can be null
	 * @return The new progress dialog
	 */
	public static ProgressDialog showProgressDialog(Context context, String title, String message) {
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
	public static ProgressDialog showProgressDialog(Context context, Integer titleId, Integer messageId) {
		String title = getStringRes(context, titleId);
		String message = getStringRes(context, messageId);
		
		return showProgressDialog(context, title, message);
	}
}
