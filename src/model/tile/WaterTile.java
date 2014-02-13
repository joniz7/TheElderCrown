package model.tile;

public class WaterTile extends Tile{

	public WaterTile(int x, int y) {
		super("water", x, y);
		updatePos(x, y);
		typeID = 1;
	}
	
}
