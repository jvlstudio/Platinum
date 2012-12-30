package org.tomleese.platinum.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import android.net.Uri;
import android.util.Log;

/**
 * A set of methods for working with files under android.
 * 
 * @author Tom Leese
 */
public abstract class FileUtils {

	protected static final String TAG = "FileUtils";
	
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
	
	/**
	 * Copies all data from the input stream into the output stream.
	 * 
	 * @param in The input stream
	 * @param out The output stream
	 * @param closeInStream Whether the input stream should be closed once the
	 *                      data has been copied
	 * @param closeOutStream Whether the output stream should be closed once
	 *                       the data has been copied
	 * @throws IOException Whether an IOException happens when copying or
	 *                     closing
	 */
	public static void copyFile(InputStream in, OutputStream out, boolean closeInStream, boolean closeOutStream) throws IOException {
		Log.d(TAG, "Copying streams: " + in.toString() + " -> " + out.toString());
		
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) != -1) {
		    out.write(buffer, 0, len);
		}
		
		if (closeInStream) {
			in.close();
		}
		
		if (closeOutStream) {
			out.close();
		}
	}
	
	/**
	 * Copies all data from the input stream into the output stream.
	 * 
	 * @param in The input stream
	 * @param out The output stream
	 * @param closeStreams Whether the streams should be closed once the data
	 *                     has been copied
	 * @throws IOException Whether an IOException happens when copying or
	 *                     closing
	 */
	public static void copyFile(InputStream in, OutputStream out, boolean closeStreams) throws IOException {
		copyFile(in, out, closeStreams, closeStreams);
	}
	
	/**
	 * Copies the contents of first file into the second file.
	 * 
	 * @param file1 The first file
	 * @param file2 The second file
	 * @throws IOException If an IO error occurs while reading or writing
	 * @throws FileNotFoundException If either of the files cannot be found or cannot be written to
	 */
	public static void copyFile(File file1, File file2) throws FileNotFoundException, IOException {
		Log.d(TAG, "Copying file: " + file1.getPath() + " -> " + file2.getPath());
		copyFile(new FileInputStream(file1), new FileOutputStream(file2), true);
	}
	
	/**
	 * Copies the contents from a file found at the first Uri, into the file at the second.
	 * 
	 * @param uri1 The location of the first file
	 * @param uri2 The location of the second file
	 * @throws FileNotFoundException If either of the files cannot be found or cannot be written to
	 * @throws IOException If an IO error occurs while reading or writing
	 */
	public static void copyFile(Uri uri1, Uri uri2) throws FileNotFoundException, IOException {
		Log.d(TAG, "Copying file: " + uri1.toString() + " -> " + uri2.toString());
		copyFile(openFileInStream(uri1), openFileOutStream(uri2), true);
	}
	
}
