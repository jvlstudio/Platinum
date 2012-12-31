package org.tomleese.platinum.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class DetailActivity extends FragmentActivity implements MultiPaneActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (isMultiPane()) {
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		// so when going back, and switching to multi-pane view, we wouldn't get the item selected
		startMasterDetailActivity();
	}
	
	public abstract void startMasterDetailActivity();
	
}
