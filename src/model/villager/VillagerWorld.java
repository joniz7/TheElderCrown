package model.villager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import model.entity.Entity;
import model.entity.bottom.NullTile;

import org.newdawn.slick.util.pathfinding.PathFindingContext;

import util.Constants;

public class VillagerWorld implements VillagersWorldPerception {
	
	private HashMap<Point, Entity> botEntities;
	private HashMap<Point, Entity> midEntities;
	private HashMap<Point, Entity> topEntities;
	
	/**
	 * Constructor
	 */
	public VillagerWorld(){
		botEntities = new HashMap<Point, Entity>();
		midEntities = new HashMap<Point, Entity>();
		topEntities = new HashMap<Point, Entity>();
		NullTile tile;
		for(int i=0; i<Constants.WORLD_WIDTH; i++){
			for(int j=0; j<Constants.WORLD_HEIGHT; j++){
				tile = new NullTile(i, j);
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
	public HashMap<Point, Entity> getBotEntities() {
		return botEntities;
	}

	@Override
	public HashMap<Point, Entity> getMidEntities() {
		return midEntities;
	}

	@Override
	public HashMap<Point, Entity> getTopEntities() {
		return topEntities;
	}

	public void updateBotEntities(HashMap<Point, Entity> bots) {
		Set<Entry<Point, Entity>> ents = bots.entrySet();
		Iterator<Entry<Point, Entity>> it = ents.iterator();
		Entry<Point, Entity> ent;
		while(it.hasNext()){
			ent = it.next();
			botEntities.put(
					ent.getKey(), 
					ent.getValue());
		}
	}

	public void updateMidEntities(HashMap<Point, Entity> mids) {
		Set<Entry<Point, Entity>> ents = mids.entrySet();
		Iterator<Entry<Point, Entity>> it = ents.iterator();
		Entry<Point, Entity> ent;
		while(it.hasNext()){
			ent = it.next();
			midEntities.put(ent.getKey(), ent.getValue());
		}
	}

	public void updateTopEntities(HashMap<Point, Entity> tops) {
		Set<Entry<Point, Entity>> ents = tops.entrySet();
		Iterator<Entry<Point, Entity>> it = ents.iterator();
		Entry<Point, Entity> ent;
		while(it.hasNext()){
			ent = it.next();
			topEntities.put(ent.getKey(), ent.getValue());
		}
	}

}
