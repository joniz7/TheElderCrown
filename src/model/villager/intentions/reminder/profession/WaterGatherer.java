package model.villager.intentions.reminder.profession;

import model.villager.Villager;
import model.villager.intentions.reminder.ProfessionLine;
import model.villager.intentions.routine.FoodGathering;
import model.villager.intentions.routine.Routine;
import model.villager.intentions.routine.WaterGathering;

public class WaterGatherer extends ProfessionLine{

	public WaterGatherer(Villager villager, WorkLevel level) {
		super(villager, new WaterGathering(), level);
	}

}
