package view;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.Timer;

import model.entity.bottom.Bed;
import model.entity.bottom.BottomEntity;
import model.entity.mid.MidEntity;
import model.entity.top.TopEntity;
import model.entity.top.Tree;
import model.entity.top.house.DrinkStorage;
import model.entity.top.house.FoodStorage;
import model.entity.top.house.HouseCorner;
import model.entity.top.house.HouseDoor;
import model.entity.top.house.HouseWall;
import model.villager.Villager;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.renderer.SGL;

import util.Constants;
import util.EntityType;
import util.ImageLoader;
import view.entity.EntityView;
import view.entity.bot.GrassTileView;
import view.entity.bot.WaterTileView;
import view.entity.mid.BedView;
import view.entity.mid.VillagerView;
import view.entity.top.Helper3View;
import view.entity.top.TreeView;
import view.entity.top.house.DrinkStorageView;
import view.entity.top.house.FoodStorageView;
import view.entity.top.house.HouseCornerView;
import view.entity.top.house.HouseDoorView;
import view.entity.top.house.HouseFloorView;
import view.entity.top.house.HouseWallView;
import view.ui.BedUI;
import view.ui.DrinkStorageUI;
import view.ui.FoodStorageUI;
import view.ui.TreeUI;
import view.ui.VillagerUI;

public class WorldView implements PropertyChangeListener {

	private ArrayList<EntityView> topGraphics = new ArrayList<EntityView>();
	private ArrayList<EntityView> midGraphics = new ArrayList<EntityView>();
	private ArrayList<EntityView> botGraphics = new ArrayList<EntityView>();
	private ArrayList<EntityView> UI = new ArrayList<EntityView>();

	//Day-Night cycle
	private Color overlay;
	private int hours;
	private float alpha, alphaMod;
	private int cX, cY;
	private float rot;
	private boolean night = false;
	
	private static final int SCROLL_SPEED = 8;
	private static int width, height;
	private static int cameraX, cameraY;
	
	//Day-Night-stuff
	private Image lightMap = ImageLoader.getImage("light");
	private Image clock = ImageLoader.getImage("clock");
	private Image clockBorder = ImageLoader.getImage("clockBorder");
	
	// The size of each tile in pixels (?)
	public static final int TILE_OFFSET = 20;
	
	//The relative speed of the simulation, eg 1.5x
	private double simSpeed;
	
	//Color of the fading simulation speed text
	private Color fadeText;
	//private Font font;
	//private UnicodeFont uFont;
	
	//Used for fading of text
	private ActionListener action;
	private Timer timer;
	private int textAlpha;

	public WorldView(int width, int height) {
		WorldView.width = width;
		WorldView.height = height;
		overlay = new Color(Color.black);
		overlay.a = 0;
		simSpeed=1.0;
		textAlpha=0;
		//font = new Font("Verdana", Font.BOLD, 20);
		//uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		fadeText = new org.newdawn.slick.Color(0, 0, 0, textAlpha);
        action = new ActionListener(){   
            @Override
            public void actionPerformed(ActionEvent event) {
            	textAlpha -=10;
            	fadeText = new org.newdawn.slick.Color(0, 0, 0, textAlpha);
               if(fadeText.a <=0){
            	   timer.stop();
               }
            }
        };
        timer=new Timer(100, action);
        timer.setInitialDelay(0);
	}

	public void addTopGraphic(EntityView d){
		topGraphics.add(d);
	}

	public void removeTopGraphic(EntityView d){
		topGraphics.remove(d);
	}

	public void addMidGraphic(EntityView d){
		midGraphics.add(d);
	}

	public void removeMidGraphic(EntityView d){
		midGraphics.remove(d);
	}

	public void addBotGraphic(EntityView d){
		botGraphics.add(d);
	}

	public void removeBotGraphic(EntityView d){
		botGraphics.remove(d);
	}

	public int getX() {
		return cameraX;
	}

	public int getY() {
		return cameraY;
	}

	public void incX(){
		if(cameraX<(modelToViewCoordinate(Constants.WORLD_WIDTH)-width))
			cameraX += SCROLL_SPEED;
	}

	public void incY(){
		if(cameraY<(modelToViewCoordinate(Constants.WORLD_HEIGHT)-height))
			cameraY += SCROLL_SPEED;
	}

	public void decX(){
		if(cameraX>modelToViewCoordinate(0))
			cameraX -= SCROLL_SPEED;
	}

	public void decY(){
		if(cameraY>modelToViewCoordinate(0))
			cameraY -= SCROLL_SPEED;
	}

	public void render(Graphics g) throws ConcurrentModificationException{
		g.clearAlphaMap();
		g.setDrawMode(Graphics.MODE_NORMAL);
		
		for(EntityView d : botGraphics)
			d.draw(g, cameraX, cameraY, width, height);
		for(EntityView d : midGraphics)
			d.draw(g, cameraX, cameraY, width, height);
		for(EntityView d : topGraphics)
			d.draw(g, cameraX, cameraY, width, height);
		
		g.setColor(overlay);
		overlay.a = alpha;
		g.fillRect(0, 0, width, height);
		
		if(night){
		    
		    GL11.glColorMask(false, false, false, true);
		    
		    lightMap.draw(cX-256, cY-256);
		     
		    GL11.glColorMask(true,true,true,true);
		    GL11.glBlendFunc(GL11.GL_DST_ALPHA,GL11.GL_ONE_MINUS_DST_ALPHA);
		    
		    g.setColor(Color.black);
		    g.fillRect(0, 0, width, height);
		}
		
		g.setDrawMode(Graphics.MODE_NORMAL);
		
		for(EntityView d : UI)
			d.draw(g, cameraX, cameraY, width, height);
		
		
		
		g.rotate((width / 2), -110, rot);
		g.drawImage(clock, (width / 2) - 170, -280);
		g.rotate((width / 2), -110, -rot);
		g.drawImage(clockBorder, (width / 2) - 150, 0); 
		g.setColor(fadeText);
	    /*uFont.getEffects().add(new ColorEffect(fadeText));
		uFont.addNeheGlyphs();
		uFont.loadGlyphs();
		g.setFont(uFont);*/
		g.drawString(simSpeed +"x", 650, 550);
		
		//g.drawString(simSpeed +"x", 650, 500);
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
			centerCamera(p);
		}
		else if(name.equals("simSpeed")){
			simSpeed=(double) event.getNewValue();
			textAlpha=255;
			fadeText = new org.newdawn.slick.Color(0, 0, 0, textAlpha);
			timer.start();
		}else if (name.equals("addTopEntity")) {
			TopEntity entity = (TopEntity) event.getNewValue();
			EntityType type = entity.getType();
			EntityView view = null;
			switch(type){
			case TREE:
				view = new TreeView(entity.getX()-1, entity.getY()-1);
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
			case FOOD_STORAGE:
				FoodStorage fs = (FoodStorage) entity;
				view = new FoodStorageView(fs.getX(), fs.getY());
				break;
			case DRINK_STORAGE:
				DrinkStorage ds = (DrinkStorage) entity;
				view = new DrinkStorageView(ds.getX(), ds.getY());
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
						villager.getLength(), villager.getWeight());
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
				view = new HouseFloorView(entity.getX(), entity.getY());
				break;
			case BED:
				view = new BedView(entity.getX(),entity.getY());
				break;
			}
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			botGraphics.add(view);
		}else if (name.equals("addVillagerUI")) {
			
			Villager entity = (Villager) event.getNewValue();
			EntityView view = new VillagerUI(entity);
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			UI.add(view);
		}else if (name.equals("addTreeUI")) {
			Tree entity = (Tree) event.getNewValue();
			EntityView view = new TreeUI();
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			UI.add(view);
		}else if (name.equals("addFoodStorageUI")) {
			FoodStorage entity = (FoodStorage) event.getNewValue();
			EntityView view = new FoodStorageUI();
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			UI.add(view);
		}else if (name.equals("addDrinkStorageUI")) {
			DrinkStorage entity = (DrinkStorage) event.getNewValue();
			EntityView view = new DrinkStorageUI();
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			UI.add(view);
		}else if (name.equals("addBedUI")){
			Bed entity = (Bed) event.getNewValue();
			EntityView view = new BedUI(entity);
			PropertyChangeSupport pcs = entity.getPCS();
			pcs.addPropertyChangeListener(view);
			UI.add(view);
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
		else if(name.equals("setTime")){
			Integer time = (Integer) event.getNewValue();
			int hours = time / 750;
			
			if(hours >= 20 && hours < 21){
				alphaMod = 0.00068f;
			}else if(hours >= 21 && hours < 22){
				alphaMod = -0.005f;
				night = true;
			}else if(hours == 22){
				alphaMod = 0;
			}else if(hours >= 6 && hours <= 7){
				alphaMod = -0.00068f;
				night = false;
			}else if(hours >= 5 && hours < 6){
				alphaMod = 0.005f;
			}else if(hours == 8){
				alphaMod = 0;
			}

			alpha = alpha + alphaMod;
			rot = time / 50;
			
//			System.out.println("Hours -" + hours + " : " + alpha);
			
			Point cPos = (Point) event.getOldValue();
			if(cPos != null){
				cX = cPos.x;
				cY = cPos.y;
			}else{
				cX = 0;
				cY = 0;
			}
		}else if(name.equals("helper")){
			Point p = (Point) event.getOldValue();
			topGraphics.add(new Helper3View(p.x, p.y));
		}
	}
	
	/**
	 * Moves the camera to the specified position
	 * 
	 * @param pos - model coordinates to center on
	 * @author Niklas
	 */
	public void centerCamera(Point p) {
		cameraX = modelToViewCoordinate(p.x) - width/2;
		cameraY = modelToViewCoordinate(p.y) - height/2;	
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
	 * @deprecated use int version instead
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
