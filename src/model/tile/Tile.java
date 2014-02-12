package model.tile;

import model.entity.GraphicalEntity;

public abstract class Tile extends GraphicalEntity{
	
	protected int typeID;

<<<<<<< HEAD
	public Tile(String imageName) {
		super(imageName);
=======
	public Tile(String folder, String image) {
		super(folder, image);
>>>>>>> 786c37832db9a7538963fc0cab6acb41d847989c
	}

	public int getTypeID() {
		return typeID;
	}
}
