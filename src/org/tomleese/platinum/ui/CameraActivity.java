package org.tomleese.platinum.ui;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

/**
 * An activity that can be used to take a picture using the android camera,
 * and recieve the resulting URI.
 * 
 * @author Tom Leese
 */
public abstract class CameraActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private static Uri CURRENT_URI = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			
			CURRENT_URI = Uri.fromFile(File.createTempFile("gallery", ".jpg", getCacheDir()));
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, CURRENT_URI);
			startActivityForResult(intent, REQUEST_CODE);
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	Log.d("CameraActivity", "Got photo from camera...");
    	    	
    	if (resultCode == RESULT_OK) {
	        if (requestCode == REQUEST_CODE) {
	        	onPhotoTaken(CURRENT_URI);
	        }
    	}
    	
    	finish();
    }
	
	/**
	 * Called when the photo gets taken.
	 * 
	 * @param uri The URI to the photo
	 */
	public abstract void onPhotoTaken(Uri uri);
	
}
