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
import model.villager.Villager;
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
	private static int width, height;
	private static int cameraX, cameraY;
	private static int worldXSize, worldYSize;
	
	// The size of each tile in pixels (?)
	public static final int TILE_OFFSET = 20;

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
		if(cameraX<modelToViewCoordinate(150))
			cameraX += SCROLL_SPEED;
//			System.out.println("CameraX: "+cameraX);
	}

	public void incY(){
		if(cameraY<modelToViewCoordinate(150))
			cameraY += SCROLL_SPEED;
//			System.out.println("CameraY: "+cameraY);
	}

	public void decX(){
		if(cameraX>modelToViewCoordinate(0))
			cameraX -= SCROLL_SPEED;
//			System.out.println("CameraX: "+cameraX);
	}

	public void decY(){
		if(cameraY>modelToViewCoordinate(0))
			cameraY -= SCROLL_SPEED;
//			System.out.println("CameraY: "+cameraY);
	}

	public static void render(Graphics g){
//		System.out.println("Size: "+botGraphics.size());
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
			cameraX = modelToViewCoordinate(p.getX()) - width/2;
			cameraY = modelToViewCoordinate(p.getY()) - height/2;
		}
		else if(name.equals("worldsize")){
			Point size = (Point) event.getNewValue();
			worldXSize = modelToViewCoordinate((int) size.getX());
			worldYSize = modelToViewCoordinate((int) size.getY());
		}
		else if (name.equals("addTopEntity")) {
			TopEntity entity = (TopEntity) event.getNewValue();
			EntityType type = entity.getType();
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
			case HOUSE_CORNER:
				HouseCorner corner = (HouseCorner) entity;
				view = new HouseCornerView(corner.getX(), corner.getY(), corner.getOrientation());
				break;
			}
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			topGraphics.add(view);
		}
		else if (name.equals("addMidEntity")) {
			MidEntity entity = (MidEntity) event.getNewValue();
			EntityType type = entity.getType();
			EntityView view = null;
			switch(type){
			case VILLAGER:
				Villager villager = (Villager) entity;
				view = new VillagerView(entity.getX(), entity.getY(), 
						villager.getHeight(), villager.getWeight());
				break;
			}
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			midGraphics.add(view);
		} 
		else if (name.equals("addBotEntity")) {
			BottomEntity entity = (BottomEntity) event.getNewValue();
			EntityType type = entity.getType();
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
	public static int modelToViewCoordinate(int worldCoordinate) {
		return worldCoordinate*TILE_OFFSET;
	}
	/**
	 * Converts from view to world coordinates.
	 * @Author Niklas 
	 */
	public static int viewToModelCoordinate(int viewCoordinate) {
		return viewCoordinate/TILE_OFFSET;
	}
	
	/**
	 * Converts from world to view coordinates.
	 * (Should always be used when receiving positions from model!)
	 * 
	 * Note: "worldCoordinate" is here converted to an integer.
	 *
	 * @deprecated
	 * @Author Niklas 
	 */
	public static int modelToViewCoordinate(double worldCoordinate) {
		return (int)worldCoordinate*TILE_OFFSET;
	}
	
	/**
	 * Converts from window to model coordinates.
	 * @Author Niklas 
	 */
	public static Point windowToModelCoordinates(Point windowCoords) {
		int viewX = (int)windowCoords.getX() + cameraX;
		int viewY = (int)windowCoords.getY() + cameraY;
		int modelX = viewToModelCoordinate(viewX);
		int modelY = viewToModelCoordinate(viewY);
		return new Point(modelX,modelY);
	}
	
	public void setSize(int width, int height){
		this.height = height;
		this.width = width;
	}
}
