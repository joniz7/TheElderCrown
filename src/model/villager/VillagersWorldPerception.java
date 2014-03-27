package model.villager;

import java.awt.Point;
import java.util.HashMap;

import model.entity.Entity;
import model.villager.order.Order;

import org.newdawn.slick.util.pathfinding.TileBasedMap;

public interface VillagersWorldPerception extends TileBasedMap{
	
	public int getWidthInTiles();
	
	public int getHeightInTiles();

	public void setPaused(boolean b);

	public HashMap<Point, Entity> getBotEntities();

	public HashMap<Point, Entity> getMidEntities();

	public HashMap<Point, Entity> getTopEntities();
	
	public void addOrder(Order o);

}
