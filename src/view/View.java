package view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class View {

	private static ArrayList<Drawable> graphics = new ArrayList<Drawable>();
	private static int width;
	private static int height;
	
	public View(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public static void addGraphic(Drawable d){
		graphics.add(d);
	}
	
	public static void removeGraphic(Drawable d){
		graphics.remove(d);
	}
	
	public static void render(Graphics g, int viewX, int viewY){
//		Display disp = new Display();
//		g.fillRect(0, 0, disp.getGraphicsDevice().getDisplayMode().getWidth(), 
//				disp.getGraphicsDevice().getDisplayMode().getHeight());
		
		int drawCount = 0;
		
		for(Drawable d : graphics)
			if(d.draw(g, viewX, viewY, width, height))
				drawCount++;
	}
	
}
