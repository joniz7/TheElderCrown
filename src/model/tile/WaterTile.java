package model.tile;

import resource.ObjectType;

public class WaterTile extends Tile{

	public WaterTile(int x, int y) {
		super("water", x, y, ObjectType.WATER_TILE);
		typeID = 1;
	}
	
}
