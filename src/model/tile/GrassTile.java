package model.tile;

import resource.ObjectType;
import view.EntityView;
import view.tile.GrassTileView;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super("grass", x ,y, ObjectType.GRASS_TILE);
		typeID = 0;
		
		EntityView view = new GrassTileView(x, y);
		pcs.addPropertyChangeListener(view);
		
	}
}