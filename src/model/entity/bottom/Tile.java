package model.entity.bottom;

import util.ObjectType;

public abstract class Tile extends BottomEntity{
	
	protected int typeID;

	public Tile(int x, int y, ObjectType id, boolean isBlocking) {
		super(x ,y, id, isBlocking);
		updatePos(x,y);
	}

	public int getTypeID() {
		return typeID;
	}
}
