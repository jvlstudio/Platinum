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
 * <p>An activity that provides a simple splash screen with an image and label that
 * fades in and out.</p>
 * 
 * <p>To configure this splash screen you should pass an xml resource to the
 * meta-data of this activity like this:</p>
 * 
 * <pre>
 * {@code <meta-data android:name="org.tomleese.platinum.splash" android:resource="@xml/splash" />}
 * </pre>
 * 
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="utf-8"?>
 * <splash xmlns:platinum="http://schemas.android.com/apk/res-auto"
 *     platinum:caption="@string/caption" platinum:drawable="@drawable/logo"
 *     platinum:activity="com.example.app.MainActivity">
 * </splash>
 * }
 * </pre>
 * 
 * @author Tom Leese
 */
public class SplashActivity extends Activity {
	
	protected static final String TAG = "SplashActivity";
	protected static final String METADATA = "org.tomleese.platinum.splash";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTheme(android.R.style.Theme_Black_NoTitleBar);
		setContentView(R.layout.activity_splash);
		
		ImageView imageView = (ImageView) findViewById(R.id.activity_splash_image);
		TextView textView = (TextView) findViewById(R.id.activity_splash_caption);
		
		try {
			int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA;
			ActivityInfo app = getPackageManager().getActivityInfo(getComponentName(), flags);
			Bundle metaData = app.metaData;
			
			final int res = metaData.getInt(METADATA, 0);
			XmlResourceParser parser = getResources().getXml(res);
			AttributeSet attrs = ResourceUtils.readAttributeSetFromElement(parser, "splash");
			
			for (int i = 0; i < attrs.getAttributeCount(); i++) {
				String name = attrs.getAttributeName(i);
				if (name.equals("drawable")) {
					imageView.setImageResource(attrs.getAttributeResourceValue(i, 0));
				} else if (name.equals("caption")) {
					String caption = getCaption(attrs, i);
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
	
	private String getCaption(AttributeSet attrs, int index) {
		int res = attrs.getAttributeResourceValue(index, 0);
		if (index == 0) {
			return attrs.getAttributeValue(index);
		}
		
		return getString(res);
	}
	
}
