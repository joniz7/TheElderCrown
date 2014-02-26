package model.villager.intentions_Reloaded;

import java.awt.Point;

import org.newdawn.slick.util.pathfinding.Path;

import model.World;
import model.villager.Villager;

public class MoveAction extends Action{

	private Path path;
	private int progress, stepCount = 1, speedCap = 240;
	
	
	public MoveAction(Villager villager, Path path) {
		super(villager);
		this.path = path;
	}

	@Override
	public void tick(World world) {
		progress += villager.getSpeed();
		
//		int interPolX = 0;
//		int interPolY = 0;
		
//		System.out.println("StepCount MoveACtion: " + stepCount);
//		System.out.println("Path Length MoveACtion: " + path.getLength());
//		System.out.println("Progress MoveACtion: " + progress);
		
//		if(stepCount < path.getLength()){
//			interPolX = path.getStep(stepCount).getX() - villager.getX();
//			interPolY = path.getStep(stepCount).getY() - villager.getY();
//		}else{
//			setMoving(false);
//		}
		
        if(progress > speedCap && stepCount < path.getLength()){
        	progress = 0;
        	if(!world.blocked(null, path.getStep(stepCount).getX(), 
        			path.getStep(stepCount).getY())){
//        		System.out.println("Step to: " + path.getStep(stepCount).getX()
//        				+ ":" + path.getStep(stepCount).getY());
        		Point newPos = new Point(path.getStep(stepCount).getX(), 
        				path.getStep(stepCount++).getY());
        		world.moveVillager(villager, newPos);
        	}else{
//        		System.out.println("Move FAILED!!!");
        		actionFailed();
        	}
        }else if(stepCount >= path.getLength()){
//        	System.out.println("Move FINISHED!!!");
        	actionFinished();
        }
	}
	
//	public boolean isMoving() {
//		return moving;
//	}
//
//	public void setMoving(boolean moving) {
//		this.moving = moving;
//		stepCount = 0;
//	}

	@Override
	protected void actionFailed() {
		isFailed = true;
	}

	@Override
	protected void actionFinished() {
		isFinished = true;
	}

	
	
	
}
