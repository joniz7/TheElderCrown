package model.villager.intentions;

import model.villager.Villager;

public class SocialIntent extends Intent{

	public SocialIntent(Villager villager) {
		super(0, villager);
		
	}

	@Override
	public Plan getPlan(){
		return new SocialPlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getSocial() * 4);
//		System.out.println("EatIntent: " + desire);
	}
}
