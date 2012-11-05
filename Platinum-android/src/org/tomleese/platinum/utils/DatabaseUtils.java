package org.tomleese.platinum.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			cursor.moveToNext();
		}

		if (closeCursor) {
			cursor.close();
		}

		if (database != null && closeDatabase) {
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
	 * @param cursor
	 * @return
	 */
	public static <T> List<T> cursorToList(Class<T> klass, Cursor cursor) {
		return cursorToList(klass, cursor, false);
	}

}
