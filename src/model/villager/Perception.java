package model.villager;

import java.awt.Point;
import java.util.HashMap;

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
	
}
