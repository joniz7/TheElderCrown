package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.bottom.GrassTile;
import model.entity.bottom.WaterTile;
import model.entity.top.House;
import model.entity.top.TopEntity;
import model.entity.top.Tree;
import model.path.PathFinder;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import util.Constants;
import util.ObjectType;

public class TestWorld extends World implements TileBasedMap{
	
	private final int WIDTH = 200, HEIGHT = 200;
	
	private final int TREE_SPARSITY = 140, VILLAGER_SPAWN = 120;
	private final int LAKE_COUNT = 3;
	private final float LAKE_WEIGHT = 1f, LAKE_LOSS = 0.02f;

	private ArrayList<Tree> trees = new ArrayList<Tree>();
	private ArrayList<House> houses = new ArrayList<House>();
	
	private Random rnd = new Random();
	
	/**
	 * Creates a new instance of TestWorld.
	 * Remember to call initialize() before use.
	 */
	public TestWorld(){
		super();
	}
	
	/**
	 * Initializes the world.
	 * Generates the map, and creates objects and villagers.
	 */
	public void initialize() {

		initializeGrass();
		initializeLakes();
		initializeTrees();
		initializeHouses();
		
		new PathFinder(this);
		
		for(int i = 112; i < 128; i++) {
			for(int j = 112; j < 128; j++) {
				Point pos = new Point(i, j);
				GrassTile grass = new GrassTile(i, j);
				addEntity(pos, grass);
			}
		}
		
		// Send camera position update to view
		Point pos = new Point(VILLAGER_SPAWN, VILLAGER_SPAWN);
		pcs.firePropertyChange("camera", null, pos);

		initializeVillagers();

	}
	
	/**
	 * Covers the whole map in grass.
	 */
	private void initializeGrass() {
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				GrassTile grass = new GrassTile(i, j);
				Point pos = new Point(i, j);
				grass.setTileID(0);
				addEntity(pos, grass);
			}
		}
	}
	
	/**
	 * A method to randomly generate a set number of lakes.
	 */
	private void initializeLakes(){
		ArrayList<Point> centers = new ArrayList<Point>();
		
		//Create random centerpoints for lakes
		for(int i = 0; i < LAKE_COUNT; i++){
			int x = rnd.nextInt(WIDTH);
			int y = rnd.nextInt(HEIGHT);
			Point pos = new Point(x, y);
			centers.add(pos);
			addEntity(pos, new WaterTile(x, y));
		}
		
		//weight and loss defines the sizes of lakes
		float weight = LAKE_WEIGHT, loss = LAKE_LOSS;
		ArrayList<Point> oldWater = new ArrayList<Point>();
		for(Point c : centers){
			boolean lakeDone = false;
			weight = LAKE_WEIGHT;
			
			oldWater.add(c);
			
			while(!lakeDone){
				ArrayList<Point> newWater = new ArrayList<Point>();
				for(Point p : oldWater){
					if(rnd.nextFloat() < weight && p.x != 0 && botEntities.get(new Point(p.x - 1, p.y)) instanceof GrassTile){
						Point pos = new Point(p.x-1, p.y);
						addEntity(pos, new WaterTile((p.x-1), p.y));
						newWater.add(new Point(p.x-1, p.y));
					}
					if(rnd.nextFloat() < weight  && p.x != 79 && botEntities.get(new Point(p.x + 1, p.y)) instanceof GrassTile){
						addEntity(new Point(p.x+1, p.y), new WaterTile((p.x+1), p.y));
						newWater.add(new Point(p.x+1, p.y));
					}	
					if(rnd.nextFloat() < weight  && p.y != 0 && botEntities.get(new Point(p.x, p.y - 1)) instanceof GrassTile){
						addEntity(new Point(p.x, p.y-1), new WaterTile(p.x, (p.y-1)));
						newWater.add(new Point(p.x, p.y-1));
					}
					if(rnd.nextFloat() < weight  && p.y != 79 && botEntities.get(new Point(p.x, p.y + 1)) instanceof GrassTile){
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
	private void initializeTrees() {
		for(int i = 0; i < WIDTH - 1; i++) {
			for(int j = 0; j < HEIGHT - 1; j++) {
				if(rnd.nextInt(TREE_SPARSITY) == 0 && botEntities.get(new Point(i + 1, j + 1)).getObjectType() == ObjectType.GRASS_TILE){
					Tree tree = new Tree(i + 1, j + 1);
					trees.add(tree);
					tickables.add(tree);
					Point pos = new Point(i + 1, j + 1);
					addEntity(pos, tree);
				}
			}
		}
	}
	
	/**
	 * The method to initialise all the houses in the world.
	 */
	private void initializeHouses() {
		//TODO Draw houses in a nicer fashion
		House house = new House(VILLAGER_SPAWN + 5, VILLAGER_SPAWN + 4, Constants.UP_ENTRANCE);
		houses.add(house);
		addEntity(new Point(VILLAGER_SPAWN + 5, VILLAGER_SPAWN + 2), house);
		
		house = new House(VILLAGER_SPAWN + 1, VILLAGER_SPAWN - 2, Constants.DOWN_ENTRANCE);
		houses.add(house);
		addEntity(new Point(VILLAGER_SPAWN + 1, VILLAGER_SPAWN - 2), house);
		
		house = new House(VILLAGER_SPAWN - 4, VILLAGER_SPAWN +1, Constants.RIGHT_ENTRANCE);
		houses.add(house);
		addEntity(new Point(VILLAGER_SPAWN - 4, VILLAGER_SPAWN +1), house);
		
		house = new House(VILLAGER_SPAWN + 2, VILLAGER_SPAWN + 4, Constants.UP_ENTRANCE);
		houses.add(house);
		addEntity(new Point(VILLAGER_SPAWN + 2, VILLAGER_SPAWN + 4), house);
	}
	
	private void initializeVillagers() {
		for(int i = 0; i < 15; i++){
			Point pos = new Point(VILLAGER_SPAWN, VILLAGER_SPAWN);
			Villager villager = new Villager(this, VILLAGER_SPAWN, VILLAGER_SPAWN);
			tickables.add(villager);
			addEntity(pos, villager);
		}
	}
	
	@Override
	public boolean blocked(PathFindingContext pfc, int x, int y){
		return botEntities.get(new Point(x, y)) instanceof WaterTile;
	}

	@Override
	public float getCost(PathFindingContext pfc, int x, int y){
		return 1.0f;
	}

	@Override
	public int getHeightInTiles(){
		return HEIGHT;
	}

	@Override
	public int getWidthInTiles(){
		return WIDTH;
	}

	@Override
	public void pathFinderVisited(int x, int y){
		
	}
	
	/**
	 * A method to get access to all the ground tiles.
	 * @return a HashMap with all the tiles identified by their position.
	 */
	public HashMap<Point, BottomEntity> getTiles() {
		return botEntities;
	}
	
	/**
	 * Returns all entities that are on the same level as villagers, including villagers.
	 * @return a Hashmap with all entities in the 'middle' layer.
	 */
	public HashMap<Point, MidEntity> getMidObjects(){
		return midEntities;
	}
	
	/**
	 * Returns all entities that are to be rendered on top of villagers.
	 * @return a Hashmap with all entities above the villagers.
	 */
	public HashMap<Point, TopEntity> getTopObjects(){
		return topEntities;
	}
	
	public Tree getTree(int tileX, int tileY){
		if(topEntities.get(new Point(tileX, tileY)) != null &&
				topEntities.get(new Point(tileX, tileY)) instanceof Tree)
			return (Tree) topEntities.get(new Point(tileX, tileY));
		else
			return null;
	}
	
	public void printArea(Point centerPoint){
		Point upperLeft = new Point(centerPoint.x - 4, centerPoint.y - 4);
		Point lowerRight = new Point(centerPoint.x + 3, centerPoint.y + 3);
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		for(int i = (int) upperLeft.getX(); i < lowerRight.getX(); i++){
			for(int j = (int) upperLeft.getY(); j < lowerRight.getY(); j++){
				System.out.print(botEntities.get(new Point(i, j)).getObjectType() + "  -  ");
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		for(int i = (int) upperLeft.getX(); i < lowerRight.getX(); i++){
			for(int j = (int) upperLeft.getY(); j < lowerRight.getY(); j++){
				if(topEntities.get(new Point(i, j)) != null)
					System.out.print(topEntities.get(new Point(i, j)).getObjectType() + "  -  ");
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
