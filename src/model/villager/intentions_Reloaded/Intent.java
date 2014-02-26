package model.villager.intentions_Reloaded;

import model.villager.Villager;

public abstract class Intent{

	protected int cost, desire;
	protected Villager villager;
	
	public Intent(int cost, Villager villager){
		this.cost = cost;
		this.villager = villager;
	}
	
	public abstract Plan getPlan();
	public abstract void calculateDesire();
	
	public void adjustDesire(int difference){
		desire = desire + difference;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDesire() {
		return desire;
	}

	public void setDesire(int desire) {
		this.desire = desire;
	}
	
	
}
