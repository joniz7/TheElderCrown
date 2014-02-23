package model;


import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import util.Tickable;
import model.entity.Agent;
import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.top.TopEntity;

public abstract class World implements Tickable{

	// The agents of this world (villagers)
	protected ArrayList<Tickable> tickables = new ArrayList<Tickable>();
	
	// The entities of this world (grass, trees, villagers, ...)
	protected HashMap<Point, BottomEntity> botEntities;
	protected HashMap<Point, MidEntity> midEntities;
	protected HashMap<Point, TopEntity> topEntities;
	protected HashMap<Point, Agent> agents;
	
	protected boolean paused;
	public boolean shouldExit;
	
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
		agents = new HashMap<Point, Agent>();
		shouldExit = false;
		}
	
	@Override
	public void tick(){
		if(!paused) {
			for(Tickable t : tickables){
				t.tick();
			}
			
			Iterator<Map.Entry<Point, Agent>> it = agents.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry<Point, Agent> e = (Map.Entry<Point, Agent>) it.next();
				e.getValue().update(e.getKey());
			}
		}
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
	
}
