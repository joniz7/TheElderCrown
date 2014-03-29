package model.villager.intentions;

import model.villager.Villager;
import model.villager.intentions.plan.DrinkPlan;
import model.villager.intentions.plan.Plan;

public class DrinkIntent extends PrimitiveIntent {
	
	public DrinkIntent(Villager villager) {
		super(villager);
	}

	@Override
	public Plan getPlan() {
		return new DrinkPlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getThirst());
	}
	
}
