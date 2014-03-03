package model.villager.intentions;


import java.util.LinkedList;
import model.villager.Villager;
public class IdlePlan extends Plan {
	
	public IdlePlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		actionQueue.addLast(new IdleAction(villager));
		
	}
}
