package model.villager;

import model.TestWorld;
import model.entity.Agent;
import model.entity.MidEntity;
import model.path.FindObject;
import model.villager.brain.Brain;
import util.EntityType;
import util.SoundP;
import util.Tickable;

public class Villager extends MidEntity implements Tickable, Agent{

	private Brain brain;
	private TestWorld world;

	private int speedCap = 240, progress = 0, speed = 20, stepCount = 0;
	private boolean moving = false;
	
	public Villager(TestWorld world, int x, int y){
		super(x , y, EntityType.VILLAGER);
		this.world = world;
		updatePos(x, y);
		brain = new Brain(this, world);
	}

	@Override
	public void tick(){
		brain.tick();

		if(moving)
			move();
		else
			updatePos(x , y);
	}
	
	public void move(){
		progress += speed;
		
		int interPolX = 0;
		int interPolY = 0;
		
		if(stepCount < brain.getCurrentPath().getLength()){
			interPolX = brain.getCurrentPath().getStep(stepCount).getX() - x;
			interPolY = brain.getCurrentPath().getStep(stepCount).getY() - y;
		}else{
			setMoving(false);
		}
		
        if(progress > speedCap && stepCount < brain.getCurrentPath().getLength()){
        	progress = 0;
        	x = brain.getCurrentPath().getStep(stepCount).getX();
        	y = brain.getCurrentPath().getStep(stepCount++).getY();
        }
        
        updatePos(x, y, (interPolX * (progress/12)), (interPolY * (progress/12)));
	}
	
	public boolean eat(){
		if(FindObject.isAdjacentObject(world, EntityType.TREE, x, y)){
			if(world.getTree(x + 1, y) != null){
				if(world.getTree(x + 1, y).hasFruit()){
					world.getTree(x + 1, y).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(x - 1, y) != null){
				if(world.getTree(x - 1, y).hasFruit()){
					world.getTree(x - 1, y).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(x, y + 1) != null){
				if(world.getTree(x, y + 1).hasFruit()){
					world.getTree(x, y + 1).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(x, y - 1) != null){
				if(world.getTree(x, y - 1).hasFruit()){
					world.getTree(x, y - 1).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean drink(){
		if(FindObject.isAdjacentTile(world, EntityType.WATER_TILE, x, y)){
			brain.getBrainStem().getThirst().satisfy(5);
			SoundP.playSound("ph", "drink.wav");
			return true;
		}
		return false;
	}
	
	public void sleep(){
		brain.getBrainStem().getSleep().satisfy(100);
		SoundP.playSound("ph", "sleep.wav");
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		stepCount = 0;
	}

	public TestWorld getWorld() {
		return world;
	}
	
	
	
}
