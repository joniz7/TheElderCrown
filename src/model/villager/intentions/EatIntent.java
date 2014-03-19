package model.villager.intentions;

import model.villager.Villager;
import model.villager.intentions.plan.EatPlan;
import model.villager.intentions.plan.Plan;

public class EatIntent extends PrimitiveIntent {

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
