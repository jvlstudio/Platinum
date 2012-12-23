package org.tomleese.platinum.widget;

import org.tomleese.platinum.R;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.CheckBox;

public class StarButton extends CheckBox {
	
	public StarButton(Context context) {
		super(context);
		
		initialise();
	}
	
	public StarButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initialise();
	}
	
	public StarButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		initialise();
	}
	
	private void initialise() {
		setButtonDrawable(R.drawable.btn_star);
		
		post(new Runnable() {
			
			@Override
			public void run() {
				int margin = 50;
				
				Rect rect = new Rect();
				getHitRect(rect);
				
				rect.left -= margin;
				rect.top -= margin;
				rect.right += margin;
				rect.bottom += margin;
				
				TouchDelegate delegate = new TouchDelegate(rect, StarButton.this);
				
				if (getParent() instanceof View) {
					((View) getParent()).setTouchDelegate(delegate);
				}
			}
		});
	}
	
}
