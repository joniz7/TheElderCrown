package model.entity.bottom;

import util.EntityType;

public class NullTile extends Tile {

	// TODO why type?
	public NullTile(int x, int y) {
		super(x, y, EntityType.NULL_TILE, false);
	}

	@Override
	public NullTile copy() {
		return new NullTile(x, y);
	}
	
}
