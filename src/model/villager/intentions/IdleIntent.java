package model.villager.intentions;

import model.path.FindObject;
import model.path.criteria.DontBlock;
import model.villager.Villager;
import model.villager.intentions.plan.ExplorePlan;
import model.villager.intentions.plan.IdlePlan;
import model.villager.intentions.plan.Plan;

/**
 * Simple class for handling whether a villager wants to idle
 * @author Tux
 *
 */

public class IdleIntent extends Intent {

	public IdleIntent(Villager villager) {
		super(villager);
	}

	@Override
	public Plan getPlan(){
		if(FindObject.getAdjacentObject(villager.getWorld(), new DontBlock(), null, villager.getX(), villager.getY()) != null){
		return new ExplorePlan(villager);
		}
		return new IdlePlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getLaziness());
	}
	
}
