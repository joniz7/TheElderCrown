package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;

import model.entity.bottom.Bed;
import model.path.FindEntity;
import model.path.PathFinder;
import model.path.criteria.IsUnclaimed;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.MoveAction;
import model.villager.intentions.action.SleepAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

/**
 * Class to plan sleep.
 * Villager will find closest unclaimed (or claimed by opposite sex)
 * bed and perform a sleep action. If no bed can be found,
 * villager will sleep on floor or grass.
 * Villager will sleep better on beds than floor/grass.
 * Villagers are not blocking while sleeping.
 * 
 * .
 * @author Tux
 *
 */

public class SleepPlan extends Plan {

	private Point bedPos;
	private Villager villager;
	
	public SleepPlan(Villager villager){
		super(villager, "Wants to sleep");
		Bed thisBed;
		actionQueue = new LinkedList<Action>();
		this.villager = villager;
		
		
		//If villager has no assigned bed, find the closest unclaimed and claim it.
		if(this.villager.getBed()==null){
			IsUnclaimed bedcriteria = new IsUnclaimed(villager);
			bedPos = FindEntity.findTopMidEntity(villager.getWorld(), bedcriteria, EntityType.BED, villager.getX(), villager.getY());
			if(bedPos == null){
				actionQueue.addLast(new SleepAction(villager));
			}
			villager.setBed(bedPos);
			thisBed = (Bed) villager.getWorld().getBotEntities().get(bedPos);
			villager.setBed(thisBed);
			if(thisBed != null){
				if(villager.isMale()){
					thisBed.setMaleClaimant(villager);
				}else if(villager.isFemale()){
					thisBed.setFemaleClaimant(villager);
				}
			}
		//Else use already assigned bed
		}else{
			bedPos = villager.getBedPos();
			thisBed = villager.getBed();
		}
		
		
		if(bedPos != null){
			/*if(thisBed.isUsed()){
				thisBed.getOther(villager).setBlocking(false);
				villager.setBlocking(false);
			}*/
			
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