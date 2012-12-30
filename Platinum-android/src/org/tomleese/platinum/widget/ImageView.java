package org.tomleese.platinum.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class ImageView extends android.widget.ImageView {
	
	private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
			mMatrix.postTranslate(-dx, -dy);
			setImageMatrix(mMatrix);
			
			invalidate();
			return true;
		}
		
	};
	
	private SimpleOnScaleGestureListener mScaleListener = new SimpleOnScaleGestureListener() {
		
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float s = detector.getScaleFactor();
			float px = detector.getFocusX();
			float py = detector.getFocusY();
			
			mMatrix.postScale(s, s, px, py);
			setImageMatrix(mMatrix);
			
			invalidate();
			return true;
		}
		
	};
		
	private GestureDetector mGestureDetector;
	private ScaleGestureDetector mScaleDetector;
	
	private Matrix mMatrix = new Matrix();
	
	public ImageView(Context context) {
		super(context);
		
		setImageMatrix(mMatrix);
		setScaleType(ScaleType.MATRIX);
		
		mGestureDetector = new GestureDetector(getContext(), mGestureListener);
		mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
	}
	
	public ImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setImageMatrix(mMatrix);
		setScaleType(ScaleType.MATRIX);
		
		mGestureDetector = new GestureDetector(getContext(), mGestureListener);
		mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
	}
	
	public ImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setImageMatrix(mMatrix);
		setScaleType(ScaleType.MATRIX);
		
		mGestureDetector = new GestureDetector(getContext(), mGestureListener);
		mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		mGestureDetector.onTouchEvent(event);
		
		return true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}
