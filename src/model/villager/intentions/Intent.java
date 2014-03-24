package model.villager.intentions;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;

public abstract class Intent{

	protected float desire;
	protected Villager villager;
	
	public Intent(Villager villager){
		this.villager = villager;
	}
	
	public abstract Plan getPlan();
	public abstract void calculateDesire();
	
	public void adjustDesire(float f){
		desire = desire + f;
	}

	public float getDesire() {
		return desire;
	}

	public void setDesire(float desire) {
		this.desire = desire;
	}
	
	
}
