package org.tomleese.platinum.app;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.tomleese.platinum.utils.IntentUtils;

/**
 * An activity that can be used to take a picture using the android camera, and
 * receive the resulting Uri. This activity will create a temporary file for the
 * photo to be stored.
 * 
 * You should start this activity by using startActivityForResult. You can get
 * the Uri to the photo filename by using Intent.getData().
 * 
 * @author Tom Leese
 */
public class CameraActivity extends Activity {
	
	protected static final String TAG = "CameraActivity";
	protected static final String METADATA = "org.tomleese.platinum.camera";
	
	private static final int REQUEST_CODE = 1;
	private static Uri sCurrentUri = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			File tempFile = File.createTempFile("camera", ".jpg", getExternalCacheDir());
			sCurrentUri = Uri.fromFile(tempFile);
			
			Log.d(TAG, "Creating temporary file for camera image: " + sCurrentUri.toString());
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, sCurrentUri);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
			
			Log.d(TAG, "Requested camera intent...");
			startActivityForResult(intent, REQUEST_CODE);
		} catch (IOException e) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_CODE) {
			Log.d(TAG, "Got photo response from the camera");
			
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK, IntentUtils.createWithData(sCurrentUri));
			} else {
				setResult(RESULT_CANCELED);
			}
		}
		
		finish();
	}
	
}
