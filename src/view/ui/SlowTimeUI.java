package view.ui;

import java.beans.PropertyChangeEvent;

import org.newdawn.slick.Graphics;

public class SlowTimeUI extends UI{

	private int x=560,y=520;
	public SlowTimeUI() {
		super("leftArrow");
	}
	
	@Override
	public boolean draw(Graphics g, int cameraX, int cameraY, int width, int height){
		System.out.println("about to draw arrow\n");
		g.drawImage(image, x,y);
		return false;
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		if(name.compareTo("hurrdurr")==0){
			
			
		}else{
			super.propertyChange(event);
		}
	}

}
