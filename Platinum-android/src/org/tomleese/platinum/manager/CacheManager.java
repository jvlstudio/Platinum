package org.tomleese.platinum.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.tomleese.platinum.utils.FileUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

public class CacheManager {

	/**
	 * This key gets set on bundles when they are stored.
	 */
	public static final String KEY_DATE = "org.tomleese.platinum.CacheManager.date";
	
	protected static final String TAG = "CacheManager";
	
	private String mName;
	private int mTimeout;
	private HashMap<String, Bundle> mCache = new HashMap<String, Bundle>();
	private Context mContext;
	
	/**
	 * Creates a new cache manager using the name and timeout.
	 * 
	 * @param name
	 *            The name of the cache manager, and incidentally, the filename
	 *            used to store the bundles.
	 * @param timeout
	 *            The timeout before an item is considered old
	 */
	public CacheManager(Context context, String name, int timeout) {
		mName = name;
		mTimeout = timeout;
		mContext = context;

		read();
	}
	
	private File getDir() {
		File cacheDir = mContext.getExternalCacheDir();
		File myDir = new File(cacheDir, "cachemanager_" + mName);
		myDir.mkdirs();
		return myDir;
	}
	
	private void read() {
		File dir = getDir();
		Log.d(TAG, "Reading cached database from: " + dir);
		
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				Log.d(TAG, "Reading cached item from: " + file.getAbsolutePath());
				
				String key = file.getName();
				Parcel parcel = Parcel.obtain();
				
				try {
					InputStream in = new FileInputStream(file);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					FileUtils.copyFile(in, out, true);
					
					byte[] buf = out.toByteArray();
					parcel.unmarshall(buf, 0, buf.length);
					parcel.setDataPosition(0);
					
					Bundle bundle = Bundle.CREATOR.createFromParcel(parcel);
					mCache.put(key, bundle);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void saveItem(String key, Bundle item) {
		File file = new File(getDir(), key);
		Log.d(TAG, "Saving cached item to: " + file.getAbsolutePath());
		
		Parcel parcel = Parcel.obtain();
		item.writeToParcel(parcel, 0);
		
		try {
			InputStream in = new ByteArrayInputStream(parcel.marshall());
			OutputStream out = new FileOutputStream(file);
			FileUtils.copyFile(in, out, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String convertKey(String key) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.update(key.getBytes(), 0, key.length());
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			return key;
		}
	}
	
	/**
	 * Returns the context connected to this cache manager.
	 * 
	 * @return The context
	 */
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * Puts an item of data into the store.
	 * 
	 * @param key The key to the item
	 * @param item The item
	 */
	public void put(String key, Bundle item) {
		item.putLong(KEY_DATE, System.currentTimeMillis());
		
		key = convertKey(key);
		
		mCache.put(key, item);
		saveItem(key, item);
	}
	
	/**
	 * Gets an item from the store.
	 * 
	 * @param key The key to the item
	 * @param nullIfOld If true, the method will return null if the item is old
	 * @return The item or null
	 */
	public Bundle get(String key, boolean nullIfOld) {
		Bundle bundle = mCache.get(convertKey(key));
		bundle.setClassLoader(mContext.getClassLoader());
		
		if (nullIfOld && isOld(bundle)) {
			return null;
		}

		return bundle;
	}
	
	/**
	 * Gets the item from the store. If the item is considered old, this will return null.
	 * 
	 * @param key The key to the item
	 * @return The item or null
	 */
	public Bundle get(String key) {
		return get(key, true);
	}
	
	/**
	 * Returns whether the cache manager has the bundle referenced by the key in the store.
	 * 
	 * @param key The key to the item
	 * @return True if item is in the store
	 */
	public boolean isCached(String key) {
		return mCache.containsKey(convertKey(key));
	}
	
	private boolean isOld(Bundle bundle) {
		long diff = System.currentTimeMillis() - bundle.getLong(KEY_DATE);
		if (diff / 1000 > mTimeout) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines whether the bundle stored by the key is old. If the bundle
	 * doesn't is cached, this returns true.
	 * 
	 * @param key The key to look for the bundle
	 * @return 
	 */
	public boolean isOld(String key) {
		if (!isCached(key)) {
			return true;
		}
		
		
		return isOld(get(key, false));
	}

}
