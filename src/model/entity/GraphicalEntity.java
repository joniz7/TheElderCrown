package model.entity;

import org.newdawn.slick.Image;

import view.Drawable;
import view.View;

public class GraphicalEntity {

	protected Drawable drawable;
	
	public GraphicalEntity(Image image){
		drawable = new Drawable(image);
		View.addGraphic(drawable);
	}
	
	public GraphicalEntity(String folder, String name){
		drawable = new Drawable(folder, name);
		View.addGraphic(drawable);
	}
	
	protected void updatePos(int x, int y){
		drawable.setDrawX(x);
		drawable.setDrawY(y);
	}
	
}
