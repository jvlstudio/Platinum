package org.tomleese.platinum.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.AttributeSet;
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
	
}
