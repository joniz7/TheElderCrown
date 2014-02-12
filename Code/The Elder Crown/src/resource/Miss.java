package resource;

import java.awt.Image;

import model.entity.GraphicalEntity;

public class Miss extends GraphicalEntity{

	protected static Image image = ImageLoader.loadImage("ph", "miss.png");
	
	public Miss(int x, int y){
		super(image);
		updatePos(x, y);
	}
	
}
