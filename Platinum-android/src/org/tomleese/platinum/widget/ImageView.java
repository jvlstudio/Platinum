package org.tomleese.platinum.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

/**
 * An ImageView which supports panning and zooming.
 * 
 * @author Tom Leese
 */
public class ImageView extends android.widget.ImageView {
	
	private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (mClickListener != null) {
				mClickListener.onClick(ImageView.this);
			}
			
			return true;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			if (mLongClickListener != null) {
				mLongClickListener.onLongClick(ImageView.this);
			}
		}
		
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

	private OnClickListener mClickListener;
	private OnLongClickListener mLongClickListener;
	
	public ImageView(Context context) {
		super(context);
		
		initialise();
	}
	
	public ImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initialise();
	}
	
	public ImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		initialise();
	}
	
	private void initialise() {
		setImageMatrix(mMatrix);
		setScaleType(ScaleType.MATRIX);
		
		mGestureDetector = new GestureDetector(getContext(), mGestureListener);
		mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		
		mClickListener = l;
	}
	
	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		super.setOnLongClickListener(l);
		
		mLongClickListener = l;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		boolean a = mScaleDetector.onTouchEvent(event);
		boolean b = mGestureDetector.onTouchEvent(event);
		
		if (a || b) {
			return true;
		} else {
			return false;
		}
	}
	
}
