package view.entity;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.ImageLoader;
import view.View;

/**
 * A generic view representation of an entity.
 *
 * Keeps track of the entity's image, view position (and image name).
 */
public class EntityView implements PropertyChangeListener {

	protected Image image;
	private String name;
	
	// View coordinates
	protected int x, y;
	
	/**
	 * Creates a new EntityView.
	 * (Note: do not forget to set up listeners)
	 * 
	 * @param name - the name of the graphic to use
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public EntityView(String name, int x, int y){
		image = ImageLoader.getImage(name);
		this.x = View.convertCoordinate(x);
		this.y = View.convertCoordinate(y);
		this.name = name;
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
		
		String name = event.getPropertyName();
		if (name.equals("position")) {
			Point p = (Point)event.getNewValue();
			x = View.convertCoordinate(p.getX());
			y = View.convertCoordinate(p.getY());
		}
		else if (name.equals("interpolPosition")) {
//			InterpolPosition p = (InterpolPosition) event.getNewValue();
//			// Change in view coordinates
//			int dx = (int) (p.getDx()*p.getProgress()*View.TILE_OFFSET);
//			int dy = (int) (p.getDy()*p.getProgress()*View.TILE_OFFSET);
//			// Originating view coordinates (i.e. tile we're moving from)
//			int x = View.convertCoordinate(p.getX());
//			int y = View.convertCoordinate(p.getY());
//			// Apply change
//			this.x = x + dx;
//			this.y = y + dy;
		}

	}
	
	
	
}
