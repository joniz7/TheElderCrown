package view.ui;

import java.beans.PropertyChangeEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import model.entity.bottom.Bed;
import model.villager.Villager;

public class BedUI extends UI {

	private int xOff = 300, yOff = 310;
	Bed bed;
	boolean show;
	String name;
	
	public BedUI(Bed bed) {
		super("villagerUI");
		this.bed = bed;
		show = false;
		this.name = "Bed";
	}
	
	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		if(show){
			g.drawImage(image, xOff, yOff);
				
			g.setColor(new Color(255, 255, 255));
			g.drawString(name, xOff + 30, yOff + 175);

			if(bed.isClaimedByMale()){
				g.drawString("Male: " + bed.getMaleClaimantName(), xOff + 30, yOff + 215);
			}
			if(bed.isClaimedByFemale()){
				g.drawString("female: " + bed.getFemaleClaimantName(), xOff + 30, yOff + 245);
			}
		}
		return false;
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		if(name.equals("bedStatus")){
			String status = (String)event.getNewValue();
			if(status.compareTo("show") == 0){
				show = true;
			}else if(status.compareTo("hide") == 0){
				show = false;
			}
		}else{
			super.propertyChange(event);
		}
	}
}
