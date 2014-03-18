package model.villager.intentions;

import java.util.LinkedList;

import model.villager.Villager;

public class Plan {

	// TODO do we need this reference later?
	protected Villager villager;
	protected boolean isFinished;
	protected String name;
	
	protected LinkedList<Action> actionQueue;
	
	public Plan(Villager villager) {
		this.villager = villager;
		actionQueue = new LinkedList<Action>();
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

	public String getName() {
		return name;
	}
	
	
}
