package view.entity.top.house;

import org.newdawn.slick.Graphics;
import view.entity.EntityView;

/**
 * The view representation of a corner of a house.
 * 
 * @author Karl-Agnes
 *
 */
public class HouseCornerView extends EntityView {

	private float rotation;

	/**
	 * Creates a new HouseCornerView
	 * 
	 * @param x the x-coordinate for the corner.
	 * @param y the y-coordinate for the corner.
	 * @param rotation an int to give the orientation.
	 */
	public HouseCornerView(int x, int y, int rotation) {
		super("corner", x, y);
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
