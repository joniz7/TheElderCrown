package model.villager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.bottom.NullTile;
import model.entity.top.TopEntity;

import org.newdawn.slick.util.pathfinding.PathFindingContext;

import util.Constants;
import util.EntityType;

public class VillagerWorld implements VillagersWorldPerception {
	
	private HashMap<Point, BottomEntity> botEntities;
	private HashMap<Point, MidEntity> midEntities;
	private HashMap<Point, TopEntity> topEntities;
	
	/**
	 * Constructor
	 */
	public VillagerWorld(){
		botEntities = new HashMap<Point, BottomEntity>();
		midEntities = new HashMap<Point, MidEntity>();
		topEntities = new HashMap<Point, TopEntity>();
		NullTile tile;
		for(int i=0; i<Constants.WORLD_WIDTH; i++){
			for(int j=0; j<Constants.WORLD_HEIGHT; j++){
				tile = new NullTile(i, j, EntityType.NULL_TILE);
				botEntities.put(new Point(i,j), tile);
			}
		}
	}
	
	@Override
	public boolean blocked(PathFindingContext arg0, int x, int y) {
		if(botEntities.get(new Point(x, y)) != null && botEntities.get(new Point(x, y)).isBlocking()){
			return true;
		}
		if(midEntities.get(new Point(x, y)) != null && midEntities.get(new Point(x, y)).isBlocking()){
			return true;
		}
		if(topEntities.get(new Point(x, y)) != null && topEntities.get(new Point(x, y)).isBlocking()){
			return true;
		}
		return false;
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getWidthInTiles() {
		return Constants.WORLD_WIDTH;
	}

	@Override
	public int getHeightInTiles() {
		return Constants.WORLD_HEIGHT;
	}

	@Override
	public void setPaused(boolean b) {
		// TODO Auto-generated method stub
	}

	@Override
	public HashMap<Point, BottomEntity> getBotEntities() {
		return botEntities;
	}

	@Override
	public HashMap<Point, MidEntity> getMidEntities() {
		return midEntities;
	}

	@Override
	public HashMap<Point, TopEntity> getTopEntities() {
		return topEntities;
	}

	public void updateBotEntities(HashMap<Point, BottomEntity> bots) {
		Set<Entry<Point, BottomEntity>> ents = bots.entrySet();
		Iterator<Entry<Point, BottomEntity>> it = ents.iterator();
		Entry<Point, BottomEntity> ent;
		while(it.hasNext()){
			ent = it.next();
			botEntities.put(
					ent.getKey(), 
					ent.getValue());
		}
	}

	public void updateMidEntities(HashMap<Point, MidEntity> mids) {
		Set<Entry<Point, MidEntity>> ents = mids.entrySet();
		Iterator<Entry<Point, MidEntity>> it = ents.iterator();
		Entry<Point, MidEntity> ent;
		while(it.hasNext()){
			ent = it.next();
			midEntities.put(ent.getKey(), ent.getValue());
		}
	}

	public void updateTopEntities(HashMap<Point, TopEntity> tops) {
		Set<Entry<Point, TopEntity>> ents = tops.entrySet();
		Iterator<Entry<Point, TopEntity>> it = ents.iterator();
		Entry<Point, TopEntity> ent;
		while(it.hasNext()){
			ent = it.next();
			topEntities.put(ent.getKey(), ent.getValue());
		}
	}

}
