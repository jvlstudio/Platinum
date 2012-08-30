package org.tomleese.platinum.callbacks;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

public class IntentOnClickListener implements
		android.content.DialogInterface.OnClickListener, android.view.View.OnClickListener {

	private Activity activity;
	private Intent intent;
	
	public IntentOnClickListener(Activity activity, Intent intent) {
		this.activity = activity;
		this.intent = intent;
	}
	
	public IntentOnClickListener(Activity activity, Class<?> target) {
		this(activity, new Intent(activity, target));
	}
	
	public void onClick(View view) {
		this.activity.startActivity(this.intent);
	}

	public void onClick(DialogInterface iface, int arg) {
		this.activity.startActivity(this.intent);
	}

}
