package model;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.entity.Agent;
import model.entity.Entity;
import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.bottom.WaterTile;
import model.entity.top.TopEntity;
import model.villager.Perception;
import model.villager.VillagersWorldPerception;
import model.villager.intentions.Action;
import model.villager.order.Order;

import org.newdawn.slick.util.pathfinding.PathFindingContext;

import util.NoPositionFoundException;
import util.Tickable;

public abstract class World implements Tickable, VillagersWorldPerception, PropertyChangeListener{

	// Tickable objects (e.g. trees)
	protected List<Tickable> tickables;
	
	// Agents (e.g. villagers)
	protected HashMap<Point, Agent> agents;
	
	// All entities of this world (grass, trees, villagers, ...)
	protected HashMap<Point, BottomEntity> botEntities;
	protected HashMap<Point, MidEntity> midEntities;
	protected HashMap<Point, TopEntity> topEntities;
	
	// Orders to agents, that should be processed in the next update
	private List<Order> orders;
	
	protected boolean paused;
	public boolean shouldExit;
	
	private final int VIEW_DISTANCE = 10;
	
	protected final PropertyChangeSupport pcs;
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
    public World() {
    	pcs = new PropertyChangeSupport(this);
		
    	botEntities = new HashMap<Point, BottomEntity>();
		midEntities = new HashMap<Point, MidEntity>();
		topEntities = new HashMap<Point, TopEntity>();
		
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
			
			HashMap<Point, BottomEntity> tempBotEnt;
			HashMap<Point, MidEntity> tempMidEnt;
			HashMap<Point, TopEntity> tempTopEnt;
			Perception perception;
			
			while(it.hasNext()) {
				tempBotEnt = new HashMap<Point, BottomEntity>();
				tempMidEnt = new HashMap<Point, MidEntity>();
				tempTopEnt = new HashMap<Point, TopEntity>();
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
	
	public boolean blockedMid(Point pos) {
		return midEntities.containsKey(pos);
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	/**
	 * Initializes the world.
	 */
	public abstract void initialize();
	
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
	
	public HashMap<Point, BottomEntity> getBotEntities() {
		return botEntities;
	}
	
	public HashMap<Point, MidEntity> getMidEntities() {
		return midEntities;
	}
	
	public HashMap<Point, TopEntity> getTopEntities() {
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

}
