package model.villager.intentions.routine;

import model.villager.Villager;

public abstract class Routine {
	
	protected Villager villager;
	
	public Routine(Villager villager){
		this.villager = villager;
	}
	
	public abstract void imposeRoutine();
	
}
