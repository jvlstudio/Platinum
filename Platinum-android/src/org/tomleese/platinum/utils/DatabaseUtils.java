package org.tomleese.platinum.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Utilities for working with databases. Extends the android DatabaseUtils class
 * for ease of use.
 * 
 * @author tom
 */
@Deprecated
public abstract class DatabaseUtils extends android.database.DatabaseUtils {

	protected static final String TAG = "DatabaseUtils";
	
	private DatabaseUtils() {

	}

	/**
	 * Converts a cursor into a list of classes. The class type must have a
	 * constructor which takes a ContentValues instance.
	 * 
	 * @param klass
	 *            The class type to create an instance of
	 * @param cursor
	 *            The cursor to look through
	 * @param closeCursor
	 *            If true, closes the cursor
	 * @param database
	 *            The database, currently only used for closing
	 * @param closeDatabase
	 *            If true, closes the database
	 * @return A new list of items
	 */
	public static <T> List<T> cursorToList(Class<T> klass, Cursor cursor,
			boolean closeCursor, SQLiteDatabase database, boolean closeDatabase) {
		List<T> items = new ArrayList<T>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d(TAG, cursor.toString() + " > " + klass.toString());

			try {
				Constructor<T> ctor = klass.getConstructor(ContentValues.class);
				ContentValues values = new ContentValues();
				cursorRowToContentValues(cursor, values);
				items.add(ctor.newInstance(values));
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}

			cursor.moveToNext();
		}

		if (closeCursor) {
			cursor.close();
		}

		if (closeDatabase && database != null) {
			database.close();
		}

		return items;
	}

	/**
	 * Converts a cursor into a list of classes. The class type must have a
	 * constructor which takes a ContentValues instance. This does not close the
	 * database at the end.
	 * 
	 * @param klass
	 *            The class type to create an instance of
	 * @param cursor
	 *            The cursor to look through
	 * @param closeCursor
	 *            If true, closes the cursor
	 * @return A new list of items
	 */
	public static <T> List<T> cursorToList(Class<T> klass, Cursor cursor,
			boolean closeCursor) {
		return cursorToList(klass, cursor, closeCursor, null, false);
	}

	/**
	 * Converts a cursor into a list of classes. The class type must have a
	 * constructor which takes a ContentValues instance. This does not close the
	 * cursor or database at the end.
	 * 
	 * @param klass
	 *            The class type to create an instance of
	 * @param cursor
	 *            The cursor to look through
	 * @return A new list of items
	 */
	public static <T> List<T> cursorToList(Class<T> klass, Cursor cursor) {
		return cursorToList(klass, cursor, false);
	}

	/**
	 * Adds an item to the database. The class must have a method called
	 * "toContentValues" which returns a ContentValues for inserting into the
	 * table.
	 * 
	 * @param klass The item to add to the table
	 * @param table The table from the database
	 * @param db The actual database
	 * @param closeDatabase If true, the database will be closed at the end
	 */
	public static <T> void addToTable(T klass, String table, SQLiteDatabase db,
			boolean closeDatabase) {
		try {
			Method method = klass.getClass().getDeclaredMethod("toContentValues");
			ContentValues values = (ContentValues) method.invoke(klass, (Object[]) null);
			
			db.insert(table, null, values);
			
			if (closeDatabase) {
				db.close();
			}
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds an item to the database. The class must have a method called
	 * "toContentValues" which returns a ContentValues for inserting into the
	 * table.
	 * 
	 * @param klass The item to add to the table
	 * @param table The table from the database
	 * @param db The actual database
	 */
	public static <T> void addToTable(T klass, String table, SQLiteDatabase db) {
		addToTable(klass, table, db, false);
	}
	
}
