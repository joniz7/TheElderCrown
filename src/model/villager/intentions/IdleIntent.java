package model.villager.intentions;

import model.villager.Villager;
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
		return new IdlePlan(villager);
	}

	@Override
	public void calculateDesire() {
		desire = -100;
	}
	
}
