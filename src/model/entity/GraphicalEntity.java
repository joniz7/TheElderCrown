package model.entity;

import resource.ObjectID;
import model.path.criteria.Criteria;
import view.Drawable;

/**
 * A class representing an object that is visible in the world.
 * should not be inherited directly, only through the subclasses in ordered layers.
 * 
 * @author Simon E, Teodor O
 */
public abstract class GraphicalEntity {
	
	protected Drawable drawable;
	protected int tileX, tileY;
	protected final int TILE_OFFSET = 20;
	ObjectID id;
	
	/**
	 * The general constructor.
	 * 
	 * @param name The name of the image to be assigned.
	 */
	public GraphicalEntity(String name, int x, int y, ObjectID id){
		drawable = new Drawable(name);
		tileX = x;
		tileY = y;
		this.id = id;
	}
	
	/**
	 * The method used to update the position of this entity.
	 * 
	 * @param x The new x-value of the entity.
	 * @param y The new y-value of the entity.
	 */
	protected void updatePos(int x, int y){
		drawable.setDrawX(x*TILE_OFFSET);
		drawable.setDrawY(y*TILE_OFFSET);
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
		drawable.setDrawX((x*TILE_OFFSET) + interPolX);
		drawable.setDrawY((y*TILE_OFFSET) + interPolY);
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
	
	public ObjectID getObjectID(){
		return id;
	}
	
}
