package org.tomleese.platinum.app;

/**
 * An interface which represents an activity with multiple panes. Typically,
 * this is used for the Master-Detail activity paradigm. 
 *  
 * @author Tom Leese
 */
public interface MultiPaneActivity {
	
	/**
	 * Returns true if the activity has multiple panes (or fragments)
	 * displayed at the same time.
	 * 
	 * @return True if multiple panes are displayed at the same time
	 */
	public boolean isMultiPane();
	
}
