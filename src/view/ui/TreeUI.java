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
public class TreeUI extends UI {

	private int xOff = 10, yOff = 10;
	private int fruit;
	private float fruitRegrowth;
	private Color c;
	private String name;
	private boolean show;
	private Image meter, meterArrow;
	
	/**
	 * Creates a new Tree UI.
	 */
	public TreeUI(){
		super("villagerUI");
		show = false;
		this.name = "Fruit Tree";
		meter = ImageLoader.getImage("meter");
		meterArrow = ImageLoader.getImage("meterArrow");
	}

	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		if(show){
			g.drawImage(image, xOff, yOff);
				
			g.setColor(new Color(255, 255, 255));
			g.drawString(name, xOff + 30, yOff + 175);
			
			if(fruit > 0){
				g.drawString("Fruit " + fruit, xOff + 30, yOff + 215);
			}else{
				g.drawImage(meter, xOff + 40, yOff + 215);
				g.drawImage(meterArrow, xOff + 110 + fruitRegrowth, yOff + 207);
				g.drawString("Regrowth", xOff + 90, yOff + 228);
			}
			
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
			}else if(status.compareTo("fruit") == 0){
				fruit = (int) event.getOldValue();
			}else if(status.compareTo("regrowth") == 0){
				fruitRegrowth = (float) event.getOldValue();
			}
		}else{
			super.propertyChange(event);
		}
	}
	

}
