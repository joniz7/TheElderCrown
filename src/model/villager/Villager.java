package model.villager;

import head.Tickable;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import resource.SoundP;
import model.TestWorld;
import model.entity.GraphicalEntity;
import model.path.FindObject;
import model.path.PathFinder;
import model.tile.WaterTile;
import model.villager.intention.Intention;

public class Villager extends GraphicalEntity implements Tickable{

	private Brain brain;
	private TestWorld world;
	
	private int tileX, tileY;

	private int speedCap = 240, progress = 0, speed = 20, stepCount = 0;
	private boolean moving = false;
	
	public Villager(TestWorld world, int tileX, int tileY){
		super("villager");
		this.world = world;
		this.tileX = tileX;
		this.tileY = tileY;
		updatePos(tileX * 20, tileY * 20);
		brain = new Brain(this, world);
	}

	@Override
	public void tick(){
		brain.tick();

		if(moving)
			move();
		else
			updatePos(tileX * 20, tileY * 20);
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
        
        updatePos((tileX * 20) + (interPolX * (progress/12)), 
    			(tileY * 20) + (interPolY * (progress/12)));
	}
	
	public boolean eat(){
		if(FindObject.isAdjacentObject(world, 101, tileX, tileY)){
			if(world.getTree(tileX + 1, tileY) != null){
				if(world.getTree(tileX + 1, tileY).isFruit()){
					world.getTree(tileX + 1, tileY).eaten();
					brain.getHunger().satisfy(14);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
				
			}
			
			if(world.getTree(tileX - 1, tileY) != null){
				if(world.getTree(tileX - 1, tileY).isFruit()){
					world.getTree(tileX - 1, tileY).eaten();
					brain.getHunger().satisfy(14);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(tileX, tileY + 1) != null){
				if(world.getTree(tileX, tileY + 1).isFruit()){
					world.getTree(tileX, tileY + 1).eaten();
					brain.getHunger().satisfy(14);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(tileX, tileY - 1) != null){
				if(world.getTree(tileX, tileY - 1).isFruit()){
					world.getTree(tileX, tileY - 1).eaten();
					brain.getHunger().satisfy(14);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean drink(){
		if(FindObject.isAdjacentTile(world, 1, tileX, tileY)){
			brain.getThirst().satisfy(10);
			SoundP.playSound("ph", "drink.wav");
			return true;
		}
		return false;
	}
	
	public void sleep(){
		brain.getSleep().satisfy(100);
		SoundP.playSound("ph", "sleep.wav");
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		stepCount = 0;
	}
	
	
}
