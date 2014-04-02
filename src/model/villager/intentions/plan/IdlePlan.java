package model.villager.intentions.plan;


import java.util.LinkedList;

import model.path.FindEntity;
import model.path.criteria.DontBlock;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.ExploreAction;
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
		actionQueue.addLast(new IdleAction(villager));
	}
}
