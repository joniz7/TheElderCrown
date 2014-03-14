package model;

import java.beans.PropertyChangeEvent;

import org.newdawn.slick.util.OperationNotSupportedException;

/**
 * A world which loads a map
 * 
 * @author Niklas
 */
public class MapWorld extends World {

	// The map that is loaded during initialisation 
	private WorldMap map;
	
	/**
	 * Creates a new MapWorld
	 * @param map - the map to use. Is loaded when initialize() is called.
	 */
	public MapWorld(WorldMap map) {
		this.map = map;
	}
	
	@Override
	/**
	 * Initializes the world.
	 * Also loads the specified map and places villagers on it.
	 */
	public void initialize() {
		super.initialize();
		
		// Add like this, so views are created correctly
		addEntities(map.botEntities, 0);
		addEntities(map.midEntities, 1);
		addEntities(map.topEntities, 2);
		// This however can be assigned directly
		tickables = map.tickables;
		
		// Place villagers after the rest of the world is done
		initializeVillagers();
		
	}
	
//	private void addViews

}
