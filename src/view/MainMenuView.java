package view;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MainMenuView{
	

	public void render(Graphics g){

		g.setColor(Color.white);
		g.drawString("The Elder Crown", 50, 40);
		g.drawString("1.	Play Game", 50, 100);
		g.drawString("2.	Set fullscreen", 50, 120);
		g.drawString("3.	Quit", 50, 140);
			
	}

}
