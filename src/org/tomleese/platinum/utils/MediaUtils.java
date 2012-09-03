package org.tomleese.platinum.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileNotFoundException;

/**
 * Provides some utils for working with media.
 * 
 * @author Tom Leese
 */
public abstract class MediaUtils {

    private static final String TAG = "MediaUtils";
    
    /**
     * Gets the filename of the image from the android media store, using a Uri
     * from a gallery app.
     * 
     * @param activity The activity to call managedQuery on
     * @param uri The uri of the image
     * @return The filename location of the image
     * @throws FileNotFoundException If the image cannot be found
     */
    public static String getFilenameFromImageStore(Activity activity, Uri uri) throws FileNotFoundException {
        Log.d(TAG, "Getting image from store using: " + uri.toString());
        String[] projx = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(uri, projx, null, null, null);
        if (cursor == null) {
            throw new FileNotFoundException();
        }
        
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
}
