package model.villager.intentions;

import model.villager.Villager;

public class SleepIntent extends PrimitiveIntent {

	public SleepIntent(Villager villager) {
		super(0, villager);
	}

	
	@Override
	public Plan getPlan() {
		return new SleepPlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getSleepiness() * 3);
//		System.out.println("SleepIntent: " + desire);

	}

}
