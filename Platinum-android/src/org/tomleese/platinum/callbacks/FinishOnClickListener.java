package org.tomleese.platinum.callbacks;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

/**
 * An OnClickListener that when clicked finishes an activity.
 * Works as both a view listener and a dialog listener.
 * 
 * @author Tom Leese
 */
public class FinishOnClickListener implements
        android.content.DialogInterface.OnClickListener, android.view.View.OnClickListener {
    
    private Activity mActivity;
    
    /**
     * Will finish the activity passed when the listener gets called.
     * 
     * @param activity The activity to finish
     */
    public FinishOnClickListener(Activity activity) {
        mActivity = activity;
    }
    
    /**
     * Called when clicked from a view.
     */
    public void onClick(View view) {
        mActivity.finish();
    }
    
    /**
     * Called when clicked from a dialog.
     */
    public void onClick(DialogInterface dialog, int which) {
        mActivity.finish();
    }
    
}
