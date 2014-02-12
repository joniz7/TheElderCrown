package resource;

import java.awt.Image;

import model.entity.GraphicalEntity;

public class Hit extends GraphicalEntity{

	protected static Image image = ImageLoader.loadImage("ph", "hit.png");
	
	public Hit(int x, int y){
		super(image);
		updatePos(x, y);
	}
	
}
