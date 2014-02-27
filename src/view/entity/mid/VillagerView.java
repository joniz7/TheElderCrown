package view.entity.mid;

import java.awt.Point;
import java.beans.PropertyChangeEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import util.ImageLoader;
import view.entity.EntityView;

/**
 * The view representation of a villager.
 * 
 * @author Niklas
 */
public class VillagerView extends EntityView {

	private int height, weight;
	
	/**
	 * Creates a new VillagerView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public VillagerView(int x, int y, int height, int weight) {
		super("villager", x, y);
		this.height = height;
		this.weight = weight;
	}

	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		int transposedX = x - cameraX;
		int transposedY = y - cameraY;
			
		if(transposedX + imageWidth > 0 && transposedX < width)
			if(transposedY + imageHeight > 0 && transposedY < height){
				float BMI = (float) (weight * 4) / (float) height;
				int diameter = ((int) (BMI * 13)) + 10;
				
				g.drawImage(image, transposedX, transposedY);
				
				g.setColor(new Color(125, 125, 125));
				g.fillOval(transposedX + (20 - diameter)/2, transposedY + (20 - diameter)/2,
						diameter, diameter);
				g.setColor(new Color(0, 0, 0));
				g.drawOval(transposedX + (20 - diameter)/2, transposedY + (20 - diameter)/2,
						diameter, diameter);

				return true;
			}
		return false;
	}
	
	@Override
	/**
	 * Is called when our associated model changes in any way.
	 * 
	 * @author Tux
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		if(name.compareTo("status")==0){
			String status = (String)event.getNewValue();
			if(status.compareTo("sleeping")==0){
				setImage("villagersleeping");
			}
			if(status.compareTo("awake")==0){
				setImage("villager");
			}
		}else{
			super.propertyChange(event);
		}
	}
}
