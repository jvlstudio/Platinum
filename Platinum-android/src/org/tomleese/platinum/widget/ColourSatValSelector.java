package org.tomleese.platinum.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader.TileMode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A Saturation and Value colour selector for colours.
 * 
 * @author Tom Leese
 */
public class ColourSatValSelector extends View {
	
	private Paint mPaint = new Paint();
	private float mHue, mSat, mVal = 1f;
	private OnSatValSelectedListener mListener;
	
	private LinearGradient mSatShader;
	private LinearGradient mValueShader;
	private ComposeShader mComposeShader;
	
	/**
	 * A callback listener for when a colour has been chosen.
	 * 
	 * @author Tom Leese
	 */
	public static abstract class OnSatValSelectedListener {
		
		/**
		 * Called when a saturation has been chosen.
		 * 
		 * @param sat The saturation
		 */
		public void onSaturation(float sat) {
			
		}
		
		/**
		 * Called when a value has been chosen.
		 * 
		 * @param val The value
		 */
		public void onValue(float val) {
			
		}
		
		/**
		 * Called when a colour has been chosen.
		 * 
		 * @param colour The colour
		 */
		public abstract void onColour(int colour);
		
	}
	
	@SuppressLint("NewApi")
	public ColourSatValSelector(Context context) {
		super(context);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		    setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}
	
	@SuppressLint("NewApi")
	public ColourSatValSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		    setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}
	
	@SuppressLint("NewApi")
	public ColourSatValSelector(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		    setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}
	
	/**
	 * Sets the hue value for the saturation and value.
	 * 
	 * @param hue Sets the hue value
	 */
	public void setHue(float hue) {
		mHue = hue;
		invalidate();
	}
	
	/**
	 * Sets the OnSatValSelectedListener for this view.
	 * 
	 * @param listener The OnSatValSelectedListener
	 */
	public void setOnSatValSelectedListener(OnSatValSelectedListener listener) {
		mListener = listener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setShader(mComposeShader);
		
		canvas.drawRect(0, 0, this.getMeasuredWidth(),
				this.getMeasuredHeight(), mPaint);
		
		mPaint.setShader(null);
		
		mPaint.setColor(mVal >= 0.5 ? Color.BLACK : Color.WHITE);
		mPaint.setStrokeWidth(2);
		
		final float x = mSat / 1f * getMeasuredWidth();
		final float y = (1f - mVal / 1f) * getMeasuredHeight();
		final float m = 4;
		canvas.drawLine(x, y - m, x, y + m, mPaint);
		canvas.drawLine(x - m, y, x + m, y, mPaint);
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		
		final int rgb = Color.HSVToColor(new float[] { mHue, 1f, 1f });
		
		mValueShader = new LinearGradient(0, 0, 0, this.getMeasuredHeight(),
				Color.WHITE, Color.BLACK, TileMode.CLAMP);
		mSatShader = new LinearGradient(0, 0, this.getMeasuredWidth(), 0,
				Color.WHITE, rgb, TileMode.CLAMP);
		
		mComposeShader = new ComposeShader(mValueShader, mSatShader,
				PorterDuff.Mode.MULTIPLY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN
				|| event.getActionMasked() == MotionEvent.ACTION_UP
				|| event.getActionMasked() == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();
			
			if (x < 0) {
				x = 0;
			}
			
			if (x > getMeasuredWidth()) {
				x = getMeasuredWidth();
			}
			
			if (y < 0) {
				y = 0;
			}
			
			if (y > getMeasuredHeight()) {
				y = getMeasuredHeight();
			}
			
		
			setSaturation(1f / getMeasuredWidth() * x);
			setValue(1f - (1f / getMeasuredHeight() * y));
			
			if (mListener != null) {
				mListener.onColour(getColour());
			}
			
			invalidate();
			
			return true;
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * Sets the current value.
	 * 
	 * @param val The value
	 */
	public void setValue(float val) {
		mVal = val;
		
		if (mListener != null) {
			mListener.onValue(val);
		}
	}
	
	/**
	 * Sets the current saturation.
	 * 
	 * @param sat The saturation
	 */
	public void setSaturation(float sat) {
		mSat = sat;
		
		if (mListener != null) {
			mListener.onSaturation(sat);
		}
	}
	
	/**
	 * Returns the currently selected colour.
	 * 
	 * @return The currently selected colour
	 */
	public int getColour() {
		return Color.HSVToColor(new float[] { mHue, mSat, mVal });
	}
	
}
