package view.ui;

import java.beans.PropertyChangeEvent;

import model.item.Item;
import model.villager.Villager;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.ImageLoader;
import view.ui.icons.ItemIcon;

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
	private Image meter, meterArrow, inventory, emptyIcon;
	private ItemIcon[] icons = new ItemIcon[6];
	
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
		inventory = ImageLoader.getImage("inventory");
		emptyIcon = ImageLoader.getImage("itemEmpty");
		
		for(int i = 0; i < icons.length; i++)
			icons[i] = new ItemIcon("itemIcon");
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
			
			g.drawImage(inventory, xOff + 55, yOff + 510);
			if(icons[0] != null)
				icons[0].draw(xOff + 58, yOff + 513, g, emptyIcon);
			if(icons[1] != null)
				icons[1].draw(xOff + 101, yOff + 513, g, emptyIcon);
			if(icons[2] != null)
				icons[2].draw(xOff + 144, yOff + 513, g, emptyIcon);
			if(icons[3] != null)
				icons[3].draw(xOff + 58, yOff + 556, g, emptyIcon);
			if(icons[4] != null)
				icons[4].draw(xOff + 101, yOff + 556, g, emptyIcon);
			if(icons[5] != null)
				icons[5].draw(xOff + 144, yOff + 556, g, emptyIcon);
			
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
			}else if(status.compareTo("inventory") == 0){
				Item[] items = (Item[]) event.getOldValue();
				for(int i = 0; i < items.length; i++)
					if(items[i] != null)
						icons[i].setImage(ImageLoader.getImage("item" + items[i].getName()));
					else
						icons[i].setImage(emptyIcon);
			}
		}else{
			super.propertyChange(event);
		}
	}
}
