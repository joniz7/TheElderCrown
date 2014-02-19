package model.entity;

import java.awt.Point;
import java.beans.PropertyChangeSupport;

import model.path.criteria.Criteria;
import util.EntityType;
import view.entity.EntityView;

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
	
	/**
	 * The general constructor.
	 * 
	 * @param name The name of the image to be assigned.
	 */
	public Entity(int x, int y, EntityType type){
		
		pcs = new PropertyChangeSupport(this);
		this.x = x;
		this.y = y;
		this.type = type;

	}
	
	/**
	 * The method used to update the position of this entity.
	 * 
	 * @param x The new x-value of the entity.
	 * @param y The new y-value of the entity.
	 */
	protected void updatePos(int x, int y){
		// Tell entity of its new position
		this.x = x;
		this.y = y;
		// TODO should send model coordinates!
		//      fix when we fix view interpolation
		Point pos = new Point(x*20, y*20);
		pcs.firePropertyChange("position", null, pos);
	}
	
	/**
	 * The method used to update the position of this entity. Also includes an
	 * interpolation value.
	 * 
	 * @param x The new x-value of the entity.
	 * @param y The new y-value of the entity.
	 * @param interPolX The interpolation in x-axis
	 * @param interPolY The interpolation in y-axis
	 */
	protected void updatePos(int x, int y, int interPolX, int interPolY){
		// Tell entity of its new position
		this.x = x;
		this.y = y;
		// TODO should not exist!
		//      interpolation is ideally handled in view (?)
		Point pos = new Point((x*20) + interPolX, (y*20) + interPolY);
		pcs.firePropertyChange("position", null, pos);		
	}
	
	/**
	 * A method to find the column of the GraphicalEntity.
	 * 
	 * @return the column in which the GraphicalEntity is.
	 */
	public int getX() {
		return x;
	}

	/**
	 * A method to find the row of the GraphicalEntity.
	 * 
	 * @return the row in which the GraphicalEntity is.
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
	 * Creates and returns a view for this Entity.
	 * Sets up listeners between the Entity and its view.
	 */
	public abstract EntityView createView();
	
	/**
	 * Returns the type of this Entity.
	 */
	public EntityType getEntityType(){
		return type;
	}
	
}
