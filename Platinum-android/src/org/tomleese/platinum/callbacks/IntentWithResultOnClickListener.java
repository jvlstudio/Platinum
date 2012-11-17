package org.tomleese.platinum.callbacks;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
/**
 * An OnClickListener that when clicked starts a new intent for a result.
 * Works as both a view listener and a dialog listener.
 * 
 * @author Tom Leese
 */
public class IntentWithResultOnClickListener implements android.content.DialogInterface.OnClickListener,
		android.view.View.OnClickListener {

	private Activity mActivity;
	private Intent mIntent;
	private int mRequestCode;
	
	/**
	 * Uses the intent provided when a button is clicked.
	 * 
	 * @param activity The parent activity, to call startActivity on
	 * @param intent The intent to be started
	 * @param requestCode The request code to be used when starting the activity
	 */
	public IntentWithResultOnClickListener(Activity activity, Intent intent, int requestCode) {
		mActivity = activity;
		mIntent = intent;
		mRequestCode = requestCode;
	}
	
	/**
	 * Creates an intent using the activity and target activity.
	 * 
	 * @param activity The parent activity, to call startActivity on
	 * @param target The target activity to be started
	 * @param requestCode The request code to be used when starting the activity
	 */
	public IntentWithResultOnClickListener(Activity activity, Class<?> target, int requestCode) {
		this(activity, new Intent(activity, target), requestCode);
	}
	
	/**
	 * Called when clicked from a view.
	 */
	public void onClick(View view) {
		mActivity.startActivityForResult(mIntent, mRequestCode);
	}

	/**
	 * Called when clicked from a dialog.
	 */
	public void onClick(DialogInterface iface, int arg) {
		mActivity.startActivityForResult(mIntent, mRequestCode);
	}

}
