package model.villager.intentions;

import util.EntityType;
import model.TestWorld;
import model.World;
import model.entity.bottom.HouseFloor;
import model.path.FindObject;
import model.villager.Villager;

public class SleepAction extends Action {

	private float stacks, sleepToGet;
	private HouseFloor floor;
	
	public SleepAction(Villager villager) {
		super(villager);
		sleepToGet = villager.getSleepiness();
	}

	@Override
	public void tick(World world){
		if(FindObject.findTile2((TestWorld) world, EntityType.HOUSE_FLOOR, villager.getX(), villager.getY()) != null) {
			villager.satisfySleep(0.1f);
			stacks = stacks + 0.1f;
			if(stacks > sleepToGet)
				actionFinished();
		}else
			actionFailed();

	}

}
