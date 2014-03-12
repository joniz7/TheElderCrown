package model;

import java.beans.PropertyChangeEvent;

/**
 * A world which loads a map
 * 
 * @author Niklas
 */
public class MapWorld extends World {

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO ?
	}

	@Override
	// @deprecated
	public void initialize() {
		
	}
	
	/**
	 * Initializes the world.
	 * @param m - the map to use
	 */
	public void initialize(WorldMap m) {
		
	}

}
