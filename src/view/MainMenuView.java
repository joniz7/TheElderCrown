package view;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MainMenuView{
	

	public void render(Graphics g){

		g.setColor(Color.white);
		g.drawString("The Elder Crown", 50, 40);
		g.drawString("1.	Play Game", 50, 100);
		g.drawString("2.	Quit", 50, 120);
			
	}

}
