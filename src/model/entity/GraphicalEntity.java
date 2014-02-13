package model.entity;

import model.path.criteria.Criteria;
import view.Drawable;

/**
 * A class representing an object that is visible in the world.
 * cannot be inherited directly, only through the subclasses in ordered layers.
 * 
 * @author Simon E, Teodor O
 */
public abstract class GraphicalEntity {
	
	protected Drawable drawable;
	/**
	 * The general constructor.
	 * 
	 * @param name The name of the image to be assigned.
	 */
	public GraphicalEntity(String name){
		drawable = new Drawable(name);
	}
	
	/**
	 * The method used to update the position of this entity.
	 * 
	 * @param x The new x-value of the entity.
	 * @param y The new y-value of the entity.
	 */
	protected void updatePos(int x, int y){
		drawable.setDrawX(x);
		drawable.setDrawY(y);
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
	
}
