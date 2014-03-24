package model.villager.intentions.routine;

import model.villager.Villager;
import model.villager.intentions.gathering.GatherDrinkPlan;
import model.villager.intentions.gathering.GatherFoodPlan;

public class WaterGathering extends Routine{
	
	public void imposeRoutine(Villager villager){
		villager.workReminder(new GatherDrinkPlan(villager));
	}
	
}
