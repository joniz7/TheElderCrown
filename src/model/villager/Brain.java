package model.villager;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.util.pathfinding.Path;

import resource.ObjectID;
import head.Tickable;
import model.TestWorld;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.tile.WaterTile;
import model.villager.intention.Intention;
import model.villager.need.Hunger;
import model.villager.need.Sleepynes;
import model.villager.need.Thirst;

public class Brain implements Tickable{
	
	private Villager villager;
	private TestWorld world;
	
	private Hunger hunger = new Hunger(this);
	private Thirst thirst = new Thirst(this);
	private Sleepynes sleep = new Sleepynes(this);
	
	private LinkedList<Intention> intentions = new LinkedList<Intention>();
	
	private Intention activeIntention;
	
	private Path currentPath;
	
	public Brain(Villager villager, TestWorld world){
		this.villager = villager;
		this.world = world;
	}

	@Override
	public void tick(){
		hunger.tick();
		thirst.tick();
		sleep.tick();
		
		if(activeIntention == null && intentions.size() > 0){
			Intention intent = intentions.getFirst();
			intentions.removeFirst();
			
			activeIntention = intent;
		}
		
		if(activeIntention != null)
			activeIntention.act(this);
	}
	
	public void walkToTileType(int tileID){
		Point p = FindObject.findTileNeighbour(world, ObjectID.WATER_TILE, 
				villager.getTileX(), villager.getTileY());
		currentPath = PathFinder.getPath(villager.getTileX(), villager.getTileY(), 
				(int) p.getX(), (int) p.getY());
		villager.setMoving(true);
	}
	
	public void walkToObjectType(ObjectID id){
		Point p = FindObject.findObjectNeighbour(world, new HasFruit(), id, 
				villager.getTileX(), villager.getTileY());
		currentPath = PathFinder.getPath(villager.getTileX(), villager.getTileY(), 
				(int) p.getX(), (int) p.getY());
		villager.setMoving(true);
	}
	
	public void activeTaskDone(){
		activeIntention = null;
	}
	
	public void addIntention(Intention intention){
		intentions.addLast(intention);
	}
	
	public Hunger getHunger() {
		return hunger;
	}

	public Thirst getThirst() {
		return thirst;
	}

	public Sleepynes getSleep() {
		return sleep;
	}

	public Path getCurrentPath() {
		return currentPath;
	}

	public Villager getVillager() {
		return villager;
	}

	
	
	
}