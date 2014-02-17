package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import util.Position;

public class View implements PropertyChangeListener {

	private static ArrayList<EntityView> topGraphics = new ArrayList<EntityView>();
	private static ArrayList<EntityView> midGraphics = new ArrayList<EntityView>();
	private static ArrayList<EntityView> botGraphics = new ArrayList<EntityView>();
	private static final int SCROLL_SPEED = 8;
	private static final int TILE_OFFSET = 20;
	private static int width, height;
	private static int cameraX, cameraY;
	

	
	public View(int width, int height) {
		View.width = width;
		View.height = height;
	}

	public static void addTopGraphic(EntityView d){
		topGraphics.add(d);
	}
	
	public static void removeTopGraphic(EntityView d){
		topGraphics.remove(d);
	}

	public static void addMidGraphic(EntityView d){
		midGraphics.add(d);
	}
	
	public static void removeMidGraphic(EntityView d){
		midGraphics.remove(d);
	}

	public static void addBotGraphic(EntityView d){
		botGraphics.add(d);
	}
	
	public static void removeBotGraphic(EntityView d){
		botGraphics.remove(d);
	}
	
	public int getX() {
		return cameraX;
	}

	public int getY() {
		return cameraY;
	}
	
	public void incX(){
		cameraX += SCROLL_SPEED;
	}
	
	public void incY(){
		cameraY += SCROLL_SPEED;
	}
	
	public void decX(){
		cameraX -= SCROLL_SPEED;
	}
	
	public void decY(){
		cameraY -= SCROLL_SPEED;
	}
	
	public static void render(Graphics g){
//		Display disp = new Display();
//		g.fillRect(0, 0, disp.getGraphicsDevice().getDisplayMode().getWidth(), 
//				disp.getGraphicsDevice().getDisplayMode().getHeight());
		
		for(EntityView d : botGraphics)
			d.draw(g, cameraX, cameraY, width, height);
			
		for(EntityView d : midGraphics)
			d.draw(g, cameraX, cameraY, width, height);
			
		for(EntityView d : topGraphics)
			d.draw(g, cameraX, cameraY, width, height);
			
	}

	/**
	 * Is called when anything changed in the model as a whole. 
	 * Updates the view accordingly.
	 * 
	 * (Note that this is just for general model events -
	 *  not for Entity position updates and such)
	 *  
	 * @Author Niklas
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		switch (event.getPropertyName()) {
			// Camera position changed
			case "camera":
				Position p = (Position)event.getNewValue();
				cameraX = convertCoordinate(p.getX());
				cameraY = convertCoordinate(p.getY());
				break;
		}
	}
	
	/**
	 * Converts from world to view coordinates.
	 * (Should always be used when receiving positions from model!)
	 * @Author Niklas 
	 */
	public static int convertCoordinate(int worldCoordinate) {
		return worldCoordinate*TILE_OFFSET;
	}
}
