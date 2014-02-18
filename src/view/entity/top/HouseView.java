package view.entity.top;

import org.newdawn.slick.Graphics;

import view.entity.EntityView;

/**
 * The view representation of a house.
 * 
 * @author Karl-Agnes
 *
 */
public class HouseView extends EntityView {
	
	private float rotation;

	/**
	 * Creates a new HouseView
	 * 
	 * @param x the x-coordinate for the top left corner of the house.
	 * @param y the y-coordinate for the top left corner of the house.
	 */
	public HouseView(int x, int y, float rotation) {
		super("hut", x, y);
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
