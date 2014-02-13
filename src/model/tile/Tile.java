package model.tile;

import model.entity.BottomLayerGraphicalEntity;

public abstract class Tile extends BottomLayerGraphicalEntity{
	
	protected int typeID;

	public Tile(String imageName, int x, int y) {
		super(imageName, x ,y);
		updatePos(x,y);
	}

	public int getTypeID() {
		return typeID;
	}
}
