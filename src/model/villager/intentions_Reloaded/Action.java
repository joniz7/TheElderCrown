package model.villager.intentions_Reloaded;

import model.TestWorld;
import model.villager.Villager;

public abstract class Action {

	protected Villager villager;
	
	public Action(Villager villager){
		this.villager = villager;
	}
	
	public abstract void tick(TestWorld world);
	
}
