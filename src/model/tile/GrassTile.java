package model.tile;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super("grass", x ,y);
		updatePos(x, y);
		typeID = 0;
	}
}