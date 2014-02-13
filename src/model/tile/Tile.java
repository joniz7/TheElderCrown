package model.tile;

import model.entity.BottomLayerGraphicalEntity;

public abstract class Tile extends BottomLayerGraphicalEntity{
	
	protected int typeID;

	public Tile(String imageName) {
		super(imageName);
	}

	public int getTypeID() {
		return typeID;
	}
}
