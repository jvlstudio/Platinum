package org.tomleese.platinum.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColourSatValSelector extends View {
	
	private Paint mPaint = new Paint();
	private float mHue, mSat, mVal = 1f;
	private OnSatValSelectedListener mListener;
	
	public static abstract class OnSatValSelectedListener {
		
		public void onSaturation(float sat) { }
		public void onValue(float val) { }
		
		public abstract void onColour(int colour);
		
	}
	
	public ColourSatValSelector(Context context) {
		super(context);
	}

	public ColourSatValSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ColourSatValSelector(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setHue(float hue) {
		mHue = hue;
		invalidate();
	}
	
	public void setOnSatValSelectedListener(OnSatValSelectedListener listener) {
		mListener = listener;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		final int rgb = Color.HSVToColor(new float[] { mHue, 1f, 1f });
		Shader value = new LinearGradient(0, 0, 0, this.getMeasuredHeight(),
				Color.WHITE, Color.BLACK, TileMode.CLAMP);
		Shader sat = new LinearGradient(0, 0, this.getMeasuredWidth(), 0,
				Color.WHITE, rgb, TileMode.CLAMP);
		
		ComposeShader shader = new ComposeShader(value, sat,
				PorterDuff.Mode.MULTIPLY);
		
		mPaint.setShader(shader);
		
		canvas.drawRect(0, 0, this.getMeasuredWidth(),
				this.getMeasuredHeight(), mPaint);
		
		mPaint.setShader(null);
		
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(2);
		
		final float x = mSat / 1f * getMeasuredWidth();
		final float y = (1f - mVal / 1f) * getMeasuredHeight();
		final float m = 4;
		canvas.drawLine(x, y - m, x, y + m, mPaint);
		canvas.drawLine(x - m, y, x + m, y, mPaint);
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
	
	public void setValue(float val) {
		mVal = val;
		
		if (mListener != null) {
			mListener.onValue(val);
		}
	}

	public void setSaturation(float sat) {
		mSat = sat;
		
		if (mListener != null) {
			mListener.onSaturation(sat);
		}
	}
	
	public int getColour() {
		return Color.HSVToColor(new float[] { mHue, mSat, mVal });
	}
	
}
