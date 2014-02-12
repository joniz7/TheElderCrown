package model.tile;

import org.newdawn.slick.Image;

import model.entity.GraphicalEntity;

public abstract class Tile extends GraphicalEntity{
	
	protected int typeID;
	
	public Tile(Image image) {
		super(image);
	}

	public int getTypeID() {
		return typeID;
	}
}
