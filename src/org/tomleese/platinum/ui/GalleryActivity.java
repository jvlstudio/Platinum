
package org.tomleese.platinum.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.tomleese.platinum.R;
import org.tomleese.platinum.utils.DialogUtils;
import org.tomleese.platinum.utils.FileUtils;
import org.tomleese.platinum.utils.MediaUtils;
import org.tomleese.platinum.utils.DialogUtils.OnDialogCallback;

/**
 * An activity that can be used to choose a picture using the android gallery,
 * and receive the resulting URI.
 * 
 * @author Tom Leese
 */
public abstract class GalleryActivity extends Activity {

	private static final int REQUEST_CODE = 0;
	private static Uri CURRENT_URI = null;
	private static final String TAG = "GalleryActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			
			File tempFile = File.createTempFile("gallery", ".jpg", getExternalCacheDir());
			CURRENT_URI = Uri.fromFile(tempFile);
						
			Log.d(TAG, "Creating temporary file for gallery image: " + CURRENT_URI.toString());
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, CURRENT_URI);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
			
			Log.d(TAG, "Requested gallery intent...");
			startActivityForResult(intent, REQUEST_CODE);
		} catch (IOException e) {
		    onPhotoError(e);
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	Log.d(TAG, "Got photo from gallery...");
    	
    	if (resultCode == RESULT_OK) {
	        if (requestCode == REQUEST_CODE) {
	            if (FileUtils.openFile(CURRENT_URI).length() <= 0L) {
	                try {
                        onPhotoChosen(MediaUtils.getFilenameFromImageStore(this, data.getData()));
                    } catch (FileNotFoundException e) {
                        onPhotoError(null);
                    }
	            } else {
	                onPhotoChosen(CURRENT_URI.getPath());
	            }
	        }
    	} else {
    		onPhotoError(null);
    	}
    }
	
	/**
	 * Called when a picture has been selected. You are also expected to finish the
     * activity via this method.
	 * 
	 * @param filename The filename of the image
	 */
	public abstract void onPhotoChosen(String filename);
	
	/**
	 * Called when the photo could not be chosen. The exception will be null
	 * if the activity was dismissed rather than calling the error method. By
     * default this shows a dialog message and finishes the activity. 
	 * 
	 * @param e The exception that caused it
	 */
	public void onPhotoError(Exception e) {
	    DialogUtils.showOkDialog(this, R.string.error_gallery, null, new OnDialogCallback() {
            
            @Override
            public void onButtonPressed(int button) {
                finish();
            }
            
        });
	}
	
}
