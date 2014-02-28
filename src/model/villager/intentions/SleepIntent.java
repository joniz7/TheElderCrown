package model.villager.intentions;

import model.villager.Villager;

public class SleepIntent extends Intent {

	public SleepIntent(Villager villager) {
		super(0, villager);
	}

	
	@Override
	public Plan getPlan() {
		return new SleepPlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getSleepiness() * 2);
//		System.out.println("SleepIntent: " + desire);

	}

}
