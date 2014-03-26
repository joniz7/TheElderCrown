package model.villager.intentions.reminder.profession;

import model.villager.Villager;
import model.villager.intentions.reminder.ProfessionLine;
import model.villager.intentions.routine.FoodGathering;

public class FoodGatherer extends ProfessionLine{

	public FoodGatherer(Villager villager, WorkLevel level) {
		super(villager, new FoodGathering(), level);
	}

}
