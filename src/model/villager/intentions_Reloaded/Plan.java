package model.villager.intentions_Reloaded;

import java.util.LinkedList;

import model.villager.Villager;

public class Plan {

	protected Villager villager;
	//protected Action activeAction;
	
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
	}
}
