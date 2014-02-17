package model.entity;

import java.beans.PropertyChangeSupport;

import model.path.criteria.Criteria;
import resource.ObjectType;
import util.Position;
import view.EntityView;

/**
 * A class representing an object that is visible in the world.
 * should not be inherited directly, only through the subclasses in ordered layers.
 * 
 * @author Simon E, Teodor O
 */
public abstract class Entity {
	
	// TODO remove - this reference is bad
	protected EntityView drawable;
	
	// TODO use Position instead
	protected int tileX, tileY;

	// TODO remove when we fix view interpolation
	protected final int TILE_OFFSET = 20;
	protected ObjectType id;
	
	protected PropertyChangeSupport pcs;
	
	/**
	 * The general constructor.
	 * 
	 * @param name The name of the image to be assigned.
	 */
	public Entity(String name, int x, int y, ObjectType id){
		
		pcs = new PropertyChangeSupport(this);
		tileX = x;
		tileY = y;
		this.id = id;
		
		// Create view and tell it to listen
		// TODO do not create views here, but in subclasses
		//      why can we not remove these?
		drawable = new EntityView(name);
		pcs.addPropertyChangeListener(drawable);
		
		
	}
	
	/**
	 * The method used to update the position of this entity.
	 * 
	 * @param x The new x-value of the entity.
	 * @param y The new y-value of the entity.
	 */
	protected void updatePos(int x, int y){
		// TODO should send model coordinates!
		//      fix when we fix view interpolation
		Position pos = new Position(x*TILE_OFFSET, y*TILE_OFFSET);
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
		// TODO should not exist!
		//      interpolation is ideally handled in view (?)
		Position pos = new Position((x*TILE_OFFSET) + interPolX, (y*TILE_OFFSET) + interPolY);
		pcs.firePropertyChange("position", null, pos);		
	}
	
	/**
	 * A method to find the column of the GraphicalEntity.
	 * 
	 * @return the column in which the GraphicalEntity is.
	 */
	public int getTileX() {
		return tileX;
	}

	/**
	 * A method to find the row of the GraphicalEntity.
	 * 
	 * @return the row in which the GraphicalEntity is.
	 */
	public int getTileY() {
		return tileY;
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
	
	public ObjectType getObjectType(){
		return id;
	}
	
}
