package model.villager.intentions.routine;

import model.villager.Villager;
import model.villager.intentions.gathering.GatherDrinkPlan;
import model.villager.intentions.plan.IdlePlan;

public class WaterGathering extends Routine{
	
	public void imposeRoutine(Villager villager){
		if(villager.getActivePlan() instanceof IdlePlan){
			System.out.println("IMPOSE ROUTINE");
			villager.workReminder(new GatherDrinkPlan(villager));
		}
		
	}
	
}
