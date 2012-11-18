package org.tomleese.platinum.widget;

import org.tomleese.platinum.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A button view that provides two triangles side by side. 
 * 
 * @author Tom Leese
 */
public class DiagonalButtons extends View {
	
	protected static final String TAG = "DiagonalButtons";
	
	public static enum ButtonPosition {
		NorthEast,
		NorthWest,
		SouthEast,
		SouthWest
	}
	
	private OnClickListener mBtn1Listener, mBtn2Listener;
	private Bitmap mButtonBitmap1, mButtonBitmap2;
	private boolean mBeingTouched1 = false, mBeingTouched2 = false;
	
	private int mBackgroundRes1, mBackgroundRes2;
	private int mIconRes1, mIconRes2;
	
	private ButtonPosition mButton1Position = ButtonPosition.NorthEast,
			mButton2Position = ButtonPosition.SouthWest;
	
	private Paint mPaint = new Paint();
	private PorterDuffColorFilter mPressedFilter;
	
	public DiagonalButtons(Context context) {
		this(context, null, 0);
	}
	
	public DiagonalButtons(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public DiagonalButtons(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		mPressedFilter = new PorterDuffColorFilter(Color.GRAY, Mode.MULTIPLY);
		
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiagonalButtons, 0, 0);
			
			try {
				mBackgroundRes1 = a.getResourceId(R.styleable.DiagonalButtons_background1, 0);
				mBackgroundRes2 = a.getResourceId(R.styleable.DiagonalButtons_background2, 0);
				mIconRes1 = a.getResourceId(R.styleable.DiagonalButtons_icon1, 0);
				mIconRes2 = a.getResourceId(R.styleable.DiagonalButtons_icon2, 0);
			} finally {
				a.recycle();
			}
		}
	}
	
	private void recreateButtonBitmaps(int width, int height) {
		if (mButtonBitmap1 != null)
			mButtonBitmap1.recycle();
		
		if (mButtonBitmap2 != null)
			mButtonBitmap2.recycle();
		
		mButtonBitmap1 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mButtonBitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	}
	
	private Path createButtonPath(int width, int height, ButtonPosition pos) {
		Path path = new Path();
		
		switch (pos) {
			case NorthEast:
				path.moveTo(0,		0);
				path.lineTo(width,	height);
				path.lineTo(width,	0);
				path.lineTo(0,		0);
				break;
				
			case NorthWest:
				path.moveTo(width,	0);
				path.lineTo(0,		height);
				path.lineTo(0,		0);
				path.lineTo(width,	0);
				break;
				
			case SouthEast:
				path.moveTo(width,	0);
				path.lineTo(0,		height);
				path.lineTo(width,	height);
				path.lineTo(width,	0);
				break;
				
			case SouthWest:
				path.moveTo(0,		0);
				path.lineTo(width,	height);
				path.lineTo(0,		height);
				path.lineTo(0,		0);
				break;
		}
		
		return path;
	}
	
	private void fillBackground(Canvas canvas, int res) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
		
		for (int x = 0; x < canvas.getWidth(); x += bitmap.getWidth()) {
			for (int y = 0; y < canvas.getHeight(); y += bitmap.getHeight()) {
				canvas.drawBitmap(bitmap, x, y, mPaint);
			}
		}
	}
	
	private void drawIcon(Canvas canvas, int res, ButtonPosition pos, int width, int height) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
		
		float exRectangleRatio = (float) height / (float) width;
		float inRectangleRatio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
		
		float left = 0f, top = 0f, bottom = 0f, right = 0f;
		
		float newWidth = width * exRectangleRatio / (exRectangleRatio + inRectangleRatio);
		float newHeight = newWidth * inRectangleRatio;
				
		switch (pos) {
			case NorthEast:
				top = 0;
				bottom = newHeight;
				right = width;
				left = right - newWidth;
				break;
				
			case NorthWest:
				top = 0;
				bottom = newHeight;
				left = 0;
				right = newWidth;
				break;
				
			case SouthEast:
				bottom = height;
				top = bottom - newHeight;
				right = width;
				left = right - newWidth;
				break;
				
			case SouthWest:
				bottom = height;
				top = bottom - newHeight;
				left = 0;
				right = newWidth;
				break;
		}
		
		Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
		canvas.drawBitmap(bitmap, null, rect, mPaint);
	}
	
	private void recreateButtons() {
		int viewWidth = getWidth();
		int viewHeight = getHeight();
		
		if (viewWidth <= 0 || viewHeight <= 0) {
			return;
		}
		
		recreateButtonBitmaps(viewWidth, viewHeight);
		
		Canvas canvas1 = new Canvas(mButtonBitmap1);
		Path path1 = createButtonPath(viewWidth, viewHeight, mButton1Position);
		canvas1.clipPath(path1);
		fillBackground(canvas1, mBackgroundRes1);
		drawIcon(canvas1, mIconRes1, mButton1Position, viewWidth, viewHeight);
		
		Canvas canvas2 = new Canvas(mButtonBitmap2);
		Path path2 = createButtonPath(viewWidth, viewHeight, mButton2Position);
		canvas2.clipPath(path2);
		fillBackground(canvas2, mBackgroundRes2);
		drawIcon(canvas2, mIconRes2, mButton2Position, viewWidth, viewHeight);
	}
	
	@Override
	public void onMeasure(int widthSpec, int heightSpec) {
		super.onMeasure(widthSpec, heightSpec);
		
		recreateButtons();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if (mBeingTouched1) {
			mPaint.setColorFilter(mPressedFilter);
		} else {
			mPaint.setColorFilter(null);
		}
		
		canvas.drawBitmap(mButtonBitmap1, 0, 0, mPaint);
		
		if (mBeingTouched2) {
			mPaint.setColorFilter(mPressedFilter);
		} else {
			mPaint.setColorFilter(null);
		}
		
		canvas.drawBitmap(mButtonBitmap2, 0, 0, mPaint);
	}
	
	private boolean inTriangle(ButtonPosition pos, float x, float y) {
		Path path = createButtonPath(getWidth(), getHeight(), pos);
		
		RectF bounds = new RectF();
		path.computeBounds(bounds, true);
		
		Rect rect = new Rect((int) bounds.left, (int) bounds.top,
				(int) bounds.right, (int) bounds.bottom);
		
		Region region = new Region();
		region.setPath(path, new Region(rect));
		return region.contains((int) x, (int) y);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final float x = event.getX();
		final float y = event.getY();
		
		if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
			if (inTriangle(mButton1Position, x, y)) {
				mBeingTouched1 = true;
				mBeingTouched2 = false;
			} else if (inTriangle(mButton2Position, x, y)) {
				mBeingTouched1 = false;
				mBeingTouched2 = true;
			} else {
				mBeingTouched1 = false;
				mBeingTouched2 = false;
			}
			
			invalidate();
		} else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			mBeingTouched1 = false;
			mBeingTouched2 = false;
			
			if (inTriangle(mButton1Position, x, y)) {
				mBtn1Listener.onClick(this);
			} else if (inTriangle(mButton2Position, x, y)) {
				mBtn2Listener.onClick(this);
			}
			
			invalidate();
		}
		
		return true;
	}
	
	public void setOnButton1ClickListener(OnClickListener listener) {
		mBtn1Listener = listener;
	}
	
	public void setOnButton2ClickListener(OnClickListener listener) {
		mBtn2Listener = listener;
	}
	
	public void setButton1Position(ButtonPosition pos) {
		mButton1Position = pos;
		
		switch (pos) {
			case NorthEast:
				mButton2Position = ButtonPosition.SouthWest;
				break;
			
			case NorthWest:
				mButton2Position = ButtonPosition.SouthEast;
				break;
			
			case SouthEast:
				mButton2Position = ButtonPosition.NorthWest;
				break;
				
			case SouthWest:
				mButton2Position = ButtonPosition.NorthEast;
				break;
		}
		
		recreateButtons();
		invalidate();
		requestLayout();
	}
	
	public void setButton2Position(ButtonPosition pos) {
		mButton2Position = pos;
		
		switch (pos) {
			case NorthEast:
				mButton1Position = ButtonPosition.SouthWest;
				break;
			
			case NorthWest:
				mButton1Position = ButtonPosition.SouthEast;
				break;
			
			case SouthEast:
				mButton1Position = ButtonPosition.NorthWest;
				break;
				
			case SouthWest:
				mButton1Position = ButtonPosition.NorthEast;
				break;
		}
		
		recreateButtons();
		invalidate();
		requestLayout();
	}
	
	public void setBackgroundResource1(int r) {
		mBackgroundRes1 = r;
		recreateButtons();
		invalidate();
		requestLayout();
	}
	
	public void setBackgroundResource2(int r) {
		mBackgroundRes2 = r;
		recreateButtons();
		invalidate();
		requestLayout();
	}
	
	public void setIconResource1(int r) {
		mIconRes1 = r;
		recreateButtons();
		invalidate();
		requestLayout();
	}
	
	public void setIconResource2(int r) {
		mIconRes2 = r;
		recreateButtons();
		invalidate();
		requestLayout();
	}
	
}
