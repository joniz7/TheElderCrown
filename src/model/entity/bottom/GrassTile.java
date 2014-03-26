package model.entity.bottom;

import util.EntityType;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super(x ,y, EntityType.GRASS_TILE, false);
	}
	
	@Override
	public GrassTile copy() {
		return new GrassTile(x, y);
	}
	
	@Override
	public float getSleepValue(){
		return 0.02f;
	}
	
}