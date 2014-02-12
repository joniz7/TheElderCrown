package model.tile;

import model.entity.GraphicalEntity;

public abstract class Tile extends GraphicalEntity{
	
	protected int typeID;

	public Tile(String folder, String image) {
		super(folder, image);
	}

	public int getTypeID() {
		return typeID;
	}
}
