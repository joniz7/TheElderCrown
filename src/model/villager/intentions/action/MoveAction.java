package model.villager.intentions.action;

import java.awt.Point;

import model.path.FindEntity;
import model.path.PathFinder;
import model.path.criteria.Criteria;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class MoveAction extends Action{

	private Path path;
	private int progress, stepCount = 1, speedCap = 240;
	private int waitTime = 0;
	private EntityType type;
	private Criteria crit;
	
	
	public MoveAction(Villager villager, Path path) {
		super(villager, "Moving");
		this.path = path;
	}
	
	public MoveAction(Villager villager, EntityType type, Criteria crit) {
		super(villager, "Moving");
		this.type = type;
		this.crit = crit;
	}

	@Override
	public void tick(ImpactableByAction world) {
		if(path == null && crit != null){
			Point p = FindEntity.findTopMidEntityNeighbour(villager.getWorld(), 
					crit, type, villager.getX(), villager.getY());

			if(p == null){
				System.out.println("Didn't find what Villager was looking for");
				villager.actionDone();
				villager.disposePlan();
				villager.clearInventory();
				villager.setExplore();
			}else
				path = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), 
						p.x, p.y);
			type = null;
		}
		
		if(path == null){
			actionFail();
			return;
		}
		if(stepCount >= path.getLength()) {
			//        	System.out.println("Move FINISHED!!!");
	    	actionSuccess();
	    }else if(!world.blocked(null, path.getStep(stepCount).getX(), 
    			path.getStep(stepCount).getY())){
	    	waitTime = 0;
			progress += villager.getSpeed();
			
			//check if the villager has the next step of the path next to it.
			if(!(Math.abs(villager.getX()-path.getStep(stepCount).getX())==1 ||
					Math.abs(villager.getY()-path.getStep(stepCount).getY())==1)){
				actionFail();
			}
			
			
			// Should move one step
		    if(progress > speedCap && stepCount < path.getLength()){
		    	progress = 0;
			
//		        		System.out.println("Step to: " + path.getStep(stepCount).getX()
//		        				+ ":" + path.getStep(stepCount).getY() + " from: "
//		        				+ villager.getX() + ":" + villager.getY());
		        		
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
				actionFail();
//				System.out.println("WAIT!");
			}
		}
	}
	
	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		if(path != null) {
			return path.getLength();
		}else{
			return 0;
		}
	}

	
	
	
}
