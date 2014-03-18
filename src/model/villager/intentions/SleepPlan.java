package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.entity.bottom.HouseFloor;
import model.entity.mid.Bed;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.IsUnclaimed;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

/**
 * Class to plan sleep.
 * Villager will find closest HOUSE_FLOOR tile and the perform SleepAction
 * .
 * @author Tux
 *
 */

public class SleepPlan extends Plan {

	private Point bedPos;
	
	public SleepPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		name = "Wants to sleep";
		
		//Point floorPos = FindObject.findTile2(villager.getWorld(),EntityType.HOUSE_FLOOR, villager.getX(),villager.getY());
		
		if(villager.getBedPos()==null){
			bedPos = FindObject.findObject2(villager.getWorld(), new IsUnclaimed(villager), EntityType.BED, villager.getX(), villager.getY());
			villager.setBed(bedPos);
		}else{
			bedPos = villager.getBedPos();
		}
		
		
		if(bedPos != null){
			Bed thisBed = (Bed) villager.getWorld().getMidEntities().get(bedPos);
			if(villager.isMale()){
				thisBed.setClaimedByMale(true);
				thisBed.setMale(villager);
			}
			if(thisBed.isUsed()){
				thisBed.getOther().setBlocking(false);
				villager.setBlocking(false);
			}
			Path movePath = PathFinder.getPath(villager.getX(), villager.getY(), bedPos.x, bedPos.y);
			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new SleepAction(villager, thisBed));
		
		}else if(bedPos == null){
			actionQueue.addLast(new SleepAction(villager));
		
		}else{
			isFinished = true;
		}
	
		
	}
}