package org.tomleese.platinum.app;

import org.tomleese.platinum.R;
import org.tomleese.platinum.utils.ResourceUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An activity that provides a simple splash screen with an image and label
 * that fades in and out.
 * 
 * @author Tom Leese
 */
public class SplashActivity extends Activity {
	
	protected static final String TAG = "SplashActivity";
	protected static final String METADATA_SPLASH = "org.tomleese.platinum.splash";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTheme(android.R.style.Theme_Black_NoTitleBar);
		setContentView(R.layout.activity_splash);
		
		ImageView imageView = (ImageView) findViewById(R.id.activity_splash_image);
		TextView textView = (TextView) findViewById(R.id.activity_splash_caption);
		
		try {
			ActivityInfo app = getPackageManager().getActivityInfo(getComponentName(),
					PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA);
			Bundle metaData = app.metaData;
						
			final int res = metaData.getInt(METADATA_SPLASH, 0);
			XmlResourceParser parser = getResources().getXml(res);
			AttributeSet attrs = ResourceUtils.readAttributeSetFromElement(parser, "splash");
			
			for (int i = 0; i < attrs.getAttributeCount(); i++) {
				String name = attrs.getAttributeName(i);
				if (name.equals("drawable")) {
					imageView.setImageResource(attrs.getAttributeResourceValue(i, 0));
				} else if (name.equals("caption")) {
					String caption = ResourceUtils.getAttributeGetString(this, attrs, i);
					if (caption == null) {
						textView.setVisibility(View.GONE);
					} else {
						textView.setText(caption);
					}
				} else if (name.equals("activity")) {
					Class<?> klass = Class.forName(attrs.getAttributeValue(i));
					final Intent nextIntent = new Intent(this, klass);
					
					new Handler().postDelayed(new Runnable() {
				        public void run() {
				            startActivity(nextIntent);
				            finish();
				            
				            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				        }
				    }, 1600);
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));
	}
	
}
