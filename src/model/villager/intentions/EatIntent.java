package model.villager.intentions;

import model.villager.Villager;

public class EatIntent extends Intent{

	public EatIntent(Villager villager) {
		super(0, villager);
	}

	@Override
	public Plan getPlan(){
		return new EatPlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getHunger() * 4);
//		System.out.println("EatIntent: " + desire);
	}
	
}
