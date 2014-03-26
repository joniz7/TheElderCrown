package model.entity.bottom;

import model.entity.Entity;
import util.EntityType;

/**
 * A class representing all objects below the villagers.
 * 
 * @author Teodor O
 *
 */
public abstract class BottomEntity extends Entity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new, non-blocking BottomEntity.
	 * 
	 * @param x the x-coordinate of the Entity.
	 * @param y the y-coordinate of the Entity.
	 * @param id The EntityType enum of the Entity.
	 */
	public BottomEntity(int x, int y, EntityType id) {
		super(x, y, id, false);
	}

	/**
	 * Constructs a new BottomEntity.
	 * 
	 * @param x the x-coordinate of the Entity.
	 * @param y the y-coordinate of the Entity.
	 * @param id The EntityType enum of the Entity.
	 * @param blocking whether this entity should hinder the movement of other villagers
	 */
	public BottomEntity(int x, int y, EntityType id, boolean blocking) {
		super(x, y, id, blocking);
	}

	public float getSleepValue(){
		return 0f;
	}
	
}
