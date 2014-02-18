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
	
	private final int TREE_SPARSITY = 280, VILLAGER_SPAWN = 120, NBR_OF_HOUSES = 1;
	private final int LAKE_COUNT = 8;
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
		
		// TODO Add grass tiles again? why?
		/*
		for(int i = 118; i < 123; i++) {
			for(int j = 118; j < 123; j++) {
				Point pos = new Point(i, j);
				GrassTile grass = new GrassTile(i, j);
				addEntity(pos, grass);
			}
		}
		*/
		
		// Send camera position update to view
		Point pos = new Point(VILLAGER_SPAWN, VILLAGER_SPAWN);
		pcs.firePropertyChange("camera", null, pos);

		initializeVillagers();

	}
	
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
	
	private void initializeHouses() {
		for(int i = 0; i < NBR_OF_HOUSES; i++){
			House house = new House(VILLAGER_SPAWN + 3, VILLAGER_SPAWN + 2, Constants.LEFT_ENTRANCE);
			houses.add(house);
			addEntity(new Point(VILLAGER_SPAWN + 3, VILLAGER_SPAWN + 2), house);
		}
	}
	
	private void initializeVillagers() {
		for(int i = 0; i < 100; i++){
			Point pos = new Point(VILLAGER_SPAWN, VILLAGER_SPAWN);
			Villager villager = new Villager(this, VILLAGER_SPAWN, VILLAGER_SPAWN);
			tickables.add(villager);
			addEntity(pos, villager);
		}
	}
	
	@Override
	public boolean blocked(PathFindingContext pfc, int x, int y){
		if(botEntities.get(new Point(x, y)) != null && botEntities.get(new Point(x, y)).isBlocking())
			return true;
		if(midEntities.get(new Point(x, y)) != null && midEntities.get(new Point(x, y)).isBlocking())
			return true;
		if(topEntities.get(new Point(x, y)) != null && topEntities.get(new Point(x, y)).isBlocking())
			return true;
		
		return false;
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

	public HashMap<Point, BottomEntity> getTiles() {
		return botEntities;
	}
	
	public HashMap<Point, MidEntity> getMidObjects(){
		return midEntities;
	}
	
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
