package org.tomleese.platinum.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public abstract class ContentUtils {
	
	public static interface Content extends ContentWriter, ContentReader {
		
	}
	
	public static interface ContentWriter {
		
		public void toContentValues(ContentValues values);
		
	}
	
	public static interface ContentReader {
		
		public void fromContentValues(ContentValues values);
		
	}
	
	public static <T extends ContentWriter> long addToDatabase(SQLiteDatabase db, String table, T content, boolean closeDb) {
		ContentValues values = new ContentValues();
		content.toContentValues(values);
		long id = db.insert(table, null, values);
		
		if (closeDb) {
			db.close();
		}
		
		return id;
	}
	
	public static <T extends ContentWriter> int updateDatabase(SQLiteDatabase db, String table, T content, String whereClause, String[] whereArgs, boolean closeDb) {
		ContentValues values = new ContentValues();
		content.toContentValues(values);
		int i = db.update(table, values, whereClause, whereArgs);
		
		if (closeDb) {
			db.close();
		}
		
		return i;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends ContentReader> T[] selectFromDatabase(SQLiteDatabase db, Cursor cursor, Class<T> klass, boolean closeCursor, boolean closeDb) {
		ArrayList<T> list = new ArrayList<T>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				T content = klass.newInstance();
				ContentValues values = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cursor, values);
				content.fromContentValues(values);
				list.add(content);
			} catch (InstantiationException e) {
				new RuntimeException(e);
			} catch (IllegalAccessException e) {
				new RuntimeException(e);
			}
			
			cursor.moveToNext();
		}
		
		if (closeCursor) {
			cursor.close();
		}
		
		if (closeDb) {
			db.close();
		}
		
		Object array = Array.newInstance(klass, list.size());
		return list.toArray((T[]) array);
	}
	
	public static <T extends ContentReader> T[] selectAllFromDatabase(SQLiteDatabase db, String table, Class<T> klass, boolean closeDb) {
		Cursor cursor = db.query(table, null, null, null, null, null, null);
		return selectFromDatabase(db, cursor, klass, true, closeDb);
	}
	
}
