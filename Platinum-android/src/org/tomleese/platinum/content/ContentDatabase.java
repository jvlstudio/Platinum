package org.tomleese.platinum.content;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A SQLiteOpenHelper which wraps a ContentManager for ease of use.
 * 
 * @author Tom Leese
 *
 * @param <T> The content type which extends Content
 */
public abstract class ContentDatabase<T extends Content> extends SQLiteOpenHelper {
	
	private ContentManager<T> mManager;
	
	/**
	 * Constructs a new ContentDatabase.
	 * 
	 * @param context The context for the {@link SQLiteOpenHelper}
	 * @param name The table and database name
	 * @param version The database version for the {@link SQLiteOpenHelper}
	 * @param klass The class for the {@link ContentManager}
	 */
	public ContentDatabase(Context context, String name, int version, Class<T> klass) {
		this(context, name, name, version, klass);
	}
	
	/**
	 * Constructs a new ContentDatabase.
	 * 
	 * @param contextThe context for the {@link SQLiteOpenHelper}
	 * @param dbName The database name for the {@link SQLiteOpenHelper}
	 * @param tableName The table name for the {@link ContentManager}
	 * @param version The version for the {@link SQLiteOpenHelper}
	 * @param klass The class for the {@link ContentManager}
	 */
	public ContentDatabase(Context context, String dbName, String tableName, int version, Class<T> klass) {
		super(context, dbName, null, version);
		
		mManager = new ContentManager<T>(this, tableName, klass);
	}
	
	/**
	 * @see {@link ContentManager}
	 */
	public long insert(T content) {
		return mManager.insert(content);
	}
	
	/**
	 * @see {@link ContentManager}
	 */
	public int update(T content, String selection, String[] selectionArgs) {
		return mManager.update(content, selection, selectionArgs);
	}
	
	/**
	 * @see {@link ContentManager}
	 */
	public int delete(String selection, String[] selectionArgs) {
		return mManager.delete(selection, selectionArgs);
	}
	
	/**
	 * @see {@link ContentManager}
	 */
	public T[] select(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, boolean distinct) {
		return mManager.select(selection, selectionArgs, groupBy, having, orderBy, limit, distinct);
	}
	
	/**
	 * @see {@link ContentManager}
	 */
	public T[] selectAll() {
		return mManager.selectAll();
	}
	
	/**
	 * @see {@link ContentManager}
	 */
	public T selectOne(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return mManager.selectOne(selection, selectionArgs, groupBy, having, orderBy);
	}
	
}
