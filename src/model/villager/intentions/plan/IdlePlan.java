package model.villager.intentions.plan;


import java.util.LinkedList;

import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.IdleAction;

/**
 * Very simple "plan" for how to idle. Might become more advanced later on.
 * 
 * @author Tux
 *
 */

public class IdlePlan extends Plan {
	
	public IdlePlan(Villager villager){
		super(villager, "Just chillin");
		actionQueue = new LinkedList<Action>();
		addAction(new IdleAction(villager));
	}

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}
}
