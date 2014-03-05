package model.villager;

import java.awt.Point;
import java.util.HashMap;

import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.top.TopEntity;
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
	public HashMap<Point, BottomEntity> botEntities;
	public HashMap<Point, MidEntity> midEntities;
	public HashMap<Point, TopEntity> topEntities;
	
}
