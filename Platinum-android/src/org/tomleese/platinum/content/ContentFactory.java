package org.tomleese.platinum.content;

import android.database.Cursor;

public interface ContentFactory<T extends Content> {
	
	public T newInstance(Cursor cursor);
	
}
