package model.villager.intentions.action;

import model.entity.bottom.Bed;
import model.entity.bottom.HouseFloor;
import model.path.FindObject;
import model.villager.Villager;
import util.Constants;
import util.EntityType;

/**
 * The sleeping action. Villager checks if on a Bed, house floor or grass tile.
 * Villager is not blocking while sleeping, and will sleep better on beds than floors.
 * 
 * @author Tux
 *
 */

public class SleepAction extends Action {

	private HouseFloor floor;
	private Bed thisBed;
	private boolean usedAtStart = false;
	private boolean firstTick = true;
	
	public SleepAction(Villager villager) {
		super(villager);
		name = "Sleeping";
	}
	
	public SleepAction(Villager villager, Bed thisBed) {
		super(villager);
		name = "Sleeping";
		this.thisBed = thisBed;
	}


	@Override
	public void tick(ImpactableByAction world){
//		if(FindObject.findTile2((TestWorld) world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY()) != null) {
//		System.out.println("SLEEP!!!");
		
		if(FindObject.standingOnTile(world, EntityType.BED, villager.getX(), villager.getY())){
			if(this.firstTick){
				//System.out.println("Sleeping on bed!");
				villager.setBlocking(false);
				thisBed.setUsed();
				System.out.println("Bed used by: "+thisBed.UsedBy());
				firstTick = false;
			}
			if(thisBed.UsedBy() > 1){
				villager.updateStatus("cuddling");
				System.out.println("Bed used by: "+thisBed.UsedBy());
				name = "Cuddling";
				villager.setPregnant(true);
			}else{
				villager.updateStatus("sleeping");
			}
			System.out.println("Bed used by: "+thisBed.UsedBy());
			villager.satisfySleep(thisBed.getSleepValue());
			if(villager.getSleepiness() >= Constants.MAX_SLEEP){
				if(villager.getMostNeed() > villager.getSleepiness() + 50){
					villager.setBlocking(true);
					villager.updateStatus("statusEnd");
					thisBed.removeUsed();
					actionFinished();
				}
			}
			
		}else if(FindObject.standingOnTile(world, EntityType.GRASS_TILE, villager.getX(), villager.getY())){
			if(this.firstTick){
				//System.out.println("Sleeping on grass!");
				villager.setBlocking(false);
				villager.updateStatus("sleeping");
				firstTick = false;
			}
			villager.satisfySleep(0.1f);
			if(villager.getSleepiness() >= Constants.MAX_SLEEP){
				villager.updateStatus("statusEnd");
				villager.setBlocking(true);
				actionFinished();
			}
		}else if(FindObject.standingOnTile(world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY())){
			if(this.firstTick){
				//System.out.println("Sleeping on floor!");
				villager.setBlocking(false);
				villager.updateStatus("sleeping");
				firstTick = false;
			}
			villager.satisfySleep(0.1f);
			if(villager.getSleepiness() >= Constants.MAX_SLEEP){
				villager.updateStatus("statusEnd");
				villager.setBlocking(true);
				actionFinished();
			}
		}else{
			villager.updateStatus("statusEnd");
			villager.setBlocking(true);
			actionFailed();
		}

	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
