package model.villager.intentions_Reloaded;

import java.awt.Point;

import org.newdawn.slick.util.pathfinding.Path;

import model.TestWorld;
import model.villager.Villager;

public class MoveAction extends Action{

	private Path path;
	private int progress, stepCount, speedCap = 240;
	private boolean moving;
	
	public MoveAction(Villager villager, Path path) {
		super(villager);
		this.path = path;
	}

	@Override
	public void tick(TestWorld world) {
		progress += villager.getSpeed();
		
		int interPolX = 0;
		int interPolY = 0;
		
		if(stepCount < path.getLength()){
			interPolX = path.getStep(stepCount).getX() - villager.getX();
			interPolY = path.getStep(stepCount).getY() - villager.getY();
		}else{
			setMoving(false);
		}
		
        if(progress > speedCap && stepCount < path.getLength()){
        	progress = 0;
        	if(!world.blocked(null, path.getStep(stepCount).getX(), 
        			path.getStep(stepCount).getY())){
        		Point newPos = new Point(path.getStep(stepCount).getX(), 
        				path.getStep(stepCount++).getY());
        		villager.update(newPos);
        	}else{
        		actionFailed();
        	}
        }else if(stepCount >= path.getLength()){
        	actionFinished();
        }
	}
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		stepCount = 0;
	}

	@Override
	protected void actionFailed() {
		// TODO What to do here?
	}

	@Override
	protected void actionFinished() {
		// TODO What to do here?
	}
}
