package model.villager.intentions_Reloaded;

import java.util.LinkedList;

import model.villager.Villager;

public class Plan {

	protected Villager villager;
	protected boolean isFinished;
	
	protected LinkedList<Action> actionQueue;
	
	public Plan(Villager villager) {
		super();
		this.villager = villager;
	}
	
	public Action getActiveAction() {
		return actionQueue.peek();
	}
	
	public void actionDone() {
		actionQueue.pop();
		if(actionQueue.size() <= 0)
			isFinished = true;
	}

	public boolean isFinished() {
		return isFinished;
	}
}
