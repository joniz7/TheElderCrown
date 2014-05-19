package model.villager.intentions.action;

import model.entity.bottom.Bed;
import model.entity.bottom.HouseFloor;
import model.path.FindEntity;
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
	private boolean firstTick;
	
	public SleepAction(Villager villager) {
		super(villager, "Sleeping");
		this.firstTick = true;
	}
	
	public SleepAction(Villager villager, Bed thisBed) {
		super(villager, "Sleeping");
		this.thisBed = thisBed;
		this.firstTick = true;
		//villager.setBlocking(false);
	}


	@Override
	public void tick(ImpactableByAction world){
//		if(FindObject.findTile2((TestWorld) world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY()) != null) {
//		System.out.println("SLEEP!!!");
		
		if(FindEntity.standingOnTile(villager.getWorld(), EntityType.BED, villager.getX(), villager.getY())){
			if(thisBed == null){
				thisBed = (Bed) FindEntity.getObjectOnTile(villager.getAgentWorld(), EntityType.BED, villager.getX(), villager.getY());
			}
			if(this.firstTick){
				//System.out.println("Sleeping on bed!");
				thisBed.use(villager);
				villager.updateStatus("sleeping");
				firstTick = false;
			}
			if(thisBed.isFemaleInBed() && thisBed.isMaleInBed()){
				villager.updateStatus("cuddling");
				//System.out.println("Bed used by >1!");
				//TODO: Change name of action.
				thisBed.getFemale().setPregnant(true, thisBed.getMale());
			}else{
				villager.updateStatus("sleeping");
			}
			villager.satisfySleep(thisBed.getSleepValue());
			if(villager.getSleepiness() >= Constants.MAX_SLEEP){
				//if(villager.getMostNeed() > villager.getSleepiness() + 50){
					villager.updateStatus("statusEnd");
					thisBed.stopUsing(villager);
					actionSuccess();
				//}
			}
			
		}else if(FindEntity.standingOnTile(villager.getWorld(), EntityType.GRASS_TILE, villager.getX(), villager.getY())){
			if(this.firstTick){
				//System.out.println("Sleeping on grass!");
				//villager.setBlocking(false);
				villager.updateStatus("sleeping");
				firstTick = false;
			}
			villager.satisfySleep(0.17f);
			if(villager.getSleepiness() >= Constants.MAX_SLEEP){
				villager.updateStatus("wakeup");
				villager.updateStatus("statusEnd");
				villager.setBlocking(true);
				actionSuccess();
				villager.sleptOutsideLastNight = true;
			}
		}else if(FindEntity.standingOnTile(villager.getWorld(), EntityType.HOUSE_FLOOR, villager.getX(), villager.getY())){
			if(this.firstTick){
				//System.out.println("Sleeping on floor!");
				//villager.setBlocking(false);
				villager.updateStatus("sleeping");
				firstTick = false;
			}
			villager.satisfySleep(0.17f);
			if(villager.getSleepiness() >= Constants.MAX_SLEEP){
				villager.updateStatus("wakeup");
				villager.updateStatus("statusEnd");
				villager.setBlocking(true);
				actionSuccess();
			}
		}else{
			villager.updateStatus("statusEnd");
			villager.setBlocking(true);
			actionFail();
		}

	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
