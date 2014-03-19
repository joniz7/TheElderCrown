package model.villager.intentions.action;

import model.villager.Villager;
import model.villager.VillagersWorldPerception;

public abstract class Action {

	protected Villager villager;
	protected boolean isFinished, isFailed;
	protected String name;
	
	public Action(Villager villager){
		this.villager = villager;
	}
	
	public abstract void tick(VillagersWorldPerception world);
	
	public boolean isFinished() {
		return isFinished;
	}

	public boolean isFailed() {
		return isFailed;
	}
	
	protected void actionFailed() {
		isFailed = true;
	}

	protected void actionFinished() {
		isFinished = true;
	}

	public String getName() {
		return name;
	}
}
