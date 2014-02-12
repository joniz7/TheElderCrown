package view;

import head.Display;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.util.ArrayList;

public class View {

	private static ArrayList<Drawable> graphics = new ArrayList<Drawable>();
	private static DisplayMode activeDisplay;
	
	public View(DisplayMode activeDisplay){
		graphics  = new ArrayList<Drawable>();
		View.activeDisplay = activeDisplay;
	}
	
	public static void addGraphic(Drawable d){
		graphics.add(d);
	}
	
	public static void removeGraphic(Drawable d){
		graphics.remove(d);
	}
	
	public static void render(Graphics g, int viewX, int viewY){
		Display disp = new Display();
		g.fillRect(0, 0, disp.getGraphicsDevice().getDisplayMode().getWidth(), 
				disp.getGraphicsDevice().getDisplayMode().getHeight());
		
		int drawCount = 0;
		
		for(Drawable d : graphics)
			if(d.draw(g, viewX, viewY, activeDisplay))
				drawCount++;
	}
	
}
