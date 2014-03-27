package model.villager.intentions;

import util.Constants;
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
		super(villager);
	}

	
	@Override
	public Plan getPlan() {
		return new SleepPlan(villager);
	}

	@Override
	public void calculateDesire() {
		int time = villager.getTime();
		int hours = time / Constants.TICKS_HOUR;
		
		if(hours >= Constants.NIGHT_HOUR || hours < Constants.DAY_HOUR)
			setDesire(-villager.getSleepiness());
		else
			setDesire(-villager.getSleepiness() - 75);
	}

}
