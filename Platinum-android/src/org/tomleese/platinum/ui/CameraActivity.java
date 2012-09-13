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
import org.tomleese.platinum.utils.DialogUtils.OnDialogCallback;
import org.tomleese.platinum.utils.FileUtils;
import org.tomleese.platinum.utils.MediaUtils;

/**
 * An activity that can be used to take a picture using the android camera,
 * and receive the resulting URI.
 * 
 * @author Tom Leese
 */
public abstract class CameraActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private static Uri CURRENT_URI = null;
	private static final String TAG = "CameraActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			
			File tempFile = File.createTempFile("camera", ".jpg", getExternalCacheDir());
			CURRENT_URI = Uri.fromFile(tempFile);
			
			Log.d(TAG, "Creating temporary file for camera image: " + CURRENT_URI.toString());
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, CURRENT_URI);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
			
			Log.d(TAG, "Requested camera intent...");
			startActivityForResult(intent, REQUEST_CODE);
		} catch (IOException e) {
			onPhotoError(e);
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	Log.d(TAG, "Got photo response from camera...");
    	
    	if (resultCode == RESULT_OK) {
	        if (requestCode == REQUEST_CODE) {
	            if (FileUtils.openFile(CURRENT_URI).length() <= 0L) {
                    try {
                        onPhotoTaken(MediaUtils.getFilenameFromImageStore(this, data.getData()));
                    } catch (FileNotFoundException e) {
                        onPhotoError(null);
                    }
                } else {
                    onPhotoTaken(CURRENT_URI.getPath());
                }
	        }
    	} else {
    		onPhotoError(null);
    	}
    }
	
	/**
	 * Called when the photo gets taken. You are also expected to finish the
	 * activity via this method.
	 * 
	 * @param uri The URI to the photo
	 */
	public abstract void onPhotoTaken(String filename);
	
	/**
     * Called when the photo could not be chosen. The exception will be null
     * if the activity was dismissed rather than calling the error method. By
     * default this shows a dialog message and finishes the activity. 
     * 
     * @param e The exception that caused it
     */
    public void onPhotoError(Exception e) {
        DialogUtils.showOkDialog(this, R.string.error_camera, null, new OnDialogCallback() {
            
            @Override
            public void onButtonPressed(int button) {
                finish();
            }
            
        });
    }
	
}
