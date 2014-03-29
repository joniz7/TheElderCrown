package model.villager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import model.entity.Entity;
import model.entity.bottom.NullTile;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import util.Constants;

public class AgentWorld implements TileBasedMap {
	
	private HashMap<Point, Entity> botEntities;
	private HashMap<Point, Entity> midEntities;
	private HashMap<Point, Entity> topEntities;
	
	/**
	 * Constructor
	 */
	public AgentWorld(){
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
		Point p = new Point(x,y);
		if(botEntities.get(p) != null && botEntities.get(p).isBlocking()){
			return true;
		}
		if(midEntities.get(p) != null && midEntities.get(p).isBlocking()){
			return true;
		}
		if(topEntities.get(p) != null && topEntities.get(p).isBlocking()){
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

	public HashMap<Point, Entity> getBotEntities() {
		return botEntities;
	}

	public HashMap<Point, Entity> getMidEntities() {
		return midEntities;
	}

	public HashMap<Point, Entity> getTopEntities() {
		return topEntities;
	}

	public void updateBotEntities(HashMap<Point, Entity> bots) {
		Iterator<Entry<Point, Entity>> it = bots.entrySet().iterator();
		Entry<Point, Entity> ent;
		while(it.hasNext()){
			ent = it.next();
			botEntities.put(
					ent.getKey(), 
					ent.getValue());
		}
	}
	
	public void updateMidEntity(Entity ent, Point pos){
		midEntities.put(pos, ent);
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
