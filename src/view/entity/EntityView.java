package view.entity;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.EntityId;
import util.ImageLoader;
import util.InterpolPosition;
import util.UtilClass;
import view.WorldView;

/**
 * A generic view representation of an entity.
 *
 * Keeps track of the entity's image, view position (and image name).
 */
public class EntityView implements PropertyChangeListener {

	protected Image image;
	private String name;
	public final int ID;
	
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
	public EntityView(String name, int x, int y, int id){
		image = ImageLoader.getImage(name);
		this.x = WorldView.modelToViewCoordinate(x);
		this.y = WorldView.modelToViewCoordinate(y);
		this.name = name;
		ID = id;
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
			x = WorldView.modelToViewCoordinate(p.getX());
			y = WorldView.modelToViewCoordinate(p.getY());
		}
		else if (name.equals("interpolPosition")) {
			InterpolPosition p = (InterpolPosition) event.getNewValue();
			// Change in view coordinates
			int dx = (int) (p.getDx()*p.getProgress()*WorldView.TILE_OFFSET);
			int dy = (int) (p.getDy()*p.getProgress()*WorldView.TILE_OFFSET);
			// Originating view coordinates (i.e. tile we're moving from)
			int x = WorldView.modelToViewCoordinate(p.getX());
			int y = WorldView.modelToViewCoordinate(p.getY());
			// Apply change
			this.x = x + dx;
			this.y = y + dy;
		}
	}
	
	
	
}
