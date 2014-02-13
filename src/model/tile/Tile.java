package model.tile;

import resource.ObjectID;
import model.entity.BottomLayerGraphicalEntity;

public abstract class Tile extends BottomLayerGraphicalEntity{
	
	protected int typeID;

	public Tile(String imageName, int x, int y, ObjectID id) {
		super(imageName, x ,y, id);
		updatePos(x,y);
	}

	public int getTypeID() {
		return typeID;
	}
}
