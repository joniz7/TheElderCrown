package model.tile;

import resource.ObjectID;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super("grass", x ,y, ObjectID.GRASS_TILE);
		typeID = 0;
	}
}