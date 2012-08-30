package org.tomleese.platinum.ui;

import org.tomleese.platinum.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An activity that provides a simple splash screen with an image that fades in and out.
 * 
 * @author tom
 */
public abstract class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTheme(android.R.style.Theme_Black_NoTitleBar);
		setContentView(R.layout.activity_splash);
		
		ImageView imageView = (ImageView) findViewById(R.id.activity_splash_image);
		TextView textView = (TextView) findViewById(R.id.activity_splash_caption);
		
		imageView.setImageDrawable(getDrawable());
		imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));
		
		String caption = getCaption();
		if (caption == null) {
			textView.setVisibility(View.GONE);
		} else {
			textView.setText(caption);
			textView.setTextColor(0xFFFFFF);
		}
		
		new Handler().postDelayed(new Runnable() {
	        public void run() {
	            startActivity(getNextIntent());
	            finish();

	            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	        }
	    }, 1600);
	}
	
	/**
	 * @return The intent to be started after the splash screen.
	 */
	public abstract Intent getNextIntent();
	
	/**
	 * @return The {@link Drawable} to be used in the splash screen.
	 */
	public abstract Drawable getDrawable();
	
	/**
	 * @return The {@link String} to be used in the caption, can be null.
	 */
	public abstract String getCaption();
		
}
