package model.tile;

import resource.ObjectID;

public class WaterTile extends Tile{

	public WaterTile(int x, int y) {
		super("water", x, y, ObjectID.WATER_TILE);
		typeID = 1;
	}
	
}
