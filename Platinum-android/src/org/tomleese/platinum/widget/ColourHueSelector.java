package org.tomleese.platinum.widget;

import org.tomleese.platinum.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ColourHueSelector extends ImageView {
	
	private OnHueSelectedListener mListener;
	private float mHue = 0;
	private Paint mPaint = new Paint();
	
	public static interface OnHueSelectedListener {

		public void onHue(float hue);

	}

	public ColourHueSelector(Context context) {
		super(context);
		
		setImageResource(R.drawable.hue);
		setScaleType(ScaleType.FIT_XY);
	}

	public ColourHueSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setImageResource(R.drawable.hue);
		setScaleType(ScaleType.FIT_XY);
	}

	public ColourHueSelector(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setImageResource(R.drawable.hue);
		setScaleType(ScaleType.FIT_XY);
	}
	
	public void setOnHueSelectedListener(OnHueSelectedListener listener) {
		mListener = listener;
	}
	
	public float getHue() {
		return mHue;
	}

	public void setHue(float hue) {
		mHue = hue;
		
		if (mListener != null) {
			mListener.onHue(mHue);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN
				|| event.getActionMasked() == MotionEvent.ACTION_UP
				|| event.getActionMasked() == MotionEvent.ACTION_MOVE) {
			float y = event.getY();
			
			if (y < 0) {
				y = 0;
			}
			
			if (y > getMeasuredHeight()) {
				y = getMeasuredHeight();
			}
			
			setHue(360f / getMeasuredHeight() * y);
			invalidate();
			
			return true;
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		final float y = mHue / 360f * getMeasuredHeight();
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(2);
		canvas.drawLine(0, y, getMeasuredWidth(), y, mPaint);
	}
	
}
