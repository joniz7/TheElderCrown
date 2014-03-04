package view.entity.mid;

import java.awt.Point;
import java.beans.PropertyChangeEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import util.ImageLoader;
import util.RandomClass;
import view.entity.EntityView;

/**
 * The view representation of a villager.
 * 
 * @author Niklas
 */
public class VillagerView extends EntityView {

	private int height, weight;
	private Color c;
	private String currentStatus;
	private Boolean drawImage;
	
	/**
	 * Creates a new VillagerView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public VillagerView(int x, int y, int height, int weight) {
		super("villager", x, y);
		this.height = height;
		this.weight = weight;
		c = new Color(RandomClass.getRandomInt(200, 25), RandomClass.getRandomInt(200, 50), 
				RandomClass.getRandomInt(200, 25));
		currentStatus="awake";
		drawImage=false;
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
				
//				g.drawImage(image, transposedX, transposedY);
				
				g.setColor(c);
				g.fillOval(transposedX + (20 - diameter)/2, transposedY + (20 - diameter)/2,
						diameter, diameter);
				g.setColor(new Color(0, 0, 0));
				g.drawOval(transposedX + (20 - diameter)/2, transposedY + (20 - diameter)/2,
						diameter, diameter);
				
				if(drawImage){
					if(currentStatus.compareTo("sleeping")==0){
						setImage("villagerSleeping");						
					}else if(currentStatus.compareTo("drinking")==0){
						setImage("villagerDrinking");
					}else if(currentStatus.compareTo("eating")==0){
						setImage("villagerEating");
					}
					g.drawImage(image, transposedX, transposedY);
				}
				
				
				

				return true;
			}
		return false;
	}
	

	@Override
	/**
	 * Is called when our associated model changes in any way.
	 * Updates a villagers current status
	 * @author Tux, Denise
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		if(name.compareTo("status")==0){
			String status = (String)event.getNewValue();
			if(status.compareTo("sleeping")==0){
				drawImage=true;
				currentStatus="sleeping";
				setImage("villagerSleeping");
			}else if(status.compareTo("eating")==0){
				drawImage=true;
				currentStatus="eating";
			}else if(status.compareTo("drinking")==0){
				drawImage=true;
				currentStatus="drinking";
			}else if(status.compareTo("statusEnd")==0){
				System.out.println("Status ended\n");
				currentStatus="moving";
				drawImage=false;
			}
		}else{
			super.propertyChange(event);
		}
	}
	

}
