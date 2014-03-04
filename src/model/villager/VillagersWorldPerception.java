package model.villager;

import java.awt.Point;
import java.util.HashMap;

import org.newdawn.slick.util.pathfinding.TileBasedMap;

import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.top.TopEntity;

public interface VillagersWorldPerception extends TileBasedMap{
	
	public int getWidthInTiles();
	
	public int getHeightInTiles();

	public void setPaused(boolean b);

	public HashMap<Point, BottomEntity> getBotEntities();

	public HashMap<Point, MidEntity> getMidEntities();

	public HashMap<Point, TopEntity> getTopEntities();

}
