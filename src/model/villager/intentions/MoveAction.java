package model.villager.intentions;

import java.awt.Point;

import model.World;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

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
		
		// Should move one step
        if(progress > speedCap && stepCount < path.getLength()){
        	progress = 0;
        	if(!world.blocked(null, path.getStep(stepCount).getX(), 
        			path.getStep(stepCount).getY())){
//        		System.out.println("Step to: " + path.getStep(stepCount).getX()
//        				+ ":" + path.getStep(stepCount).getY());
        		Point newPos = new Point(path.getStep(stepCount).getX(), 
        				path.getStep(stepCount++).getY());
        		world.moveVillager(villager, newPos);
        	} else {
//        		System.out.println("Move FAILED!!!");
        		actionFailed();
        	}
        } // Has finished moving
        else if(stepCount >= path.getLength()){
//        	System.out.println("Move FINISHED!!!");
        	actionFinished();
        }
        // Not enough progress to move one tile yet
        else {
        	// Calculate in which direction we're moving, and how far we've come
			int dx = path.getStep(stepCount).getX() - villager.getX();
			int dy = path.getStep(stepCount).getY() - villager.getY();
			double progressPercent = (double)progress/speedCap;
			// Send this interpolation information to view
			villager.updateViewPosition(dx, dy, progressPercent);
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
