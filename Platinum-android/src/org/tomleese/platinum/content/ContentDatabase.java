package org.tomleese.platinum.content;

import org.tomleese.platinum.content.Content.ContentCreator;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class ContentDatabase<T extends Content> extends SQLiteOpenHelper {
	
	private ContentManager<T> mManager;
	
	public ContentDatabase(Context context, String name, int version, ContentCreator<T> creator) {
		super(context, name, null, version);
		
		mManager = new ContentManager<T>(this, name, creator);
	}
	
	public ContentDatabase(Context context, String name, int version, Class<T> klass) {
		super(context, name, null, version);
		
		mManager = new ContentManager<T>(this, name, klass);
	}
	
	public long insert(T content) {
		return mManager.insert(content);
	}
	
	public int update(T content, String selection, String[] selectionArgs) {
		return mManager.update(content, selection, selectionArgs);
	}
	
	public int delete(String selection, String[] selectionArgs) {
		return mManager.delete(selection, selectionArgs);
	}
	
	public T[] select(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, boolean distinct) {
		return mManager.select(selection, selectionArgs, groupBy, having, orderBy, limit, distinct);
	}
	
	public T[] selectAll() {
		return mManager.selectAll();
	}
	
	public T selectOne(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return mManager.selectOne(selection, selectionArgs, groupBy, having, orderBy);
	}
	
}
