package model.villager.intentions;

import model.villager.Villager;

public class ExploreIntent extends Intent {


	public ExploreIntent(Villager villager) {
		super(villager);
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
