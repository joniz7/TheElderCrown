package model.villager.intentions;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;
import model.villager.intentions.plan.SleepPlan;

/**
 * Simple class to see when the villager needs to rest.
 * 
 * @author Tux
 *
 */
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
		int time = villager.getTime();
		int hours = time / 750;
		
		if(hours > 22 || hours < 8)
			setDesire(-villager.getSleepiness() + 100);
		else
			setDesire(-villager.getSleepiness() - 100);
		
		System.out.println("Sleepy: " + desire + " at hour " + hours);
	}

}
