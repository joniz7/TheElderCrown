package model.villager.intentions_Reloaded;

import model.villager.Villager;

public class EatIntent extends Intent{

	public EatIntent(Villager villager) {
		super(250, villager);
	}

	@Override
	public Plan getPlan() {
		return new EatPlan(villager);
	}

	@Override
	public void calculateDesire() {
		if(villager.getHunger() < 0)
			this.setDesire(-villager.getHunger());
		System.out.println("EatIntent: " + desire);
	}

	
}
