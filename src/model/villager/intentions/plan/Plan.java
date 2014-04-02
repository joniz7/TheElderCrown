package model.villager.intentions.plan;

import java.util.LinkedList;

import model.villager.Villager;
import model.villager.intentions.action.Action;

public class Plan {

	protected Villager villager;
	protected boolean isFinished;
	private String name;
	
	protected LinkedList<Action> actionQueue;
	
	public Plan(Villager villager, String name) {
		this.villager = villager;
		this.name = name;
		actionQueue = new LinkedList<Action>();
	}
	
	public Action getActiveAction() {
		return actionQueue.peek();
	}
	
	public void actionDone() {
		if(actionQueue.size() <= 0){
			villager.disposePlan();
			return;
		}
		actionQueue.pop();
		if(actionQueue.size() <= 0)
			isFinished = true;
	}

	public boolean isFinished() {
		return isFinished;
	}
	
	public float getCost() {
		float cost = 0;
		for(Action a : actionQueue) {
			cost += a.getCost();
		}
		
		return cost;
	}

	public String getName() {
		return name;
	}
}
