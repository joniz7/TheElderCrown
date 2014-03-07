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
import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.bottom.GrassTile;
import model.entity.bottom.HouseFloor;
import model.entity.bottom.WaterTile;
import model.entity.top.TopEntity;
import model.entity.top.Tree;
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

public class TestWorld extends World{
	
	// -- World configuration --
	// Villagers
	private final int VILLAGER_SPAWN_POS = 40, VILLAGER_COUNT = 8;
	// Lakes 
	private final float LAKE_COUNT = 8, LAKE_WEIGHT = 1f, LAKE_LOSS = 0.02f;
	// Trees
	private final int TREE_SPARSITY = 280;

	private ArrayList<Tree> trees = new ArrayList<Tree>();
	
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


		initializeLakes();
		initializeHouses();
		initializeGrass();
		initializeTrees();
		
		new PathFinder(this);
		
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
		
		// Send camera position update to view
		Point pos = new Point(VILLAGER_SPAWN_POS, VILLAGER_SPAWN_POS);
		pcs.firePropertyChange("camera", null, pos);
		Point size = new Point(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		pcs.firePropertyChange("worldsize",null,size);

		initializeVillagers();
	}
	
	/**
	 * Covers the whole map in grass, except for where there is water.
	 */
	private void initializeGrass() {
		for(int i = 0; i < Constants.WORLD_WIDTH; i++) {
			for(int j = 0; j < Constants.WORLD_HEIGHT; j++) {
				Point pos = new Point(i, j);
				if(!botEntities.containsKey(pos)){
				GrassTile grass = new GrassTile(i, j);
				addEntity(pos, grass);
				}
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
				System.out.println("Generating lakes");
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
		for(int i = 0; i < Constants.WORLD_WIDTH - 1; i++) {
			for(int j = 0; j < Constants.WORLD_HEIGHT - 1; j++) {
				if(rnd.nextInt(TREE_SPARSITY) == 0 && botEntities.get(new Point(i + 1, j + 1)).getType() == EntityType.GRASS_TILE){
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
		for(int k=0; k<outerWidth-2; k++){
			for(int l=0; l<outerHeight-2; l++){
				floor = new HouseFloor(p.x, p.y);
				addEntity(new Point(p.x, p.y), floor);
				p.translate(0, 1);
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

	private void initializeVillagers() {
		for(int i = 0; i < VILLAGER_COUNT; i++) {
			Point pos = new Point(VILLAGER_SPAWN_POS + 5, VILLAGER_SPAWN_POS+i);
			Villager villager = new Villager(VILLAGER_SPAWN_POS + 5, VILLAGER_SPAWN_POS+i);
			addEntity(pos, villager);
			villager.getPCS().addPropertyChangeListener(this);
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
		return Constants.WORLD_HEIGHT;
	}

	@Override
	public int getWidthInTiles(){
		return Constants.WORLD_WIDTH;
	}

	@Override
	public void pathFinderVisited(int x, int y){
		
	}
		
	/**
	 * Returns all entities who are also agents.
	 * @return a HashMap of all the Agents in the game.
	 */
	public HashMap<Point, Agent> getAgents(){
		return agents;
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
	
	/**
	 * Call this when you want a reference to a Tree at a specific location.
	 * @param tileX the x-coordinate of the Tree to be found.
	 * @param tileY the y-coordinate of the Tree to be found
	 * @return if there is a Tree at the specified location it is returned. Otherwise null.
	 */
	public Tree getTree(int tileX, int tileY){
		if(topEntities.get(new Point(tileX, tileY)) != null &&
				topEntities.get(new Point(tileX, tileY)) instanceof Tree)
			return (Tree) topEntities.get(new Point(tileX, tileY));
		else
			return null;
	}
	
	/**
	 * Call this when you want a reference to a specific entity at a specific position.
	 * 
	 * @param pos the position in which you want to find the Entity.
	 * @param type the Entity type desired.
	 * @return the entity of the desired type at the specified Point
	 * @throws NoSuchEntityException if there is no Entity of the specified type at the specified Point.
	 */
	public Entity getEntity(Point pos, EntityType type) throws NoSuchEntityException{
		Entity e = null;
		if(midEntities.get(pos).getType() == type)
			e = midEntities.get(pos);
		else if(topEntities.get(pos).getType() == type)
			e = topEntities.get(pos);
		else
			throw new NoSuchEntityException();
		return e;
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

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();

		if(name.equals("move")){
			Point p = null;
			Point pos = (Point) event.getNewValue();
			Villager villager = (Villager) event.getSource();
			try {
				p = getPosition(villager);
			} catch (NoPositionFoundException e) {
				e.printStackTrace();
			}
			if(!blockedMid(pos)) {
				agents.put(pos, villager);
				midEntities.put(pos, villager);
				agents.remove(p);
				midEntities.remove(p);
			}
		}else if(name.equals("status")){
			String evtString = (String) event.getNewValue();
			if(evtString.equals("dead")){
				Iterator<Map.Entry<Point, Agent>> it = agents.entrySet().iterator();
				Agent agent = (Agent) event.getSource();
				while(it.hasNext()) {
					Map.Entry<Point, Agent> e = (Map.Entry<Point, Agent>) it.next();
					if(e.getValue() == agent) {
						agents.remove(e.getKey());
						break;
					}
				}
			}
		}
	}
}
