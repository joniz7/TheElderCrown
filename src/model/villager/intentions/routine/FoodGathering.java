package model.villager.intentions.routine;

import model.villager.Villager;
import model.villager.intentions.gathering.GatherFoodPlan;

public class FoodGathering extends Routine{
	
	protected Villager villager;
	
	public FoodGathering(Villager villager){
		super(villager);
	}
	
	public void imposeRoutine(){
		villager.workReminder(new GatherFoodPlan(villager));
	}
	
}
