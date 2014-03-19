package model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import model.entity.Agent;
import model.entity.Entity;
import model.entity.bottom.BottomEntity;
import model.entity.bottom.GrassTile;
import model.entity.bottom.HouseFloor;
import model.entity.bottom.WaterTile;
import model.entity.mid.Bed;
import model.entity.mid.MidEntity;
import model.entity.top.TopEntity;
import model.entity.top.Tree;
import model.entity.top.house.FoodStorage;
import model.entity.top.house.HouseCorner;
import model.entity.top.house.HouseDoor;
import model.entity.top.house.HouseWall;
import model.path.PathFinder;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.PathFindingContext;

import util.Constants;
import util.EntityType;
import util.NoPositionFoundException;
import util.NoSuchEntityException;

/**
 * A world whose map is generated randomly on load
 * 
 * @author Niklas
 */
public class RandomWorld extends World{
	
	// -- World configuration --
	// Lakes 
	private final float LAKE_COUNT = 8, LAKE_WEIGHT = 1f, LAKE_LOSS = 0.02f;
	// Trees
	private final int TREE_SPARSITY = 280;

	/** @deprecated tickable is enough **/
	private ArrayList<Tree> trees = new ArrayList<Tree>();
	
	private Random rnd = new Random();
	
	/**
	 * Creates a new instance of TestWorld.
	 * Remember to call initialize() before use.
	 */
	public RandomWorld(){
		super();
	}
	
	@Override
	/**
	 * Initializes the world.
	 * Generates the map, and creates objects and villagers.
	 */
	public void initialize() {
		super.initialize();
		
		generateLakes();
		generateHouses();
		generateTrees();
		generateGrass();
		
//		for(int i = 112; i < 128; i++) {
//			for(int j = 112; j < 128; j++) {
//				Point pos = new Point(i, j);
//				GrassTile grass = new GrassTile(i, j);
//				addEntity(pos, grass);
//			}
//		}
		
		Point posG1 = new Point(120, 121);
		GrassTile grass = new GrassTile(120, 121);
		addEntity(posG1, grass);
		
		Point posG2 = new Point(121, 120);
		GrassTile grass2 = new GrassTile(121, 120);
		addEntity(posG2, grass2);
		
		Point posG3 = new Point(119, 120);
		GrassTile grass3 = new GrassTile(119, 120);
		addEntity(posG3, grass3);
		
		Point posG4 = new Point(120, 119);
		GrassTile grass4 = new GrassTile(120, 119);
		addEntity(posG4, grass4);
		
		initializeVillagers();
	}
	
	/**
	 * Covers the whole map in grass, except for where there is water.
	 */
	private void generateGrass() {
		for(int i = 0; i < Constants.WORLD_WIDTH; i++) {
			for(int j = 0; j < Constants.WORLD_HEIGHT; j++) {
				Point pos = new Point(i, j);
				if(!botEntities.containsKey(pos) && topEntities.get(pos) == null){
				GrassTile grass = new GrassTile(i, j);
				addEntity(pos, grass);
				}
			}
		}
	}
	
	/**
	 * A method to randomly generate a set number of lakes.
	 */
	private void generateLakes(){
		ArrayList<Point> centers = new ArrayList<Point>();
		
		//Create random centerpoints for lakes
		for(int i = 0; i < LAKE_COUNT; i++){
			int x = rnd.nextInt(Constants.WORLD_WIDTH);
			int y = rnd.nextInt(Constants.WORLD_HEIGHT);
			Point pos = new Point(x, y);
			centers.add(pos);
			addEntity(pos, new WaterTile(x, y));
		}
		
		//weight and loss defines the sizes of lakes
		float weight = LAKE_WEIGHT;
		ArrayList<Point> oldWater = new ArrayList<Point>();
		for(Point c : centers){
			boolean lakeDone = false;
			weight = LAKE_WEIGHT;
			
			oldWater.add(c);
			
			while(!lakeDone){
				ArrayList<Point> newWater = new ArrayList<Point>();
				for(Point p : oldWater){
					if(rnd.nextFloat() < weight && p.x != 0 && !(botEntities.get(new Point(p.x - 1, p.y)) instanceof WaterTile)){
						Point pos = new Point(p.x-1, p.y);
						addEntity(pos, new WaterTile((p.x-1), p.y));
						newWater.add(new Point(p.x-1, p.y));
					}
					if(rnd.nextFloat() < weight  && p.x != 79 && !(botEntities.get(new Point(p.x + 1, p.y)) instanceof WaterTile)){
						addEntity(new Point(p.x+1, p.y), new WaterTile((p.x+1), p.y));
						newWater.add(new Point(p.x+1, p.y));
					}	
					if(rnd.nextFloat() < weight  && p.y != 0 && !(botEntities.get(new Point(p.x, p.y - 1)) instanceof WaterTile)){
						addEntity(new Point(p.x, p.y-1), new WaterTile(p.x, (p.y-1)));
						newWater.add(new Point(p.x, p.y-1));
					}
					if(rnd.nextFloat() < weight  && p.y != 79 && !(botEntities.get(new Point(p.x, p.y + 1)) instanceof WaterTile)){
						addEntity(new Point(p.x, p.y+1), new WaterTile(p.x, (p.y+1)));
						newWater.add(new Point(p.x, p.y+1));
					}
				}
				
				oldWater = newWater;
				weight = weight - LAKE_LOSS;
				if(weight <= 0)
					lakeDone = true;
			}
		}
		
	}

	/**
	 * A method that spawns a tree with a set probability on each grass tile.
	 */
	private void generateTrees() {
		int sparsity;
		for(int i = 0; i < Constants.WORLD_WIDTH - 1; i++) {
			for(int j = 0; j < Constants.WORLD_HEIGHT - 1; j++) {
				Point p = new Point(i + 1, j + 1);
				sparsity = rnd.nextInt(TREE_SPARSITY);
				if(sparsity == 0 && botEntities.get(p) == null || 
						sparsity == 0 && botEntities.get(p) != null && botEntities.get(p).getType() == EntityType.GRASS_TILE){
					Tree tree = new Tree(i + 1, j + 1);
//					trees.add(tree);
					tickables.add(tree);
					Point pos = new Point(i + 1, j + 1);
					addEntity(pos, tree);
					addTreeUI(pos, tree);
					tree.getPCS().addPropertyChangeListener(this);
				}
			}
		}
	}
	
	/**
	 * The method to initialise all the houses in the world.
	 */
	private void generateHouses() {
		
		for(int i=-18;i<18;i++){
			for(int j=-10;j<10;j++){
				GrassTile grass = new GrassTile(VILLAGER_SPAWN_POS+i, VILLAGER_SPAWN_POS+j);
				Point pos = new Point(VILLAGER_SPAWN_POS+i, VILLAGER_SPAWN_POS+j);
				addEntity(pos, grass);
			}
		}

		buildHouse(VILLAGER_SPAWN_POS - 5, VILLAGER_SPAWN_POS + 4, 3, 2, Constants.UP_ENTRANCE);
		buildHouse(VILLAGER_SPAWN_POS - 12, VILLAGER_SPAWN_POS, 3, 3, Constants.RIGHT_ENTRANCE);
		buildHouse(VILLAGER_SPAWN_POS - 6, VILLAGER_SPAWN_POS - 3, 2, 3, Constants.DOWN_ENTRANCE);
		buildHouse(VILLAGER_SPAWN_POS - 2, VILLAGER_SPAWN_POS, 4, 4, Constants.LEFT_ENTRANCE);
		
		FoodStorage storage = new FoodStorage(38, 33);
		addEntity(new Point(38, 33), storage);
		addFoodStorageUI(new Point(38, 33), storage);
	}
	
	/**
	 * A method to build a house.
	 * 
	 * @param x the x-coordinate of the door to the house.
	 * @param y the y-coordinate of the door to the house.
	 * @param i the width, relative to the door, of the floor.
	 * @param j the depth, relative to the door, of the floor.
	 * @param orientation the direction in which the door will face.
	 */
	private void buildHouse(int x, int y, int i, int j, int orientation) {
		Point p = new Point(x,y);
		int outerWidth = i+2; // +2 to account for the thickness of the walls.
		int outerHeight = j+2;
		switch (orientation){
		case Constants.DOWN_ENTRANCE:
			p = new Point(x-(outerWidth/2), y-outerHeight+1);
			break;
		case Constants.LEFT_ENTRANCE:
			outerWidth = j+2; // +2 because of the walls
			outerHeight = i+2;
			p = new Point(x, y-(outerHeight/2));
			break;
		case Constants.RIGHT_ENTRANCE:
			outerWidth = j+2;  // +2 because of the walls
			outerHeight = i+2;
			p = new Point(x-outerWidth+1, y-(outerHeight/2));
			break;
		case Constants.UP_ENTRANCE:
			p = new Point(x-(outerWidth/2), y);
			break;
		}
		//BUILD WALLS
		HouseWall wall;
		HouseCorner corner;
		boolean cornerPut = false;
		for(int k=1; k<outerWidth; k++){
			if(p.x != x || p.y != y){
				if(cornerPut){
					wall = new HouseWall(p.x, p.y, Constants.UP_ENTRANCE);
					addEntity(new Point(p.x, p.y), wall);
				}else{
					corner = new HouseCorner(p.x, p.y, Constants.UP_ENTRANCE);
					addEntity(new Point(p.x, p.y), corner);
					cornerPut = true;
				}
			}
			p.translate(1, 0);
		}
		cornerPut = false;
		for(int k=1; k<outerHeight; k++){
			if(p.x != x || p.y != y){
				if(cornerPut){
					wall = new HouseWall(p.x, p.y, Constants.RIGHT_ENTRANCE);
					addEntity(new Point(p.x, p.y), wall);
				}else{
					corner = new HouseCorner(p.x, p.y, Constants.RIGHT_ENTRANCE);
					addEntity(new Point(p.x, p.y), corner);
					cornerPut = true;
				}
			}
			p.translate(0, 1);
		}
		cornerPut = false;
		for(int k=1; k<outerWidth; k++){
			if(p.x != x || p.y != y){
				if(cornerPut){
					wall = new HouseWall(p.x, p.y, Constants.DOWN_ENTRANCE);
					addEntity(new Point(p.x, p.y), wall);
				}else{
					corner = new HouseCorner(p.x, p.y, Constants.DOWN_ENTRANCE);
					addEntity(new Point(p.x, p.y), corner);
					cornerPut = true;
				}
			}
			p.translate(-1, 0);
		}
		cornerPut = false;
		for(int k=1; k<outerHeight; k++){
			if(p.x != x || p.y != y){
				if(cornerPut){
					wall = new HouseWall(p.x, p.y, Constants.LEFT_ENTRANCE);
					addEntity(new Point(p.x, p.y), wall);
				}else{
					corner = new HouseCorner(p.x, p.y, Constants.LEFT_ENTRANCE);
					addEntity(new Point(p.x, p.y), corner);
					cornerPut = true;
				}
			}
			p.translate(0, -1);
		}
		cornerPut = false;
		p.translate(1, 1);
		//ADD FLOOR
		HouseFloor floor;
		Bed bed;
		for(int k=0; k<outerWidth-2; k++){
			for(int l=0; l<outerHeight-2; l++){
				if(l==0 || l==outerHeight-3){
					bed = new Bed(p.x,p.y);
					addEntity(new Point(p.x, p.y), bed);
					floor = new HouseFloor(p.x, p.y);
					addEntity(new Point(p.x, p.y), floor);
					p.translate(0, 1);
				}else{
				floor = new HouseFloor(p.x, p.y);
				addEntity(new Point(p.x, p.y), floor);
				p.translate(0, 1);
				}
			}
			p.translate(1, -(outerHeight-2)); //-2 to accoun for the absence of walls in this case.
		}
		//ADD DOOR
		Point doorPoint = new Point(x, y);
		floor = new HouseFloor(x, y);
		addEntity(doorPoint, floor);
		topEntities.remove(doorPoint);
		HouseDoor door = new HouseDoor(x, y, orientation);
		addEntity(doorPoint, door);
		
	}

	/**
	 * Debuggin purposes.
	 * @param centerPoint
	 */
	public void printArea(Point centerPoint){
		Point upperLeft = new Point(centerPoint.x - 4, centerPoint.y - 4);
		Point lowerRight = new Point(centerPoint.x + 3, centerPoint.y + 3);
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		for(int i = (int) upperLeft.getX(); i < lowerRight.getX(); i++){
			for(int j = (int) upperLeft.getY(); j < lowerRight.getY(); j++){
				System.out.print(botEntities.get(new Point(i, j)).getType() + "  -  ");
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		for(int i = (int) upperLeft.getX(); i < lowerRight.getX(); i++){
			for(int j = (int) upperLeft.getY(); j < lowerRight.getY(); j++){
				if(topEntities.get(new Point(i, j)) != null)
					System.out.print(topEntities.get(new Point(i, j)).getType() + "  -  ");
				else
					System.out.print("NULL  -  ");

			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}

}
