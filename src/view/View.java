package view;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.bottom.HouseFloor;
import model.entity.top.TopEntity;
import model.entity.top.house.*;

import org.newdawn.slick.Graphics;

import util.EntityType;
import view.entity.EntityView;
import view.entity.bot.GrassTileView;
import view.entity.bot.WaterTileView;
import view.entity.mid.VillagerView;
import view.entity.top.TreeView;
import view.entity.top.house.*;

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

		//		System.out.println(""+botGraphics.size()+" "+midGraphics.size()+" "+topGraphics.size());

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
	 * @Author Niklas & Teodor O
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {

		String name = event.getPropertyName();
		if (name.equals("camera")) {
			Point p = (Point)event.getNewValue();
			cameraX = convertCoordinate(p.getX());
			cameraY = convertCoordinate(p.getY());
		}
		else if (name.equals("addTopEntity")) {
			TopEntity entity = (TopEntity) event.getNewValue();
			EntityType type = entity.getEntityType();
			EntityView view = null;
			switch(type){
			case TREE:
				view = new TreeView(entity.getX(), entity.getY());
				break;
			case HOUSE_DOOR:
				HouseDoor door = (HouseDoor) entity;
				view = new HouseDoorView(door.getX(), door.getY(), door.getOrientation());
				break;
			case HOUSE_WALL:
				HouseWall wall = (HouseWall) entity;
				view = new HouseWallView(wall.getX(), wall.getY(), wall.getOrientation());
				break;
			}
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			topGraphics.add(view);
		}
		else if (name.equals("addMidEntity")) {
			MidEntity entity = (MidEntity) event.getNewValue();
			EntityType type = entity.getEntityType();
			EntityView view = null;
			switch(type){
			case VILLAGER:
				view = new VillagerView(entity.getX(), entity.getY());
				break;
			}
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			midGraphics.add(view);
		} 
		else if (name.equals("addBotEntity")) {
			BottomEntity entity = (BottomEntity) event.getNewValue();
			EntityType type = entity.getEntityType();
			EntityView view = null;
			switch(type){
			case GRASS_TILE:
				view = new GrassTileView(entity.getX(), entity.getY());
				break;
			case WATER_TILE:
				view = new WaterTileView(entity.getX(), entity.getY());
				break;
			case HOUSE_FLOOR:
				HouseFloor floor = (HouseFloor) entity;
				view = new HouseFloorView(floor.getX(), floor.getY());
				break;
			}
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			botGraphics.add(view);
		}
		else if (name.equals("removeTopEntity")) {
			// TODO search and remove from list
			//      (should implement GUIDs first)
			throw new UnsupportedOperationException();
		}
		else if (name.equals("removeMidEntity")) {
			throw new UnsupportedOperationException();
		}
		else if (name.equals("removeBotEntity")) {
			throw new UnsupportedOperationException();
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
	/**
	 * Converts from world to view coordinates.
	 * (Should always be used when receiving positions from model!)
	 * 
	 * Note: "worldCoordinate" is here converted to an integer.
	 * 
	 * @Author Niklas 
	 */
	public static int convertCoordinate(double worldCoordinate) {
		return (int)worldCoordinate*TILE_OFFSET;
	}
}
