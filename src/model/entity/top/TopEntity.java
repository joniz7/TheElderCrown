package model.entity.top;

import model.entity.Entity;
import util.EntityType;

/**
 * A class representing all objects visible and above the villagers.
 * 
 * @author Teodor O
 *
 */
public abstract class TopEntity extends Entity {

	/**
	 * General constructor for a (graphically) top layered Entity.
	 * 
	 * @param x the x-coordinate of the topEntity.
	 * @param y the y-coordinate of the topEntity.
	 * @param id the EntityType to be assiciated with the Entity.
	 */
	public TopEntity(int x, int y, EntityType id) {
		super(x, y, id);
		// View.addTopGraphic(drawable);
	}
	
	/**
	 * A constructor with the extended functionality to be able to set whether or not 
	 * the Entity should be blocking the square it's stationed in.
	 * @param x the x-coordinate of the topEntity.
	 * @param y the y-coordinate of the topEntity.
	 * @param id the EntityType to be assiciated with the Entity.
	 * @param blocking a boolean to set whether or not the Entity should block its square.
	 */
	public TopEntity(int x, int y, EntityType id, boolean blocking) {
		super(x, y, id, blocking);
	}


}
