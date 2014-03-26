package model.villager.intentions.routine;

import model.villager.Villager;
import model.villager.intentions.gathering.GatherDrinkPlan;

public class WaterGathering extends Routine{
	
	public void imposeRoutine(Villager villager){
		villager.workReminder(new GatherDrinkPlan(villager));
	}
	
}
