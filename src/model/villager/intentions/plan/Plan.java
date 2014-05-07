package model.villager.intentions.plan;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import model.villager.Villager;
import model.villager.intentions.action.Action;

public abstract class Plan implements PropertyChangeListener{

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
	
	/**
	 * @deprecated
	 */
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

	/**
	 * Recreates the current action and places it first in the queue.
	 * Needs to be specifik for each plan.
	 */
	public abstract void retryAction();
	
	/**
	 * Adds an action to the action queue
	 */
	protected final void addAction(Action a) {
//		a.addListener(this);
		actionQueue.add(a);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
