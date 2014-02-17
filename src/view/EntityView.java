package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import resource.ImageLoader;
import util.Position;

/**
 * The graphical part of an Entity.
 *
 * Keeps track of the entity's image, view position (and image name).
 */
public class EntityView implements PropertyChangeListener {

	private Image image;
	
	// View coordinates
	private int x, y;
	
	private String name;
	
	public EntityView(String name){
		image = ImageLoader.getImage(name);
		this.name = name;
	}
	
	public EntityView(Image image){
		this.image = image;
	}
	
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		int transposedX = x - cameraX;
		int transposedY = y - cameraY;
			
		if(transposedX + imageWidth > 0 && transposedX < width)
			if(transposedY + imageHeight > 0 && transposedY < height){
				g.drawImage(image, transposedX, transposedY);
				return true;
			}
		return false;
	}

	/*
	public void setDrawX(int drawX) {
		this.worldX = drawX;
	}

	public void setDrawY(int drawY) {
		this.worldY = drawY;
	}
	*/

	public void setImage(String name) {
		image = ImageLoader.getImage(name);
	}
	
	public String getImageName(){
		return name;
	}

	
	@Override
	/**
	 * Is called when our associated model changes in any way.
	 * 
	 * @author Niklas
	 */
	public void propertyChange(PropertyChangeEvent event) {

		
		
		switch (event.getPropertyName()) {
		
		// Camera position changed
		case "position":
			Position p = (Position)event.getNewValue();
			x = p.getX();
			y = p.getY();
			// TODO should send model coordinates here!
			// Fix when we do view interpolation
//			x = View.convertCoordinate(p.getX());
//			y = View.convertCoordinate(p.getY());
			break;
	
	}
		
	}
	
	
	
}
