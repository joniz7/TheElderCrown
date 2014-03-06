package model.villager.intentions;

import model.villager.Villager;

public class DrinkIntent extends PrimitiveIntent {
	
	public DrinkIntent(Villager villager) {
		super(villager);
	}

	@Override
	public Plan getPlan() {
		plan = new DrinkPlan(villager);
		return plan;
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getThirst() * 10);
		//System.out.println("DrinkIntent: " + desire);
	}
	
}
