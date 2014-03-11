package view.ui;

import java.beans.PropertyChangeEvent;

import model.villager.Villager;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.ImageLoader;
import util.RandomClass;
import view.entity.EntityView;

/**
 * The UI representation of a villager.
 * 
 * @author Simon E
 */
public class VillagerUI extends UI {

	private int length, weight, xOff = 10, yOff = 10;
	private float hungerOff, thirstOff, sleepOff;
	private Color c;
	private String currentAction, currentPlan;
	private String name;
	private boolean show;
	private Image meter, meterArrow;
	
	/**
	 * Creates a new Villager UI.
	 * 
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public VillagerUI(Villager villager){
		super("villagerUI");
		show = false;
		length = villager.getLength();
		weight = villager.getWeight();
		this.name = villager.getName();
		meter = ImageLoader.getImage("meter");
		meterArrow = ImageLoader.getImage("meterArrow");
	}

	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		if(show){
			g.drawImage(image, xOff, yOff);
				
			g.setColor(new Color(255, 255, 255));
			g.drawString(name, xOff + 30, yOff + 175);
			g.drawString("Height: " + length + " cm", xOff + 30, yOff + 215);
			g.drawString("Weight: " + weight + " kg", xOff + 30, yOff + 235);
			
			g.drawImage(meter, xOff + 40, yOff + 275);
			g.drawImage(meterArrow, xOff + 110 + hungerOff, yOff + 267);
			g.drawString("Hunger", xOff + 90, yOff + 288);
			
			g.drawImage(meter, xOff + 40, yOff + 315);
			g.drawImage(meterArrow, xOff + 110 + thirstOff, yOff + 307);
			g.drawString("Thirst", xOff + 90, yOff + 328);
			
			g.drawImage(meter, xOff + 40, yOff + 355);
			g.drawImage(meterArrow, xOff + 110 + sleepOff, yOff + 347);
			g.drawString("Sleep", xOff + 90, yOff + 368);
			
			g.drawString("Currently:", xOff + 30, yOff + 410);
			g.drawString(currentPlan, xOff + 30, yOff + 450);
			g.drawString(currentAction, xOff + 30, yOff + 470);
		}
		return false;
	}
	

	@Override
	/**
	 * Is called when our associated villager changes in any way.
	 * Updates a villagers current meters
	 * 
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
			}else if(status.compareTo("hunger") == 0){
				hungerOff = (Float) event.getOldValue();
				if(hungerOff > 80)
					hungerOff = 80;
				else if(hungerOff < -80)
					hungerOff = -80;
			}else if(status.compareTo("thirst") == 0){
				thirstOff = (Float) event.getOldValue();
				if(thirstOff > 80)
					thirstOff = 80;
				else if(thirstOff < -80)
					thirstOff = -80;
			}else if(status.compareTo("sleepiness") == 0){
				sleepOff = (Float) event.getOldValue();
				if(sleepOff > 80)
					sleepOff = 80;
				else if(sleepOff < -80)
					sleepOff = -80;
			}else if(status.compareTo("action") == 0){
				currentAction = (String) event.getOldValue();
			}else if(status.compareTo("plan") == 0){
				currentPlan = (String) event.getOldValue();
			}
		}else{
			super.propertyChange(event);
		}
	}
	

}
