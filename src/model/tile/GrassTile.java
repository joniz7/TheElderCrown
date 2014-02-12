package model.tile;

import org.newdawn.slick.Image;

import resource.ImageLoader;

public class GrassTile extends Tile{

	protected static Image image = ImageLoader.loadImage("ph", "grass.png");
	
	public GrassTile(int x, int y) {
		super(image);
		updatePos(x, y);
		typeID = 0;
	}
}