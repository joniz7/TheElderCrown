package model.villager.intentions;

import model.villager.Villager;

public class EatIntent extends PrimitiveIntent {

	private Plan plan;
	
	public EatIntent(Villager villager) {
		super(villager);
	}

	@Override
	public Plan getPlan(){
		plan = new EatPlan(villager);
		return plan;
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getHunger() * 4);
//		System.out.println("EatIntent: " + desire);
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return plan.getCost();
	}
	
}
