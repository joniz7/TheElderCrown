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

}
