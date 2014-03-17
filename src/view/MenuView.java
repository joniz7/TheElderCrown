package view;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import view.menu.MenuItem;

public class MenuView{
	
	private List<MenuItem> menuItems;

	// The title of the game
	private String title = "The Elder Crown";
	// A message text that is displayed under the game title
	private String message;
	
	public MenuView() {
		menuItems = new ArrayList<MenuItem>();
		menuItems.add(new MenuItem("1.\tPlay Game", Color.white,  50, 100));
		menuItems.add(new MenuItem("2.	Create new random world", Color.white,  50, 120));
		menuItems.add(new MenuItem("3.	Create new map world (using \"test.map\")", Color.white,  50, 140));
		menuItems.add(new MenuItem("4.	Toggle fullscreen", Color.white,  50, 180));
		menuItems.add(new MenuItem("5.	Quit", Color.white,  50, 200));
		menuItems.add(new MenuItem("6.	Save map to file (!)", Color.white,  50, 240));
	}
	
	public void render(Graphics g){

		// Draw title
		g.setColor(Color.white);
		g.drawString(title, 50, 40);
		
		// Draw message beneath title
		g.setColor(Color.yellow);
		g.drawString(message, 50, 60);
		
		// Draw all menu items
		for (MenuItem item : menuItems) {
			g.setColor(item.getColor());
			g.drawString(item.getText(), item.getX(), item.getY());
		}
		
	}
	
	/**
	 * Sets the message text, to be displayed below the game title
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Hides the message text.
	 */
	public void resetMessage() {
		this.message = "";
	}
	

}
