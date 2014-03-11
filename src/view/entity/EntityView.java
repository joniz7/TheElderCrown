package view.entity;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.ImageLoader;
import util.InterpolPosition;
import view.MainGameView;

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
		this.x = MainGameView.modelToViewCoordinate(x);
		this.y = MainGameView.modelToViewCoordinate(y);
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
			x = MainGameView.modelToViewCoordinate(p.getX());
			y = MainGameView.modelToViewCoordinate(p.getY());
		}
		else if (name.equals("interpolPosition")) {
			InterpolPosition p = (InterpolPosition) event.getNewValue();
			// Change in view coordinates
			int dx = (int) (p.getDx()*p.getProgress()*MainGameView.TILE_OFFSET);
			int dy = (int) (p.getDy()*p.getProgress()*MainGameView.TILE_OFFSET);
			// Originating view coordinates (i.e. tile we're moving from)
			int x = MainGameView.modelToViewCoordinate(p.getX());
			int y = MainGameView.modelToViewCoordinate(p.getY());
			// Apply change
			this.x = x + dx;
			this.y = y + dy;
		}
	}
	
	
	
}
