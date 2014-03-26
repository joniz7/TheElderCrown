package model.entity.bottom;

import util.EntityType;

public class WaterTile extends Tile{

	private static final long serialVersionUID = 1L;

	public WaterTile(int x, int y) {

		super(x, y, EntityType.WATER_TILE, true);
	}
	
	@Override
	public WaterTile copy() {
		return new WaterTile(x, y);
	}
	
}
