package org.tomleese.platinum.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.tomleese.platinum.utils.FileUtils;
import org.tomleese.platinum.utils.IntentUtils;

/**
 * An activity that can be used to choose a picture using the android gallery,
 * and receive the resulting Uri. Like the CameraActivity, this activity creates
 * a temporary file for the picked photo to be stored.
 * 
 * You should start this activity by using startActivityForResult. You can get
 * the Uri to the photo filename by using Intent.getData().
 * 
 * @author Tom Leese
 */
public class GalleryActivity extends Activity {
	
	protected static final String TAG = "GalleryActivity";
	
	private static final int REQUEST_CODE = 0;
	private static Uri sCurrentUri = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			
			File tempFile = File.createTempFile("gallery", ".jpg", getExternalCacheDir());
			sCurrentUri = Uri.fromFile(tempFile);
			
			Log.d(TAG, "Creating temporary file for gallery image: " + sCurrentUri.toString());
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, sCurrentUri);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
			
			Log.d(TAG, "Requested gallery intent...");
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
			Log.d(TAG, "Got photo from gallery...");
			
			if (resultCode == RESULT_OK) {
				if (FileUtils.openFile(sCurrentUri).length() <= 0L) {
					try {
						InputStream is = getContentResolver().openInputStream(data.getData());
						Bitmap bitmap = BitmapFactory.decodeStream(is);
						
						if (bitmap == null) {
							setResult(RESULT_CANCELED);
						} else {
							bitmap.compress(CompressFormat.JPEG, 100, FileUtils.openFileOutStream(sCurrentUri));
							bitmap.recycle();

							setResult(RESULT_OK, IntentUtils.createWithData(sCurrentUri));
						}
					} catch (IOException e) {
						setResult(RESULT_CANCELED);
					}
				} else {
					setResult(RESULT_OK, IntentUtils.createWithData(sCurrentUri));
				}
			} else {
				setResult(RESULT_CANCELED);
			}
		}
		
		finish();
	}
	
}
