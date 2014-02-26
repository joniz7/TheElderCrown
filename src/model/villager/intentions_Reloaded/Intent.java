package model.villager.intentions_Reloaded;

import model.villager.Villager;

public abstract class Intent{

	protected float cost, desire;
	protected Villager villager;
	
	public Intent(int cost, Villager villager){
		this.cost = cost;
		this.villager = villager;
	}
	
	public abstract Plan getPlan();
	public abstract void calculateDesire();
	
	public void adjustDesire(float f){
		desire = desire + f;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getDesire() {
		return desire;
	}

	public void setDesire(float desire) {
		this.desire = desire;
	}
	
	
}
