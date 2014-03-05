package model.entity.top.house;

import util.EntityType;

/**
 * The class to represent a corner of a house.
 * 
 * @author Karl-Agnes
 *
 */
public class HouseCorner extends HousePart {
	
	private int orientation;

	/**
	 * The general constructor.
	 * 
	 * @param x the x-coordinate of the wall.
	 * @param y the y-coordinate of the wall.
	 * @param orientation an int specifying what direction the wall will face.
	 */
	public HouseCorner(int x, int y, int orientation) {
		super(x, y, EntityType.HOUSE_CORNER, true);
		this.orientation = orientation;
	}
	
	public int getOrientation(){
		return orientation;
	}

}
