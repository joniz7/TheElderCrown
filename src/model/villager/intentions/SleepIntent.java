package model.villager.intentions;

import model.villager.Villager;

/**
 * Simple class to see when the villager needs to rest.
 * 
 * @author Tux
 *
 */

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
		setDesire(-villager.getSleepiness() * 3);
//		System.out.println("SleepIntent: " + desire);

	}

}
