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
		super(villager);
		Bed thisBed;
		actionQueue = new LinkedList<Action>();
		name = "Wants to sleep";
		this.villager = villager;
		
		
		//If villager has no assigned bed, find the closest unclaimed and claim it.
		if(villager.getBedPos()==null){
			IsUnclaimed bedcriteria = new IsUnclaimed(villager);
			System.out.println("SleepPlan: "+ bedcriteria.toString());
			bedPos = FindObject.findObject2(villager.getWorld(), bedcriteria, EntityType.BED, villager.getX(), villager.getY());
			villager.setBed(bedPos);
			thisBed = (Bed) villager.getWorld().getBotEntities().get(bedPos);
			if(thisBed != null){
				if(villager.isMale()){
					thisBed.setClaimedByMale(true);
					thisBed.setMale(villager);
				}else if(villager.isFemale()){
					thisBed.setClaimedByFemale(true);
					thisBed.setFemale(villager);
				}
			}
		//Else use already assigned bed
		}else{
			bedPos = villager.getBedPos();
			thisBed = (Bed) villager.getWorld().getBotEntities().get(bedPos);
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