package org.tomleese.platinum.callbacks;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * An OnClickListener that when clicked starts a new intent.
 * Works as both a view listener and a dialog listener.
 * 
 * @author Tom Leese
 */
public class IntentOnClickListener implements
		android.content.DialogInterface.OnClickListener, android.view.View.OnClickListener {

	private Activity activity;
	private Intent intent;
	
	/**
	 * Uses the intent provided when a button is clicked.
	 * 
	 * @param activity The parent activity, to call startActivity on
	 * @param intent The intent to be started
	 */
	public IntentOnClickListener(Activity activity, Intent intent) {
		this.activity = activity;
		this.intent = intent;
	}
	
	/**
	 * Creates an intent using the activity and target activity.
	 * 
	 * @param activity The parent activity, to call startActivity on
	 * @param target The target activity to be started.
	 */
	public IntentOnClickListener(Activity activity, Class<?> target) {
		this(activity, new Intent(activity, target));
	}
	
	/**
	 * Called when clicked from a view.
	 */
	public void onClick(View view) {
		this.activity.startActivity(this.intent);
	}

	/**
	 * Called when clicked from a dialog.
	 */
	public void onClick(DialogInterface iface, int arg) {
		this.activity.startActivity(this.intent);
	}

}
