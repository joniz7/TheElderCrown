package model.villager.intentions;

import model.villager.Villager;
import model.villager.intentions.plan.ExplorePlan;
import model.villager.intentions.plan.Plan;

public class ExploreIntent extends Intent {


	public ExploreIntent(Villager villager) {
		super(0, villager);
	}

	@Override
	public Plan getPlan(){
		return new ExplorePlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getLaziness());
//		System.out.println("EatIntent: " + desire);
	}
	
}
