package org.tomleese.platinum.content;

import java.util.Map.Entry;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;

public abstract class Content implements Parcelable {
	
	private static final String TAG = "Content";
	
	private Uri mUri;
	private ContentValues mValues;
	
	public Content() {
		mUri = null;
		mValues = new ContentValues();
	}
	
	public Content(Parcel parcel) {
		mUri = parcel.readParcelable(getClass().getClassLoader());
		mValues = parcel.readParcelable(getClass().getClassLoader());
	}
	
	public Content(Cursor cursor, Uri contentUri) {
		int index = cursor.getColumnIndex(BaseColumns._ID);
		if (index != -1 && cursor.getPosition() < cursor.getCount()) {
			long id = cursor.getLong(index);
			Log.d(TAG, String.valueOf(id));
			mUri = ContentUris.withAppendedId(contentUri, id);
		}
		
		mValues = new ContentValues();
		fillContentValues(mValues, cursor);
	}
	
	public Content(Uri uri, Cursor cursor) {
		mUri = uri;
		
		mValues = new ContentValues();
		fillContentValues(mValues, cursor);
	}
	
	private void fillContentValues(ContentValues values, Cursor cursor) {
		for (int i = 0; i < cursor.getColumnCount(); i++) {
			String key = cursor.getColumnName(i);
			
			try {
				String value = cursor.getString(i);
				if (value != null) {
					values.put(key, value);
				} else {
					throw new SQLiteException();
				}
			} catch (SQLiteException e) {
				values.put(key, cursor.getBlob(i));
			}
		}
	}
	
	public Uri getContentUri() {
		return mUri;
	}
	
	public void setContentUri(Uri uri) {
		mUri = uri;
	}
	
	public ContentValues getContentValues() {
		return mValues;
	}
	
	@Override
	public int hashCode() {
		int code = 10;
		
		if (mUri != null) {
			code += mUri.hashCode();
		}
		
		for (Entry<String, Object> obj : mValues.valueSet()) {
			code += obj.hashCode();
		}
		
		return code;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mUri, flags);
		dest.writeParcelable(mValues, flags);
	}
	
}
