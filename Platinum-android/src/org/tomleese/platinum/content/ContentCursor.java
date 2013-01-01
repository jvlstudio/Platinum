package org.tomleese.platinum.content;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public class ContentCursor<T extends Content> extends SQLiteCursor {

	private Class<T> mClass;

	@SuppressWarnings("deprecation")
	public ContentCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
			String editTable, SQLiteQuery query, Class<T> klass) {
		super(db, driver, editTable, query);
		mClass = klass;
	}
	
	public T toContent() {
		try {
			T content = mClass.newInstance();
			
			ContentValues values = new ContentValues();
			DatabaseUtils.cursorRowToContentValues(this, values);
			content.fromContentValues(values);
			
			return content;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
