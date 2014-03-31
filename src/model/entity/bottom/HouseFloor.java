package model.entity.bottom;

import util.EntityType;

public class HouseFloor extends Tile {

	private static final long serialVersionUID = 1L;

	/**
	 * The general constructor for a HouseFloor.
	 * 
	 * @param x the x-coordinate of the floor.
	 * @param y the y-coordinate of the floor.
	 */
	public HouseFloor(int x, int y) {
		super(x, y, EntityType.HOUSE_FLOOR, false);
	}

	@Override
	public HouseFloor copy() {
		return new HouseFloor(x, y);
	}
	
	@Override
	public float getSleepValue(){
		return 0.02f;
	}
	
}
