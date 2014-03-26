package view.ui;

import java.beans.PropertyChangeEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * The UI representation of a villager.
 * 
 * @author Simon E
 */
public class DrinkStorageUI extends UI {

	private int xOff = 10, yOff = 10;
	private int fruit;
	private Color c;
	private String name;
	private boolean show;
	
	/**
	 * Creates a new Tree UI.
	 */
	public DrinkStorageUI(){
		super("villagerUI");
		show = false;
		this.name = "Drink Storage";
	}

	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		if(show){
			g.drawImage(image, xOff, yOff);
				
			g.setColor(new Color(255, 255, 255));
			g.drawString(name, xOff + 30, yOff + 175);

			g.drawString("Water " + fruit, xOff + 30, yOff + 215);
		}
		return false;
	}
	

	@Override
	/**
	 * @author Simon E
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		if(name.compareTo("status") == 0){
			String status = (String)event.getNewValue();
			if(status.compareTo("show") == 0){
				show = true;
			}else if(status.compareTo("hide") == 0){
				show = false;
			}else if(status.compareTo("drinks") == 0){
				fruit = (int) event.getOldValue();
			}
		}else{
			super.propertyChange(event);
		}
	}
	

}
