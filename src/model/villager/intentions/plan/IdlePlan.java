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
		super(villager);
		actionQueue = new LinkedList<Action>();
		actionQueue.addLast(new IdleAction(villager));
		name = "Just chillin";
	}
}
