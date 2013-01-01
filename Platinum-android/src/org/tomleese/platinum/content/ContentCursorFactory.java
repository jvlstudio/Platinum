package org.tomleese.platinum.content;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

public class ContentCursorFactory<T extends Content> implements CursorFactory {
	
	private Class<T> mClass;
	
	public ContentCursorFactory(Class<T> klass) {
		mClass = klass;
	}
	
	@Override
	public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
			String editTable, SQLiteQuery query) {
		return new ContentCursor<T>(db, driver, editTable, query, mClass);
	}
	
}
