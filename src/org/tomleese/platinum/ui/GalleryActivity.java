package org.tomleese.platinum.ui;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

/**
 * An activity that can be used to choose a picture using the android gallery,
 * and recieve the resulting URI.
 * 
 * @author Tom Leese
 */
public abstract class GalleryActivity extends Activity {

	private static final int REQUEST_CODE = 0;
	private static Uri CURRENT_URI = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.name());
			
			File outputFile = File.createTempFile("gallery", ".png", getCacheDir());
			
			CURRENT_URI = Uri.fromFile(outputFile);
			
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
    	
    	Log.d("GalleryActivity", "Got photo from gallery...");
    	
    	if (resultCode == RESULT_OK) {
	        if (requestCode == REQUEST_CODE) {
	        	onPhotoChosen(CURRENT_URI);
	        }
    	}

    	finish();
    }
	
	/**
	 * Called when a picture has been selected.
	 * 
	 * @param uri The URI to the resulting image
	 */
	public abstract void onPhotoChosen(Uri uri);
	
}
