package view.entity.top.house;

import org.newdawn.slick.Graphics;

import view.entity.EntityView;

/**
 * The view representation of a door to a house.
 * 
 * @author Karl-Agnes
 *
 */
public class HouseDoorView extends EntityView {
	
	private float rotation;

	/**
	 * Creates a new HouseDoorView
	 * 
	 * @param x the x-coordinate for the door.
	 * @param y the y-coordinate for the door.
	 * @param rotation an int to give the orientation.
	 */
	public HouseDoorView(int x, int y, int rotation) {
		super("door", x, y);
		this.rotation = rotation;
	}

	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		int transposedX = x - cameraX;
		int transposedY = y - cameraY;
		
		image.setRotation(rotation);
		if(transposedX + imageWidth > 0 && transposedX < width)
			if(transposedY + imageHeight > 0 && transposedY < height){
				g.drawImage(image, transposedX, transposedY);
				return true;
			}
		return false;
	}
}
