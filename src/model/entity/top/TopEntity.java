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
	 * Constructs a new, non-blocking TopEntity.
	 * 
	 * @param x the x-coordinate of the topEntity.
	 * @param y the y-coordinate of the topEntity.
	 * @param type the EntityType to be assiciated with the Entity.
	 */
	public TopEntity(int x, int y, EntityType type) {
		super(x, y, type, false);
	}
	
	/**
	 * Constructs a new TopEntity.
	 * 
	 * @param x the x-coordinate of the topEntity.
	 * @param y the y-coordinate of the topEntity.
	 * @param id the EntityType to be assiciated with the Entity.
	 * @param blocking a boolean to set whether or not the Entity should block its square.
	 */
	public TopEntity(int x, int y, EntityType id, boolean blocking) {
		super(x, y, id, blocking);
	}

}
