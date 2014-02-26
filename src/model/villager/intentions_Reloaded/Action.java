package model.villager.intentions_Reloaded;

import model.World;
import model.villager.Villager;

public abstract class Action {

	protected Villager villager;
	protected boolean isFinished, isFailed;
	
	public Action(Villager villager){
		this.villager = villager;
	}
	
	public abstract void tick(World world);
	protected abstract void actionFailed();
	protected abstract void actionFinished();
	
	public boolean isFinished() {
		return isFinished;
	}

	public boolean isFailed() {
		return isFailed;
	}
}
