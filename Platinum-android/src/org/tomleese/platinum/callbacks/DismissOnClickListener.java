package org.tomleese.platinum.callbacks;

import android.content.DialogInterface;

/**
 * An OnClickListener that when clicked dismisses a dialog.
 * 
 * @author Tom Leese
 */
public class DismissOnClickListener implements
        android.content.DialogInterface.OnClickListener {
    
    /**
     * Called when clicked from a dialog.
     */
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
    
}
