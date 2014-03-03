package model.villager.intentions;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.villager.Villager;

public class IdleAction extends Action {

	private int stacks, idleTime;
	private boolean firstTick = true;
	
	public IdleAction(Villager villager) {
		super(villager);
		idleTime = 300;
	}

	@Override
	public void tick(World world){
		if(firstTick) {
			villager.updateStatus("idling");
		}
		stacks++;
		if(stacks > idleTime){
			villager.updateStatus("statusEnd");
			actionFinished();
		}
			

	}

}
