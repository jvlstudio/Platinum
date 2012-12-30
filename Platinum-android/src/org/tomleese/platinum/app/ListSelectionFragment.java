package org.tomleese.platinum.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public abstract class ListSelectionFragment extends ListFragment {
	
	private static final String SAVE_CURRENT_POSITION = "org.tomleese.platinum.current_position";
	
	private int mCurPosition = -1;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
			    
	    if (savedInstanceState != null) {
			mCurPosition = savedInstanceState.getInt(SAVE_CURRENT_POSITION, -1);
		}
	    
	    if (((MultiPaneActivity) getActivity()).isMultiPane()) {
	    	getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
	    }
	    
	    if (mCurPosition != -1) {
    		itemSelected(mCurPosition);
    	}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
	
	public void itemSelected(int index) {
		getListView().setItemChecked(index, true);
		onItemSelected(index);
	}
	
	public void clearChoices() {
		mCurPosition = -1;
		getListView().clearChoices();
	}
	
	public abstract void onPopulateItems();
	public abstract void onItemSelected(int index);
	
}
