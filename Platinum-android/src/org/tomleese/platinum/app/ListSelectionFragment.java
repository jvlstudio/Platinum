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
public abstract class ListSelectionFragment extends ListFragment {

	private static final String TAG = "ListSelectionFragment";
	private static final String SAVE_CURRENT_POSITION = "org.tomleese.platinum.current_position";

	private int mCurPosition = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "Created");

		if (savedInstanceState != null) {
			mCurPosition = savedInstanceState.getInt(SAVE_CURRENT_POSITION, -1);
			Log.d(TAG, "Restoring saved instance: " + mCurPosition);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.d(TAG, "Activity created");

		onPopulateItems();

		if (getActivity() instanceof MultiPaneActivity) {
			if (((MultiPaneActivity) getActivity()).isMultiPane()) {
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			}
		}

		if (mCurPosition != -1) {
			itemSelected(mCurPosition);
		}
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
	
	@Override
	public void onResume() {
		super.onResume();

		onPopulateItems();
	}
	
	/**
	 * This will set the currently selected item and cause the onItemSelected
	 * method to be called.
	 * 
	 * @param index The index of the item in the adapter
	 */
	public void itemSelected(int index) {
		setItemSelected(index);
		onItemSelected(index);
	}
	
	/**
	 * This will set the currently selected item. (This will not cause the
	 * onItemSelected method to be called.)
	 * 
	 * @param index The index of the item in the adapter
	 */
	public void setItemSelected(int index) {
		Log.d(TAG, "Set item Selected: " + index);
		mCurPosition = index;
		getListView().setItemChecked(index, true);
	}
	
	/**
	 * This will clear any selected choices.
	 */
	public void clearChoices() {
		Log.d(TAG, "Choices cleared");
		mCurPosition = -1;
		getListView().clearChoices();
	}
	
	/**
	 * Called during onResume and onActivityCreated to tell the instanced class
	 * to (re)populate the adapter.
	 */
	public abstract void onPopulateItems();
	
	/**
	 * Called when an item has been selected. Typically here you would call a
	 * method on the activity which opens the item on the item view pane.
	 * 
	 * @param index The index of the item in the adapter.
	 */
	public abstract void onItemSelected(int index);
	
}
