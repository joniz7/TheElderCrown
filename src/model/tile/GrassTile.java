package model.tile;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super("grass");
		updatePos(x, y);
		typeID = 0;
	}
}