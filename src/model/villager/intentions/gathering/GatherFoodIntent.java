package model.villager.intentions.gathering;

import model.villager.Villager;
import model.villager.intentions.Intent;
import model.villager.intentions.action.IdleAction;
import model.villager.intentions.plan.IdlePlan;
import model.villager.intentions.plan.Plan;

public class GatherFoodIntent extends Intent{

	public GatherFoodIntent(Villager villager) {
		super(0, villager);
	}

	@Override
	public Plan getPlan(){
		return new GatherFoodPlan(villager);
	}

	@Override
	public void calculateDesire() {
		desire = -20;
	}

}
