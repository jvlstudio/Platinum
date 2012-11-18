package org.tomleese.platinum.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

/**
 * A set of utilities for working with resources.
 * 
 * @author Tom Leese
 */
public abstract class ResourceUtils {
	
	protected static final String TAG = "ResourceUtils";

	/**
	 * Returns an AttributeSet by searching through the parser until the element is found.
	 * 
	 * @param parser The parser to search through
	 * @param element The element to create an attribute set from
	 * @return A new AttributeSet
	 */
	public static AttributeSet readAttributeSetFromElement(XmlPullParser parser, String element) {
		int state = 0;
		do {
			try {
				state = parser.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (state == XmlPullParser.START_TAG) {
	            if (parser.getName().equals(element)) {
	                return Xml.asAttributeSet(parser);
	            }
	        }
		} while (state != XmlPullParser.END_DOCUMENT);
		
		return null;
	}
	
	/**
	 * Returns a string from an attribute set. If the value is a resource, it will convert that into a string before returning.
	 * 
	 * @param context The context for getting the string
	 * @param attrs The attribute set to look for the string
	 * @param namespace The namespace of the value to search for
	 * @param name The name of the value to search for
	 * @return The string, or null
	 */
	@Deprecated
	public static String getAttributeGetString(Context context, AttributeSet attrs, String namespace, String name) {
		int res = attrs.getAttributeResourceValue(namespace, name, 0);
		if (res != 0) {
			return context.getString(res);
		}
		
		return attrs.getAttributeValue(namespace, name);
	}
	
	/**
	 * Returns a string from an attribute set. If the value is a resource, it will convert that into a string before hand.
	 * 
	 * @param context The context for getting the string
	 * @param attrs The attribute set to look for the string
	 * @param index The index of the attribute in the set
	 * @return The string, or null
	 */
	@Deprecated
	public static String getAttributeGetString(Context context, AttributeSet attrs, int index) {
		Log.d(TAG, attrs.getAttributeValue(index));
		
		int res = attrs.getAttributeResourceValue(index, 0);
		if (res != 0) {
			return context.getString(res);
		}
		
		return attrs.getAttributeValue(index);
	}
	
}
