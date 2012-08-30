package org.tomleese.platinum.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
		
		// TODO: make it use the application name
		
		File galleryFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/QuickPics/");
		galleryFile.mkdirs();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		galleryFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/QuickPics/" + dateFormat.format(new Date()) + ".jpg");
		
		CURRENT_URI = Uri.fromFile(galleryFile); 
		
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, CURRENT_URI);
		startActivityForResult(intent, REQUEST_CODE);
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
