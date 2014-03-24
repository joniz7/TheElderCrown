package model.villager;

import java.awt.Point;
import java.util.HashMap;

import org.newdawn.slick.util.pathfinding.TileBasedMap;

import model.entity.Entity;
import model.entity.bottom.BottomEntity;
import model.entity.mid.MidEntity;
import model.entity.top.TopEntity;

public interface VillagersWorldPerception extends TileBasedMap{
	
	public int getWidthInTiles();
	
	public int getHeightInTiles();

	public void setPaused(boolean b);

	public HashMap<Point, Entity> getBotEntities();

	public HashMap<Point, Entity> getMidEntities();

	public HashMap<Point, Entity> getTopEntities();

}
