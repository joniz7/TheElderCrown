package model.villager.intentions_Reloaded;

import java.util.PriorityQueue;

import model.villager.Villager;

public class IntentionHandler {

	private PriorityQueue<Intent> pq;
	
	private EatIntent eatInt;
	
	public IntentionHandler(Villager villager){
		pq = new PriorityQueue<Intent>(5, new IntentComparator());
	
		eatInt = new EatIntent(villager);
		pq.add(eatInt);
	}
	
	public Plan getFirstPlan(){
		return pq.peek().getPlan();
	}

	public EatIntent getEatInt() {
		return eatInt;
	}
	
	
	
}
