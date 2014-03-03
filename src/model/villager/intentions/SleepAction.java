package model.villager.intentions;

import util.EntityType;
import model.TestWorld;
import model.World;
import model.entity.bottom.HouseFloor;
import model.path.FindObject;
import model.villager.Villager;

/**
 * The sleeping action. Villager checks if on a HOUSE_FLOOR tile and rests there for a while.
 * 
 * @author Tux
 *
 */

public class SleepAction extends Action {

	private float stacks, sleepToGet;
	private HouseFloor floor;
	
	public SleepAction(Villager villager) {
		super(villager);
		sleepToGet = 250; // villager.getSleepiness();
	}

	@Override
	public void tick(World world){
		if(FindObject.findTile2((TestWorld) world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY()) != null) {
			villager.satisfySleep(0.1f);
			stacks = stacks + 0.2f;
			if(stacks > sleepToGet)
				actionFinished();
		}else
			actionFailed();

	}

}
