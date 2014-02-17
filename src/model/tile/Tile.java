package model.tile;

import resource.ObjectType;
import model.entity.BottomLayerEntity;

public abstract class Tile extends BottomLayerEntity{
	
	protected int typeID;

	public Tile(String imageName, int x, int y, ObjectType id) {
		super(imageName, x ,y, id);
		updatePos(x,y);
	}

	public int getTypeID() {
		return typeID;
	}
}
