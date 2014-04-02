package model.villager.intentions.action;

import model.villager.Villager;

public abstract class Action {

	protected Villager villager;
	protected boolean isSuccess, isFailed;
	private String name;
	
	public Action(Villager villager, String name){
		this.villager = villager;
		this.name = name;
	}
	
	public abstract void tick(ImpactableByAction world);
	
	public abstract float getCost();
	
	public boolean isFinished() {
		return isSuccess;
	}

	public boolean isFailed() {
		return isFailed;
	}
	
	/**
	 * Aborts the current action.
	 */
	protected void actionFail() {
		isFailed = true;
	}
	
	/**
	 * Aborts the current plan.
	 * TODO implement when plan/action system is reasonable
	 */
	protected void planFail() {
//		isFailed = true;
	}

	/**
	 * Marks the current action as successful!
	 */
	protected void actionSuccess() {
		isSuccess = true;
	}

	public String getName() {
		return name;
	}
}
