package model.villager.brain;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.util.pathfinding.Path;

import resource.Helper;
import resource.ObjectID;
import head.Tickable;
import model.TestWorld;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.tile.WaterTile;
import model.villager.Villager;
import model.villager.brain.stem.BrainStem;
import model.villager.intention.Intention;
import model.villager.need.Hunger;
import model.villager.need.Sleepyness;
import model.villager.need.Thirst;

public class Brain implements Tickable{
	
	private Villager villager;
	private TestWorld world;
	
//	private Hunger hunger = new Hunger(this);
//	private Thirst thirst = new Thirst(this);
//	private Sleepyness sleep = new Sleepyness(this);
	
	private LinkedList<Intention> intentions = new LinkedList<Intention>();
	
	private Intention activeIntention;
	
	private Path currentPath;
	
	private BrainStem stem = new BrainStem(this);
	
	public Brain(Villager villager, TestWorld world){
		this.villager = villager;
		this.world = world;
	}

	@Override
	public void tick(){
		stem.tick();
		
		if(activeIntention == null && intentions.size() > 0){
			Intention intent = intentions.getFirst();
			intentions.removeFirst();
			
			activeIntention = intent;
		}
		
		if(activeIntention != null)
			activeIntention.act(this);
	}
	
	public void input(BrainInput input){
		if(input instanceof Instinct)
			this.addIntention(((Instinct) input).getIntent());
	}
	
	public void walkToTileType(int tileID){
		long startTime = System.currentTimeMillis();

		Point p = FindObject.findTileNeighbour(world, ObjectID.WATER_TILE, 
				villager.getTileX(), villager.getTileY());
		
		long endTime = System.currentTimeMillis();
		System.out.println("Brain, find water time: " + (endTime - startTime));

		if(endTime - startTime > 100)
			world.printArea(p);
		
		startTime = System.currentTimeMillis();
		
		currentPath = PathFinder.getPath(villager.getTileX(), villager.getTileY(), 
				(int) p.getX(), (int) p.getY());
		villager.setMoving(true);
		
		endTime = System.currentTimeMillis();
		System.out.println("Brain, path-find time to water: " + (endTime - startTime));
	}
	
	public void walkToObjectType(ObjectID id){
		long startTime = System.currentTimeMillis();
		
		Point p = FindObject.findObjectNeighbour(world, new HasFruit(), id, 
				villager.getTileX(), villager.getTileY());
		
		long endTime = System.currentTimeMillis();
		System.out.println("Brain, find Tree time: " + (endTime - startTime) + " : Point: " + p.toString());
		if(endTime - startTime > 100){
			world.printArea(p);
			world.setPaused(true);
			new Helper(villager.getTileX(), villager.getTileY());
		}
		
		startTime = System.currentTimeMillis();
		
		currentPath = PathFinder.getPath(villager.getTileX(), villager.getTileY(), 
				(int) p.getX(), (int) p.getY());
		villager.setMoving(true);
		
		endTime = System.currentTimeMillis();
		System.out.println("Brain, path-find time to Tree: " + (endTime - startTime));
	}
	
	public void activeTaskDone(){
		activeIntention = null;
	}
	
	public void addIntention(Intention intention){
		boolean hasIntent = false;
		for(Intention x : intentions)
			if(x.getClass() == intention.getClass()){
				hasIntent = true;
			}
		if(!hasIntent)
			intentions.addLast(intention);
	}
	
	public Path getCurrentPath() {
		return currentPath;
	}

	public Villager getVillager() {
		return villager;
	}

	public BrainStem getBrainStem() {
		return stem;
	}

	
	
	
}