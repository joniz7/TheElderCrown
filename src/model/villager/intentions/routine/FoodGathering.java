package model.villager.intentions.routine;

import model.villager.Villager;
import model.villager.intentions.gathering.GatherFoodPlan;

public class FoodGathering extends Routine{
	
	public void imposeRoutine(Villager villager){
		villager.workReminder(new GatherFoodPlan(villager));
	}
	
}
