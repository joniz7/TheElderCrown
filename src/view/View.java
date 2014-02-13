package view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class View {

	private static ArrayList<Drawable> topGraphics = new ArrayList<Drawable>();
	private static ArrayList<Drawable> midGraphics = new ArrayList<Drawable>();
	private static ArrayList<Drawable> botGraphics = new ArrayList<Drawable>();
	private static int width;
	private static int height;
	
	public View(int width, int height) {
		View.width = width;
		View.height = height;
	}

	public static void addTopGraphic(Drawable d){
		topGraphics.add(d);
	}
	
	public static void removeTopGraphic(Drawable d){
		topGraphics.remove(d);
	}

	public static void addMidGraphic(Drawable d){
		midGraphics.add(d);
	}
	
	public static void removeMidGraphic(Drawable d){
		midGraphics.remove(d);
	}

	public static void addBotGraphic(Drawable d){
		botGraphics.add(d);
	}
	
	public static void removeBotGraphic(Drawable d){
		botGraphics.remove(d);
	}
	
	public static void render(Graphics g, int viewX, int viewY){
//		Display disp = new Display();
//		g.fillRect(0, 0, disp.getGraphicsDevice().getDisplayMode().getWidth(), 
//				disp.getGraphicsDevice().getDisplayMode().getHeight());
		
		for(Drawable d : botGraphics)
			d.draw(g, viewX, viewY, width, height);
			
		for(Drawable d : midGraphics)
			d.draw(g, viewX, viewY, width, height);
			
		for(Drawable d : topGraphics)
			d.draw(g, viewX, viewY, width, height);
			
	}
	
}
