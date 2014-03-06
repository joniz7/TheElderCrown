package model.villager.intentions;

import model.villager.Villager;

public class SleepIntent extends PrimitiveIntent {

	private Plan plan;
	
	public SleepIntent(Villager villager) {
		super(villager);
	}

	
	@Override
	public Plan getPlan() {
		plan = new SleepPlan(villager);
		return plan;
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getSleepiness() * 3);
//		System.out.println("SleepIntent: " + desire);

	}


	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return plan.getCost();
	}

}
