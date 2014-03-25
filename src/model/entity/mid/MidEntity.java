package model.entity.mid;

import model.entity.Entity;
import util.EntityType;

/**
 * A class representing all objects visible and at the same height as the villagers.
 * 
 * @author Teodor O
 *
 */
public abstract class MidEntity extends Entity {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new, blocking MidEntity.
	 * 
	 * @param x the x-coordinate of the Entity.
	 * @param y the y-coordinate of the Entity.
	 * @param id The EntityType enum of the Entity.
	 */
	public MidEntity(int x, int y, EntityType id) {
		super(x, y, id, true);
	}

	/**
	 * Constructs a new MidEntity.
	 * 
	 * @param x the x-coordinate of the Entity.
	 * @param y the y-coordinate of the Entity.
	 * @param id The EntityType enum of the Entity.
	 * @param blocking whether this entity should hinder the movement of other entities
	 */
	public MidEntity(int x, int y, EntityType id, boolean blocking) {
		super(x, y, id, blocking);
	}


}
