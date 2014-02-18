package model.entity.bottom;

import util.ObjectType;

public abstract class Tile extends BottomEntity{
	
	protected int typeID;

	public Tile(int x, int y, ObjectType id) {
		super(x ,y, id);
		updatePos(x,y);
	}

	public int getTypeID() {
		return typeID;
	}
}
