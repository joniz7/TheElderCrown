package model.villager.intentions;

import model.villager.Villager;

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
		setDesire(-villager.getHunger() * 4);
//		System.out.println("EatIntent: " + desire);
	}
	
}
