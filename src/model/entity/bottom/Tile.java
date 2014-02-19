package model.entity.bottom;

import util.EntityType;

public abstract class Tile extends BottomEntity{
	
	protected int typeID;

	public Tile(int x, int y, EntityType id) {
		super(x ,y, id);
		updatePos(x,y);
	}

	public int getTypeID() {
		return typeID;
	}
}
