package org.tomleese.platinum.content;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A content manager wraps around a SQLiteOpenHelper and a SQL Table to make
 * working with Contents easier.
 * 
 * @author Tom Leese
 *
 * @param <T> The content type
 */
public class ContentManager<T extends Content> {
	
	private static final String TAG = "ContentManager";
	
	private SQLiteOpenHelper mDatabase;
	private String mTable;
	private Class<T> mClass;
	
	/**
	 * Constructs a new ContentManager.
	 * 
	 * @param db The SQLiteOpenHelper for that database
	 * @param table The table for the content
	 * @param klass The content class used for newInstance
	 */
	public ContentManager(SQLiteOpenHelper db, String table, Class<T> klass) {
		mDatabase = db;
		mTable = table;
		mClass = klass;
		
		Log.d(TAG, "Creating new ContentManager for: " + table);
	}
	
	/**
	 * Inserts a content type into the database using toContentValues.
	 * 
	 * @param content The content type
	 * @return The rowid of the newly inserted item
	 */
	public long insert(T content) {
		ContentValues values = new ContentValues();
		content.toContentValues(values);
		
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		long i = db.insert(mTable, null, values);
		db.close();
		return i;
	}
	
	/**
	 * Updates a content type by using the content's ContentValues and
	 * where clause.
	 * 
	 * @param content The content type
	 * @param whereClause The where clause to use when updating
	 * @param whereArgs The arguments used to expand the whereClause
	 * @return How many rows have been changed
	 */
	public int update(T content, String whereClause, String[] whereArgs) {
		ContentValues values = new ContentValues();
		content.toContentValues(values);
		
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		int i = db.update(mTable, values, whereClause, whereArgs);
		db.close();
		return i;
	}
	
	/**
	 * Deletes content from the database using the where clause.
	 * 
	 * @param whereClause The where clause to use when updating
	 * @param whereArgs The arguments used to expand the whereClause
	 * @return How many rows have been changed
	 */
	public int delete(String whereClause, String[] whereArgs) {
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		int i = db.delete(mTable, whereClause, whereArgs);
		db.close();
		return i;
	}
	
	/**
	 * Selects content from the database.
	 * 
	 * @param selection The text for the SELECT part of the query
	 * @param selectionArgs The arguments used to expand the selection
	 * @param groupBy The text for the GROUP BY part
	 * @param having The text for the HAVING part
	 * @param orderBy The text for the ORDER BY part
	 * @param limit The text for the LIMIT part
	 * @param distinct True if only distinct items should be returns
	 * @return An array of Content types, created using fromContentValues
	 */
	@SuppressWarnings("unchecked")
	public T[] select(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, boolean distinct) {
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		ContentCursor<T> cursor = (ContentCursor<T>) db.query(distinct, mTable,
				null, selection, selectionArgs, groupBy, having, orderBy,
				limit);
				
		List<T> items = new ArrayList<T>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			items.add(cursor.toContent());
			cursor.moveToNext();
		}
		
		cursor.close();
		db.close();
		
		Object array = Array.newInstance(mClass, items.size());
		return items.toArray((T[]) array);
	}
	
	/**
	 * Same as select, but returns only the first item, or null.
	 * 
	 * @return The first content item, or null
	 */
	public T selectOne(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		T[] items = select(selection, selectionArgs, groupBy, having, orderBy, "0, 1", false);
		if (items.length > 0) {
			return items[0];
		}
		
		return null;
	}
	
	/**
	 * Executes a select query which would return every single item in the
	 * table.
	 * 
	 * @return An array of every item in the table.
	 */
	public T[] selectAll() {
		return select(null, null, null, null, null, null, false);
	}
	
}
