package org.tomleese.platinum.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public abstract class ContentUtils {
	
	public static interface Content {
		
		public ContentValues toContentValues();
		
	}
	
	public static <T extends Content> void addToDatabase(SQLiteDatabase db, String table, T content, boolean closeDb) {
		ContentValues values = content.toContentValues();
		db.insert(table, null, values);
		
		if (closeDb) {
			db.close();
		}
	}
	
	public static <T extends Content> void updateDatabase(SQLiteDatabase db, String table, T content, String whereClause, String[] whereArgs, boolean closeDb) {
		ContentValues values = content.toContentValues();
		db.update(table, values, whereClause, whereArgs);
		
		if (closeDb) {
			db.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Content> T[] selectFromDatabase(SQLiteDatabase db, Cursor cursor, Class<T> klass, boolean closeCursor, boolean closeDb) {
		ArrayList<T> list = new ArrayList<T>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				ContentValues values = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cursor, values);
				
				T content = klass.getConstructor(ContentValues.class).newInstance(values);
				list.add(content);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			
			cursor.moveToNext();
		}
		
		Object array = Array.newInstance(klass, list.size());
		return list.toArray((T[]) array);
	}
	
}
