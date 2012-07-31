package org.tomleese.platinum.ui;

import org.tomleese.platinum.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * An activity that provides a simple splash screen with an image that fades in and out.
 * 
 * @author tom
 */
public abstract class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(android.R.style.Theme_Black);
		
		ImageView imageView = new ImageView(this);
		setContentView(imageView);
		
		imageView.setImageDrawable(getDrawable());
		imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));
		
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
		
}
