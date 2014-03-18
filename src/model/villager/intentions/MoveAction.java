package model.villager.intentions;

import java.awt.Point;

import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.Criteria;
import model.path.criteria.IsFoodStorage;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class MoveAction extends Action{

	private Path path;
	private int progress, stepCount = 1, speedCap = 240;
	private int waitTime = 0;
	private EntityType type;
	private Criteria crit;
	
	
	public MoveAction(Villager villager, Path path) {
		super(villager);
		this.path = path;
		name = "Moving";
	}
	
	public MoveAction(Villager villager, EntityType type, Criteria crit) {
		super(villager);
		this.type = type;
		this.crit = crit;
		name = "Moving";
	}

	@Override
	public void tick(VillagersWorldPerception world) {
		if(path == null && type != null){
			Point p = FindObject.findObjectNeighbour(world, 
					crit, type, villager.getX(), villager.getY());
			path = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), 
					p.x, p.y);
			type = null;
		}
		
		if(path == null){
			actionFailed();
			return;
		}
		if(stepCount >= path.getLength()) {
			//        	System.out.println("Move FINISHED!!!");
	    	actionFinished();
	    }else if(!world.blocked(null, path.getStep(stepCount).getX(), 
    			path.getStep(stepCount).getY())){
	    	waitTime = 0;
			progress += villager.getSpeed();
			
			// Should move one step
		    if(progress > speedCap && stepCount < path.getLength()){
		    	progress = 0;
			
		//        		System.out.println("Step to: " + path.getStep(stepCount).getX()
		//        				+ ":" + path.getStep(stepCount).getY());
				Point newPos = new Point(path.getStep(stepCount).getX(),
						path.getStep(stepCount).getY());
				villager.attemptMove(newPos);
				stepCount++;
		    } // Has finished moving
		    
		    // Not enough progress to move one tile yet
		    else {
		    	// Calculate in which direction we're moving, and how far we've come
				int dx = path.getStep(stepCount).getX() - villager.getX();
				int dy = path.getStep(stepCount).getY() - villager.getY();
				double progressPercent = (double)progress/speedCap;
				// Send this interpolation information to view
				villager.updateViewPosition(dx, dy, progressPercent);
		    }
		} else {
			waitTime++;
			if(waitTime > 40){
				actionFailed();
//				System.out.println("WAIT!");
			}
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
