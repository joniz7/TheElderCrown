package model.entity;

import java.awt.Point;
import java.beans.PropertyChangeSupport;

import model.path.criteria.Criteria;
import util.EntityType;
import util.InterpolPosition;

/**
 * A class representing an object that is visible in the world.
 * should not be inherited directly, only through the subclasses in ordered layers.
 * 
 * @author Simon E, Teodor O
 */
public abstract class Entity {
	
	// This entity's belief of its position
	protected int x, y;
	protected EntityType type;
	protected PropertyChangeSupport pcs;
	private boolean blocking;
	
	/**
	 * The general constructor.
	 * 
	 * @param x the x-coordinate of the Entity.
	 * @param y the y-coordinate of the Entity.
	 * @param type the EntityType associated with the Entity.
	 */
	public Entity(int x, int y, EntityType type){
		pcs = new PropertyChangeSupport(this);
		this.x = x;
		this.y = y;
		this.type = type;
		this.blocking = true;
	}
	
	/**
	 * A constructor with added functionality to set the Entity to blocking or not.
	 * 
	 * @param x the x-coordinate of the Entity.
	 * @param y the y-coordinate of the Entity.
	 * @param type the EntityType associated with the Entity.
	 * @param blocking a boolean representing whether or not this Entity would block movement on its tile.
	 */
	public Entity(int x, int y, EntityType type, boolean blocking){
		pcs = new PropertyChangeSupport(this);
		this.x = x;
		this.y = y;
		this.type = type;
		this.blocking = blocking;
	}
	
	/**
	 * The method used to update the position of this entity.
	 * 
	 * @param x The new x-value of the entity.
	 * @param y The new y-value of the entity.
	 */
	protected void updatePos(int x, int y){
		
		Point oldPos = new Point(this.x, this.y);
		// Tell entity of its new position
		this.x = x;
		this.y = y;
		// Send new position to view
		Point pos = new Point(x, y);
		
		if (!pos.equals(oldPos)) System.out.println("new position: "+x+","+y);
		
		pcs.firePropertyChange("position", oldPos, pos);
	}
	
	/**
	 * Moves the view of this entity in the specified direction
	 * by 'progress' percent.
	 * Changes nothing in the model - is used merely for view interpolation
	 * 
	 * @param dx usually -1, 0 or 1 
	 * @param dy usually -1, 0 or 1
	 * @param progress how far the entity has come in a move action [0,1]
	 */
	public void updateViewPosition(int dx, int dy, double progress){
		InterpolPosition p = new InterpolPosition(x, y, dx, dy, progress);
		pcs.firePropertyChange("interpolPosition", null, p);
	}
	
	/**
	 * A method to find the column of the Entity.
	 * 
	 * @return the column in which the Entity is.
	 */
	public int getX() {
		return x;
	}

	/**
	 * A method to find the row of the Entity.
	 * 
	 * @return the row in which the Entity is.
	 */
	public int getY() {
		return y;
	}

	/**
	 * A method to assist in pathfinding to object with special criteria.
	 * 
	 * @param criteria The specified criteria to be checked.
	 * @return true if the criteria is met, false otherwise.
	 */
	public boolean meetCriteria(Criteria criteria){
		return criteria.match(this);
	}
	
	/**
	 * Returns the type of this Entity.
	 */
	public EntityType getEntityType(){
		return type;
	}
	
	/**
	 * Use when you need something to listen to this.
	 * @return the pcs of this entity.
	 */
	public PropertyChangeSupport getPCS(){
		return pcs;
	}
	
	public boolean isBlocking(){
		return blocking;
	}
	
}
