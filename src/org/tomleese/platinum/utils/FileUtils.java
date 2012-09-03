package org.tomleese.platinum.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;

import android.net.Uri;
import android.util.Log;

/**
 * A set of methods for working with files under android.
 * 
 * @author Tom Leese
 */
public abstract class FileUtils {

	/**
	 * Converts an android Uri class to a java URI class.
	 * 
	 * @param uri The android Uri
	 * @return A new Java URI
	 */
	public static URI androidUriToJavaUri(Uri uri) {
		return URI.create(uri.toString());
	}
	
	/**
	 * Converts a Java URI class to an Android Uri class.
	 * 
	 * @param uri The java URI
	 * @return A new android Uri
	 */
	public static Uri javaUriToAndroidUri(URI uri) {
		return Uri.parse(uri.toString());
	}
	
	/**
	 * Provides a way of opening a file using an android Uri.
	 * @param uri The android Uri
	 * @return A newly opened file
	 */
	public static File openFile(Uri uri) {
		Log.d("FileUtils", "Opening file: " + uri.toString());
		return new File(FileUtils.androidUriToJavaUri(uri));
	}
	
	/**
	 * Provides a way of opening a file input stream using an android Uri. 
	 * 
	 * @param uri The android Uri
	 * @return A new file input stream
	 * @throws FileNotFoundException
	 */
	public static FileInputStream openFileInStream(Uri uri) throws FileNotFoundException {
		Log.d("FileUtils", "Opening file input stream: " + uri);
		return new FileInputStream(FileUtils.openFile(uri));
	}
	
	/**
	 * Provides a way of opening a file output stream using an android Uri. 
	 * 
	 * @param uri The android Uri
	 * @return A new file output stream
	 * @throws FileNotFoundException
	 */
	public static FileOutputStream openFileOutStream(Uri uri) throws FileNotFoundException {
		Log.d("FileUtils", "Opening file output stream: " + uri);
		return new FileOutputStream(FileUtils.openFile(uri));
	}
	
}
