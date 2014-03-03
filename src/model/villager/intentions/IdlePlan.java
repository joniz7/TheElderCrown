package model.villager.intentions;


import java.util.LinkedList;
import model.villager.Villager;

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
		
	}
}
