package org.tomleese.platinum.app;

public abstract interface MasterFragment {
	
	/**
	 * This will clear any selected choices.
	 */
	public abstract void clearChoices();
	
	/**
	 * This will set the currently selected item. (This will not cause the
	 * onItemSelected method to be called.)
	 * 
	 * @param index The index of the item in the adapter
	 */
	public abstract void setItemSelected(int index);
	
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
