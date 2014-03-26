package model.entity.top.house;

import util.EntityType;

/**
 * The class representing a Door.
 * 
 * @author Karl-Agnes
 *
 */
public class HouseDoor extends HousePart {
	
	private static final long serialVersionUID = 1L;
	private int orientation;

	/**
	 * The general constructor.
	 * 
	 * @param x the x-coordinate of the door.
	 * @param y the y-coordinate of the door.
	 * @param orientation an integer giving direction in which the door will face.
	 */
	public HouseDoor(int x, int y, int orientation) {
		super(x, y, EntityType.HOUSE_DOOR, false);
		this.orientation = orientation;
	}
	
	public int getOrientation(){
		return orientation;
	}
	
	@Override
	public HouseDoor copy() {
		return new HouseDoor(x,y,orientation);
	}

}
