package model.tile;

import model.entity.GraphicalEntity;

public abstract class Tile extends GraphicalEntity{
	
	protected int typeID;
	
	public Tile(String imageName) {
		super(imageName);
	}

	public int getTypeID() {
		return typeID;
	}
}
