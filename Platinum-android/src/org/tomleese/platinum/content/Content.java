package org.tomleese.platinum.content;

import android.content.ContentValues;

/**
 * An interface for working with content internal to an application. Content,
 * and its various classes, are designed to be used when putting content into a
 * database.
 * 
 * For this to work properly, a Content type must have an empty constructor
 * available.
 * 
 * @author Tom Leese
 */
public interface Content {
	
	/**
	 * Converts a Content instance into a ContentValues instance.
	 * 
	 * @param values The ContentValues to fill
	 */
	public void toContentValues(ContentValues values);
	
	/**
	 * Converts a ContentValues instance into a Content.
	 * 
	 * @param values The ContentValues to fill from
	 */
	public void fromContentValues(ContentValues values);
	
}
