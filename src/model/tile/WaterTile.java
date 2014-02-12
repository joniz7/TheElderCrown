package model.tile;

import java.awt.Image;

import resource.ImageLoader;

public class WaterTile extends Tile{

protected static Image image = ImageLoader.loadImage("ph", "water.png");
	
	public WaterTile(int x, int y) {
		super(image);
		updatePos(x, y);
		typeID = 1;
	}
	
}
