package model.villager.intentions;

import util.EntityType;
import model.entity.bottom.HouseFloor;
import model.entity.mid.Bed;
import model.path.FindObject;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;

/**
 * The sleeping action. Villager checks if on a HOUSE_FLOOR tile and rests there for a while.
 * 
 * @author Tux
 *
 */

public class SleepAction extends Action {

	private float stacks, sleepToGet;
	private HouseFloor floor;
	private Bed thisBed;
	private boolean usedAtStart = false;
	
	public SleepAction(Villager villager) {
		super(villager);
		sleepToGet = 250; // villager.getSleepiness();
		name = "Sleeping";
	}

	public SleepAction(Villager villager, Bed thisBed) {
		super(villager);
		this.thisBed = thisBed;
		if(thisBed.isUsed()){
			usedAtStart = true;
		}
	}

	@Override
	public void tick(VillagersWorldPerception world){
//		if(FindObject.findTile2((TestWorld) world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY()) != null) {

		
		if(FindObject.standingOnObject(world, EntityType.BED, villager.getX(), villager.getY())){
			villager.satisfySleep(0.2f);
			stacks = stacks + 0.3f;
			if(stacks > sleepToGet)
				if(usedAtStart){
					thisBed.getOther(villager).setBlocking(true);
					villager.setBlocking(true);
					}
				actionFinished();
			
		}else if(FindObject.standingOnTile(world, EntityType.GRASS_TILE, villager.getX(), villager.getY())){
			villager.satisfySleep(0.1f);
			stacks = stacks + 0.1f;
			if(stacks > sleepToGet)
				actionFinished();
			
		}else if(FindObject.standingOnTile(world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY())){
			villager.satisfySleep(0.1f);
			stacks = stacks + 0.2f;
			if(stacks > sleepToGet)
				actionFinished();
			
		}else
			actionFailed();

	}

}
