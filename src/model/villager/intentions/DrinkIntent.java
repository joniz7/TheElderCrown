package model.villager.intentions;

import model.villager.Villager;

public class DrinkIntent extends Intent{

	public DrinkIntent(Villager villager) {
		super(0, villager);
	}

	@Override
	public Plan getPlan() {
		return new DrinkPlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getThirst() * 10);
//		System.out.println("DrinkIntent: " + desire);
	}
	
}
