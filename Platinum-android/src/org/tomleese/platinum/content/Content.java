package org.tomleese.platinum.content;

import android.content.ContentValues;

public interface Content {
	
	public void toContentValues(ContentValues values);
	public void fromContentValues(ContentValues values);
	
	public static interface ContentCreator<T extends Content> {
		
		public T newInstance();
		public T[] newArray(int size);
		
	}
	
}
