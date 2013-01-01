package org.tomleese.platinum.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * A ListFragment that provides a standard item selection pane on a typical
 * Multi-Pane app. If the host activity implements a MultiPaneActivity, this
 * list view will use CHOICE_MODE_SINGLE if isMultiPane() returns true.
 * 
 * @author Tom Leese
 */
public abstract class MasterListFragment extends ListFragment implements MasterFragment {
	
	private static final String TAG = "MasterListFragment";
	private static final String SAVE_CURRENT_POSITION = "org.tomleese.platinum.current_position";
	
	private int mCurPosition = -1;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		onPopulateItems();
		
		if (getActivity() instanceof MultiPaneActivity) {
			if (((MultiPaneActivity) getActivity()).isMultiPane()) {
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			}
		}
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
				
		if (savedInstanceState != null) {
			mCurPosition = savedInstanceState.getInt(SAVE_CURRENT_POSITION, mCurPosition);
			Log.d(TAG, "Restored saved instance: " + mCurPosition);
		}
		
		if (mCurPosition != -1) {
			itemSelected(mCurPosition);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();

		onPopulateItems();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		Log.d(TAG, "Saving saved instance: " + mCurPosition);
		outState.putInt(SAVE_CURRENT_POSITION, mCurPosition);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelected(position);
	}
	
	private void itemSelected(int index) {
		Log.d(TAG, "itemSelected(" + index + ")");
		setItemSelected(index);
		onItemSelected(index);
	}
	
	public void setItemSelected(int index) {
		Log.d(TAG, "setItemSelected(" + index + ")");
		mCurPosition = index;
		getListView().setItemChecked(index, true);
	}
	
	public void clearChoices() {
		Log.d(TAG, "clearChoices()");
		mCurPosition = -1;
		getListView().clearChoices();
	}
	
}
