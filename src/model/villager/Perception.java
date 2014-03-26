package model.villager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import model.entity.Agent;
import model.entity.Entity;
import model.villager.order.Order;

/**
 * Simple wrapper class for all things the Villagers perceive from the world.
 * 
 * @author Karl-Agnes
 *
 */
public class Perception {

	public Point position;
	public Order order;
	public HashMap<Point, Entity> botEntities;
	public HashMap<Point, Entity> topEntities;
	
	
	// All agents and villagers we currently see
	// All Villagers appear twice - in both hashmaps
	// The hashmaps may be null, if no agents/villagers exist in sight
	public HashMap<Point, Villager> villagers;
	public HashMap<Point, Agent> agents;
	
	// All midEntities not already in villagers/agents
	public HashMap<Point, Entity> midEntities;
	
	/**
	 * Checks whether this perception contains any villagers other than myself
	 * @return
	 */
	public boolean hasVillagers() {
		return villagers != null;
	}
	
}
