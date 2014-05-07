package view.entity.top.house;

import org.newdawn.slick.Graphics;
import view.entity.EntityView;

/**
 * The view representation of a food storage.
 * 
 * @author Simon E
 *
 */
public class FoodStorageView extends EntityView {

	private float rotation;

	/**
	 * Creates a new FoodStorageView
	 * 
	 * @param x the x-coordinate for the wall.
	 * @param y the y-coordinate for the wall.
	 */
	public FoodStorageView(int x, int y, int id) {
		super("storage", x, y, id);
	}

	@Override
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
}
