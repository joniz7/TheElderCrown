package view.menu;

import org.newdawn.slick.Color;

/**
 * A graphical menu item.
 * Has text, colour, and a position
 * 
 * @author Niklas
 */
public class MenuItem {

	private Color color;
	private String text;
	private int x;
	private int y;
	
	public MenuItem(String text, Color color, int x, int y) {
		super();
		this.text = text;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
