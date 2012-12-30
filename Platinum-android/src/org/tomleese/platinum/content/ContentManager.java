package org.tomleese.platinum.content;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.tomleese.platinum.content.Content.ContentCreator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContentManager<T extends Content> {
	
	private SQLiteOpenHelper mDatabase;
	private String mTable;
	private ContentCreator<T> mCreator;
	
	public ContentManager(SQLiteOpenHelper db, String table, ContentCreator<T> creator) {
		mDatabase = db;
		mTable = table;
		mCreator = creator;
	}
	
	public ContentManager(SQLiteOpenHelper db, String table, final Class<T> klass) {
		mDatabase = db;
		mTable = table;
		
		mCreator = new ContentCreator<T>() {
			
			@Override
			public T newInstance() {
				try {
					return klass.newInstance();
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			
			@Override
			@SuppressWarnings("unchecked")
			public T[] newArray(int size) {
				return (T[]) Array.newInstance(klass, size);
			}
			
		};
	}
	
	public long insert(T content) {
		ContentValues values = new ContentValues();
		content.toContentValues(values);
		
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		long i = db.insert(mTable, null, values);
		db.close();
		return i;
	}
	
	public int update(T content, String selection, String[] selectionArgs) {
		ContentValues values = new ContentValues();
		content.toContentValues(values);
		
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		int i = db.update(mTable, values, selection, selectionArgs);
		db.close();
		return i;
	}
	
	public int delete(String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		int i = db.delete(mTable, selection, selectionArgs);
		db.close();
		return i;
	}
	
	public T[] select(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, boolean distinct) {
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		Cursor cursor = db.query(distinct, mTable, null, selection, selectionArgs, groupBy, having, orderBy, limit);
		
		ContentValues values = new ContentValues();
		
		List<T> items = new ArrayList<T>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			T content = mCreator.newInstance();
			
			values.clear();
			DatabaseUtils.cursorRowToContentValues(cursor, values);
			content.fromContentValues(values);
			
			items.add(content);
			
			cursor.moveToNext();
		}
		
		cursor.close();
		db.close();
		
		return items.toArray(mCreator.newArray(items.size()));
	}
	
	public T selectOne(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		T[] items = select(selection, selectionArgs, groupBy, having, orderBy, "0, 1", false);
		if (items.length > 0) {
			return items[0];
		}
		
		return null;
	}
	
	public T[] selectAll() {
		return select(null, null, null, null, null, null, false);
	}
	
}
