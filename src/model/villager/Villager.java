package model.villager;

import head.Tickable;
import model.TestWorld;
import model.entity.MiddleLayerGraphicalEntity;
import model.path.FindObject;
import model.villager.brain.Brain;
import resource.ObjectID;
import resource.SoundP;

public class Villager extends MiddleLayerGraphicalEntity implements Tickable{

	private Brain brain;
	private TestWorld world;

	private int speedCap = 240, progress = 0, speed = 20, stepCount = 0;
	private boolean moving = false;
	
	public Villager(TestWorld world, int x, int y){
		super("villager", x , y, ObjectID.VILLAGER);
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
			updatePos(tileX , tileY);
	}
	
	public void move(){
		progress += speed;
		
		int interPolX = 0;
		int interPolY = 0;
		
		if(stepCount < brain.getCurrentPath().getLength()){
			interPolX = brain.getCurrentPath().getStep(stepCount).getX() - tileX;
			interPolY = brain.getCurrentPath().getStep(stepCount).getY() - tileY;
		}else{
			setMoving(false);
		}
		
        if(progress > speedCap && stepCount < brain.getCurrentPath().getLength()){
        	progress = 0;
        	tileX = brain.getCurrentPath().getStep(stepCount).getX();
        	tileY = brain.getCurrentPath().getStep(stepCount++).getY();
        }
        
        updatePos(tileX, tileY, (interPolX * (progress/12)), (interPolY * (progress/12)));
	}
	
	public boolean eat(){
		if(FindObject.isAdjacentObject(world, ObjectID.TREE, tileX, tileY)){
			if(world.getTree(tileX + 1, tileY) != null){
				if(world.getTree(tileX + 1, tileY).hasFruit()){
					world.getTree(tileX + 1, tileY).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(tileX - 1, tileY) != null){
				if(world.getTree(tileX - 1, tileY).hasFruit()){
					world.getTree(tileX - 1, tileY).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(tileX, tileY + 1) != null){
				if(world.getTree(tileX, tileY + 1).hasFruit()){
					world.getTree(tileX, tileY + 1).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(tileX, tileY - 1) != null){
				if(world.getTree(tileX, tileY - 1).hasFruit()){
					world.getTree(tileX, tileY - 1).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean drink(){
		if(FindObject.isAdjacentTile(world, ObjectID.WATER_TILE, tileX, tileY)){
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
	
	
}
