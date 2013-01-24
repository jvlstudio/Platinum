package org.tomleese.platinum.content;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class ContentCursorAdapter<T extends Content> extends CursorAdapter {

	private Context mContext;
	private ContentFactory<T> mFactory;
	
	public ContentCursorAdapter(Context context, ContentFactory<T> factory, Cursor c, int flags) {
		super(context, c, flags);
		
		mContext = context;
		mFactory = factory;
	}
	
	public abstract void bindView(View view, Context context, T content, Cursor cursor);
	public abstract View newView(Context context, T content, Cursor cursor, ViewGroup parent);
	
	public Context getContext() {
		return mContext;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		T content = mFactory.newInstance(cursor);
		bindView(view, context, content, cursor);
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		T content = mFactory.newInstance(cursor);
		return newView(context, content, cursor, parent);
	}
	
}
