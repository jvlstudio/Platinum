package org.tomleese.platinum.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * A set of utilities for working with bitmaps.
 * 
 * @author Tom Leese
 */
public abstract class BitmapUtils {

	/**
	 * Returns a scaled version of the bitmap by providing the maximum width and
	 * height. This will ensure the bitmap stays in the correct width and height
	 * ratio.
	 * 
	 * Depending on maxWidth and maxHeight, the returned Bitmap can be exactly
	 * the same as the original bitmap provided. This is to save memory on the
	 * device.
	 * 
	 * @param original The original bitmap to transform
	 * @param maxWidth The new maximum width
	 * @param maxHeight The new maximum height
	 * @return The scaled bitmap
	 */
	public static Bitmap getScaledBitmap(Bitmap original, int maxWidth,
			int maxHeight) {
		if (maxWidth <= 0) {
			maxWidth = original.getWidth();
		}

		if (maxHeight <= 0) {
			maxHeight = original.getHeight();
		}

		int width = original.getWidth();
		int height = original.getHeight();

		if (maxWidth >= width && maxHeight >= height) {
			return original;
		}

		float scalex = (float) maxWidth / (float) width;
		float scaley = (float) maxHeight / (float) height;
		float scale = scalex > scaley ? scaley : scalex;

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
	}
	
}
