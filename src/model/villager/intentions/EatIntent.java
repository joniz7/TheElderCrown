package model.villager.intentions;

import model.villager.Villager;
import model.villager.intentions.plan.EatPlan;
import model.villager.intentions.plan.Plan;

public class EatIntent extends PrimitiveIntent {
	
	public EatIntent(Villager villager) {
		super(villager);
	}

	@Override
	public Plan getPlan(){
		return new EatPlan(villager);
	}

	@Override
	public void calculateDesire() {
		if(villager.getHunger()>0){
			setDesire(-villager.getHunger());
		}else{
			setDesire(-villager.getHunger()*villager.getHowHungry());
		}
//		System.out.println("EatIntent: " + desire);
	}
	
}
