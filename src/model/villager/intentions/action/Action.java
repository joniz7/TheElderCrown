package model.villager.intentions.action;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;

public abstract class Action {

	protected Villager villager;
	protected boolean isSuccess, isFailed;
	private String name;
	
	private PropertyChangeSupport pcs;
	
	public Action(Villager villager, String name){
		this.villager = villager;
		this.name = name;
		this.pcs = new PropertyChangeSupport(this);
	}
	
	public abstract void tick(ImpactableByAction world);
	
	public abstract float getCost();
	
	/**
	 * @deprecated
	 */
	public boolean isSuccessful() {
		return isSuccess;
	}

	/**
	 * @deprecated
	 */
	public boolean isFailed() {
		return isFailed;
	}
	
	/**
	 * Aborts the current action.
	 * @deprecated
	 */
	protected void actionFail() {
		isFailed = true;
	}
	
	/**
	 * Aborts the current plan.
	 * TODO implement when plan/action system is reasonable
	 * @deprecated
	 */
	protected void planFail() {
//		isFailed = true;
	}

	/**
	 * Marks the current action as successful!
	 * @deprecated
	 */
	protected void actionSuccess() {
		isSuccess = true;
	}

	/**
	 * Returns the name of this action.
	 * (e.g. "Eating" or "Moving")
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Marks this action as successful!
	 * This moves forward in the plan. 
	 */
	protected final void success() {
		villager.updateStatus("statusEnd"); // update image
		pcs.firePropertyChange("status", null, "success");
	}
	
	/**
	 * Retry this action.
	 * This makes the plan create a new instance of this Action  
	 */
	protected final void retry() {
		villager.updateStatus("statusEnd"); // update image
		pcs.firePropertyChange("status", null, "retry");
	}
	
	/**
	 * Fails this action.
	 * This fails the entire plan - use with caution
	 */
	protected final void fail() {
		villager.updateStatus("statusEnd"); // update image
		pcs.firePropertyChange("status", null, "fail");
	}
	
	/**
	 * Adds a listener that should be notified when this action changes state.
	 * Should typically only be a Plan.
	 * 
	 * The following events may be emitted,
	 * and should be handled by listener:
	 *  success - this action has succeeded
	 *  retry  - this action should be re-created (TODO should this be handled by listener?)
	 *  fail    - this action has failed. Plan should fail
	 *  
	 * @param listener - The Plan containing this action
	 */
	public void addListener(PropertyChangeListener listener) {
		if (!(listener instanceof Plan)) {
			System.err.println("Warning: something other than a Plan started listening to an action!");
		}
		pcs.addPropertyChangeListener(listener);
	}
}
