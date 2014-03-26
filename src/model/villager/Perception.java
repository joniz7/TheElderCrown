package model.villager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

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
	public HashMap<Point, Entity> midEntities;
	public HashMap<Point, Entity> topEntities;
	// All villagers we currently see. May be null
	public HashMap<Point, Villager> villagers;
	
	/**
	 * Checks whether this perception contains any villagers other than myself
	 * @return
	 */
	public boolean hasVillagers() {
		return villagers != null;
	}
	
}
