package model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.entity.Agent;
import model.entity.Entity;
import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.top.TopEntity;
import model.entity.top.Tree;
import model.entity.top.house.DrinkStorage;
import model.entity.top.house.FoodStorage;
import model.path.PathFinder;
import model.villager.Perception;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import model.villager.intentions.action.Action;
import model.villager.order.Order;

import org.newdawn.slick.util.OperationNotSupportedException;
import org.newdawn.slick.util.pathfinding.PathFindingContext;

import util.Constants;
import util.Copyable;
import util.EntityType;
import util.NoPositionFoundException;
import util.NoSuchEntityException;
import util.Tickable;

public abstract class World implements Tickable, VillagersWorldPerception, PropertyChangeListener {

	// Tickable objects (e.g. trees)
	protected List<Tickable> tickables;
	
	// Agents (e.g. villagers)
	protected HashMap<Point, Agent> agents;
	
	// All entities of this world (grass, trees, villagers, ...)
	protected HashMap<Point, Entity> botEntities;
	protected HashMap<Point, Entity> midEntities;
	protected HashMap<Point, Entity> topEntities;
	
	// Orders to agents, that should be processed in the next update
	private List<Order> orders;
	
	protected boolean paused;
	public boolean shouldExit;
	
	// World configuration
	private final int VIEW_DISTANCE = 10;
	public final int VILLAGER_SPAWN_POS = 40, VILLAGER_COUNT = 1;

	protected final PropertyChangeSupport pcs;
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
    public World() {
    	pcs = new PropertyChangeSupport(this);
		
    	botEntities = new HashMap<Point, Entity>();
		midEntities = new HashMap<Point, Entity>();
		topEntities = new HashMap<Point, Entity>();
		
		tickables  = new ArrayList<Tickable>();
		agents = new HashMap<Point, Agent>();
		orders = new LinkedList<Order>();
		
		shouldExit = false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void tick(){
		if(!paused) {
			// Update all tickables
			for(Tickable t : tickables){
				t.tick();
			}
			updateAgents();
		}
	}

	/**
	 * Updates all agents in the system, and resolves their actions.
	 * 
	 * Goes through all agents and:
	 * 1. Sends them input
	 * 2. Gets and resolves their action
	 */
	private void updateAgents() {

			// Update all villagers
			HashMap<Point, Agent> temp = (HashMap<Point, Agent>)agents.clone();
			Iterator<Map.Entry<Point, Agent>> it = temp.entrySet().iterator();
			
			HashMap<Point, Entity> tempBotEnt;
			HashMap<Point, Entity> tempMidEnt;
			HashMap<Point, Entity> tempTopEnt;
			Perception perception;
			
			while(it.hasNext()) {
				tempBotEnt = new HashMap<Point, Entity>();
				tempMidEnt = new HashMap<Point, Entity>();
				tempTopEnt = new HashMap<Point, Entity>();
				perception = new Perception();
				
				Map.Entry<Point, Agent> e = (Map.Entry<Point, Agent>) it.next();
				
				perception.position = e.getKey();
				Agent agent = e.getValue();
				Entity entity = (Entity)agent;
				
				for(int i=(-VIEW_DISTANCE); i<VIEW_DISTANCE*2; i++){
					for(int j=(-VIEW_DISTANCE); j<VIEW_DISTANCE*2; j++){
						Point p = new Point(perception.position.x+i,perception.position.y+j);
						if(botEntities.get(p) != null){
							tempBotEnt.put(p, botEntities.get(p));
						}
						if(midEntities.get(p) != null){
							tempMidEnt.put(p, midEntities.get(p));
						}
						if(topEntities.get(p) != null){
							tempTopEnt.put(p, topEntities.get(p));
						}
					}
				}
				perception.botEntities = tempBotEnt;
				perception.midEntities = tempMidEnt;
				perception.topEntities = tempTopEnt;
				
	 			// Has this agent been given an order?
				for(Order o : orders) {
					if (o.getToId() == entity.getId()) {
						// Send order information in update 
						perception.order = o;
						orders.remove(o);		
					}
				}

				agent.update(perception);
				Action activeAction = agent.getAction();
				if(activeAction != null && !activeAction.isFailed() && !activeAction.isFinished())
					activeAction.tick(this);
				else
					agent.actionDone();

		}
	}

	@Override
	/**
	 * Checks whether the given position is blocked in any layer.
	 * Note: use blocked(PathFindingContext, Point) instead!
	 */
	public boolean blocked(PathFindingContext pfc, int x, int y){
		return blocked(pfc, new Point(x, y));
	}
	
	/**
	 * Checks whether the given position is blocked in any layer.
	 * @param pfc - ?
	 * @return true if there is something at position, which is blocking
	 */
	public boolean blocked(PathFindingContext pfc, Point p){
		if (botBlocked(p)) return true;
		else if(midBlocked(p)) return true;
		else if(topBlocked(p)) return true;
		else return false;
	}
	/**
	 * Checks whether the specified position is blocked in the middle layer.
	 * @return true if there is something at position, which is blocking
	 */
	public boolean topBlocked(Point p) {
		Entity e = topEntities.get(p);
		return e != null && e.isBlocking();
	}
	/**
	 * Checks whether the specified position is blocked in the middle layer.
	 * @return true if there is something at position, that is blocking
	 */
	public boolean midBlocked(Point p) {
		Entity e = midEntities.get(p);
		return e != null && e.isBlocking();
	}
	/**
	 * Checks whether the specified position is blocked in the bottom layer.
	 * @return true if there is something at position, that is blocking
	 */
	public boolean botBlocked(Point p) {
		Entity e = botEntities.get(p);
		return e != null && e.isBlocking();
	}
	
	/*
	 * Deprecated?
	public boolean blocked(PathFindingContext pfc, int x, int y){
		boolean blocked = false;
		Point p = new Point(x, y);
		if(botEntities.get(p) instanceof WaterTile)
			blocked = true;
		if(midEntities.get(p) != null)
			blocked = midEntities.get(p).isBlocking();
		if(topEntities.get(p) != null)
			blocked = topEntities.get(p).isBlocking();
		return blocked;
	}
	*/
	
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	/**
	 * Initializes the things needed for all Worlds.
	 * 
	 * Subclasses should:
	 * 	1. override (and call) this method,
	 *  2. initialize the map,
	 *  3. call initializeVillagers()
	 */
	public void initialize() {
		new PathFinder(this);
		
		// Send camera position update to view
		Point pos = new Point(VILLAGER_SPAWN_POS, VILLAGER_SPAWN_POS);
		pcs.firePropertyChange("camera", null, pos);
		// Tell view of our world's size
		Point size = new Point(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
		pcs.firePropertyChange("worldsize",null,size);
	}
	
	/**
	 * Creates villagers, and positions them in our world.
	 * Note: Be sure to initialize world properly before calling this method
	 */
	protected final void initializeVillagers() {
		for(int i = 0; i < VILLAGER_COUNT; i++) {
			Point pos = new Point(VILLAGER_SPAWN_POS + 5, VILLAGER_SPAWN_POS+i);
			Villager villager = new Villager(VILLAGER_SPAWN_POS + 5, VILLAGER_SPAWN_POS+i);
			addEntity(pos, villager);
			addVillagerUI(pos, villager);
			villager.getPCS().addPropertyChangeListener(this);
		}
	}
	
	/**
	 * Generates a WorldMap from this World's current state,
	 * and returns it.
	 */
	public WorldMap getWorldMap() {
		WorldMap map = new WorldMap();
		map.botEntities = deepCopy(botEntities);
		map.midEntities = deepCopy(midEntities);
		map.topEntities = deepCopy(topEntities);
		map.tickables = deepCopy(tickables);
		
		// Remove all villagers from map
		List<HashMap<Point, Entity>> villagers = getEntities(EntityType.VILLAGER);
		// Remove from bottom layer (should be empty)
		Set<Point> pointsToRemove = villagers.get(0).keySet();
		for (Point p : pointsToRemove) map.botEntities.remove(p);
		// Remove from middle layer
		pointsToRemove = villagers.get(1).keySet();
		for (Point p : pointsToRemove) map.midEntities.remove(p);
		// Remove from top layer (should be empty)
		pointsToRemove = villagers.get(2).keySet();
		for (Point p : pointsToRemove) map.topEntities.remove(p);

		return map;
	}
	
	/**
	 * Adds an Entity to the bottom layer.
	 * Also notifies View of the change.
	 * 
	 * @param point - the position of the Entity on the map
	 * @param entity - the entity to add
	 * @author Niklas
	 */
	public void addEntity(Point point, BottomEntity entity) {
		botEntities.put(point, entity);
		pcs.firePropertyChange("addBotEntity", null, entity);
	}

	/**
	 * Adds an Entity to the middle layer.
	 * Also notifies View of the change.
	 * 
	 * @param point - the position of the Entity on the map
	 * @param entity - the entity to add
	 * @author Niklas
	 */
	public void addEntity(Point point, MidEntity entity) {
		midEntities.put(point, entity);
		pcs.firePropertyChange("addMidEntity", null, entity);
		if(entity instanceof Agent){
			addAgent(point, (Agent) entity);
		}
	}

	/**
	 * Adds an Entity to the top layer.
	 * Also notifies View of the change.
	 * 
	 * @param point - the position of the Entity on the map
	 * @param entity - the entity to add
	 * @author Niklas
	 */
	public void addEntity(Point point, TopEntity entity) {
		topEntities.put(point, entity);
		pcs.firePropertyChange("addTopEntity", null, entity);
	}
	
	/**
	 * Adds all entities to the specified layer
	 * 
	 * @param entities - a hashmap of entities to add
	 * @param layer - 0 (bot), 1 (mid) or 2 (top)
	 * @author Niklas
	 */
	public void addEntities(HashMap<Point, Entity> entities, int layer) {
		Collection<Point> keys = entities.keySet();
		Iterator<Point> it = keys.iterator();
		// Go through all entities
		while(it.hasNext()){
			Point k = it.next();
			Entity v = entities.get(k);
			// Add to specified layer
			switch (layer) {
				case 0:
					addEntity(k, (BottomEntity)v);
					break;
				case 1:
					addEntity(k, (MidEntity)v);
					break;
				case 2:
					addEntity(k, (TopEntity)v);
					if (v instanceof Tree) {
						// Make view display correctly; hack
						// TODO make programming multi-tile entities prettier. Also in Tree's constructor
						v.updateViewPosition(1, 1, 1);
					}
					break;	
			}
		}
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
	 * Get all entities of the specified type.
	 * Looks through all layers.
	 * 
	 * @param type - the EntityType to search for
	 * @return A list of hashmaps; results for bottom layer is found at index 0 and so on
	 * @author Niklas
	 */
	public List<HashMap<Point, Entity>> getEntities(EntityType type) {
		List<HashMap<Point, Entity>> result = new ArrayList<HashMap<Point, Entity>>(); 
		result.add(getEntities(type,botEntities));
		result.add(getEntities(type,midEntities));
		result.add(getEntities(type,topEntities));
		return result;
	}
	
	/**
	 * Helper method; looks for entities of the specified type in a hashmap
	 * 
	 * @param type - The EntityType to search for
	 * @param src - the hashmap to search through
	 * @return - an new HashMap containing the results (may be empty)
	 */
	private HashMap<Point, Entity> getEntities(EntityType type, HashMap<Point, Entity> src) {
		HashMap<Point, Entity> result = new HashMap<Point, Entity>();
		Collection<Point> keys = src.keySet();
		Iterator<Point> it = keys.iterator();
		while(it.hasNext()){
			Point k = it.next();
			Entity v = src.get(k);
			if(v.getType() == type) {
				result.put(k, v);
			}	
		}
		return result;
	}
	
	/**
	 * Call this when you want a reference to a Tree at a specific location.
	 * @param tileX the x-coordinate of the Tree to be found.
	 * @param tileY the y-coordinate of the Tree to be found
	 * @return if there is a Tree at the specified location it is returned. Otherwise null.
	 * @deprecated not used?
	 */
	public Tree getTree(int tileX, int tileY){
		Entity e = topEntities.get(new Point(tileX, tileY)); 
		if(e != null && e instanceof Tree)
			return (Tree) e;
		else
			return null;
	}
	
	/**
	 * Adds an Entity to the top layer.
	 * Also notifies View of the change.
	 * 
	 * @param point - the position of the Entity on the map
	 * @param entity - the entity to add
	 * @author Niklas
	 */
	public void addVillagerUI(Point point, Villager villager) {
		pcs.firePropertyChange("addVillagerUI", null, villager);
	}
	
	/**
	 * Adds an Entity to the top layer.
	 * Also notifies View of the change.
	 * 
	 * @param point - the position of the Entity on the map
	 * @param entity - the entity to add
	 * @author Niklas
	 */
	public void addTreeUI(Point point, Tree tree) {
		pcs.firePropertyChange("addTreeUI", null, tree);
	}
	
	/**
	 * Adds an Entity to the top layer.
	 * Also notifies View of the change.
	 * 
	 * @param point - the position of the Entity on the map
	 * @param entity - the entity to add
	 * @author Niklas
	 */
	public void addFoodStorageUI(Point point, FoodStorage fs) {
		pcs.firePropertyChange("addFoodStorageUI", null, fs);
	}
	
	protected void addDrinkStorageUI(Point point, DrinkStorage storage2) {
		pcs.firePropertyChange("addDrinkStorageUI", null, storage2);
	}
	
	private void addAgent(Point point, Agent agent){
		agents.put(point, agent);
	}
	
	/**
	 * A method to be called when the game is to exit.
	 */
	public void closeRequested() {
		shouldExit = true;
	}
	
	//TODO Maybe might fail if things are moving at just the wrong time.
	//It should be here and not in TestWorld right?
	/**
	 * Call this if you have an Entity and need to find its position in the world.
	 * 
	 * @param e the Entity to be located.
	 * @return the Point in which the Entity resides.
	 * @throws NoPositionFoundException if no point could be found.
	 */
	public Point getPosition(Entity e) throws NoPositionFoundException{
		Point p = null;
		Collection<Point> midKeys = midEntities.keySet();
		Iterator<Point> midIterator = midKeys.iterator();
		while(midIterator.hasNext()){
			p = midIterator.next();
			if(midEntities.get(p).equals(e))
				return p;
		}
		Collection<Point> topKeys = topEntities.keySet();
		Iterator<Point> topIterator = topKeys.iterator();
		while(topIterator.hasNext()){
			p = topIterator.next();
			if(topEntities.get(p).equals(e))
				return p;
		}
		Collection<Point> botKeys = botEntities.keySet();
		Iterator<Point> botIterator = botKeys.iterator();
		while(botIterator.hasNext()){
			p = botIterator.next();
			if(botEntities.get(p).equals(e))
				return p;
		}
		throw new NoPositionFoundException();
	}
	
	/**
	 * Add an order to be processed by the world in the next update
	 */
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	public HashMap<Point, Entity> getBotEntities() {
		return botEntities;
	}
	
	public HashMap<Point, Entity> getMidEntities() {
		return midEntities;
	}
	
	public HashMap<Point, Entity> getTopEntities() {
		return topEntities;
	}
	
	/**
	 * Returns all entities who are also agents.
	 * @return a HashMap of all the Agents in the game.
	 */
	public HashMap<Point, Agent> getAgents(){
		return agents;
	}

	public void removeAgent(Agent agent) {
		Iterator<Map.Entry<Point, Agent>> it = agents.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<Point, Agent> e = (Map.Entry<Point, Agent>) it.next();
			if(e.getValue() == agent) {
				agents.remove(e.getKey());
				break;
			}
		}
	}

	@Override
	/**
	 * Handles events, sent by entities
	 * TODO comment
	 * @author ?
	 */
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
			if(!midBlocked(pos)) {
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

	
	// -- Path-finding methods --
	// TODO Should be somewhere else? Empty "pathFinderVisited" needed?
	
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
	
	@SuppressWarnings("unchecked")
	/**
	 * Makes a deep copy of a hashmap containing Copyable keys/values. 
	 * @param map - the map to copy
	 * @return a deep copy of the map
	 */
	private static <K, V extends Copyable>
			HashMap<K,V> deepCopy(HashMap<K, V> map) {
		HashMap<K,V> newMap = new HashMap<K,V>();
		Set<K> keys = map.keySet();
		for (K key : keys) {
			K newKey = key;
			// Copy key if possible
			if (key instanceof Copyable) {
				newKey = (K) ((Copyable)key).copy();
			}
			V newVal = (V) map.get(key).copy();
			newMap.put(newKey, newVal);
		}
		return newMap;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Makes a deep copy of an arrayList containing Copyables.
	 * @param list the ArrayList to copy
	 * @return a new ArrayList
	 */
	private static <T extends Copyable>
			List<T> deepCopy(List<T> list) {
		ArrayList<T> newList = new ArrayList<T>();
		for(T item : list) {
			newList.add((T) item.copy());
		}
		return newList;
	}
	
	@Override
	/**
	 * @deprecated - Do not use this
	 * (TODO change: A Tree and a World shouldn't be the same kind of object (Tickable)
	 */
	public World copy() {
		throw new OperationNotSupportedException("helo");
	}
	
}
