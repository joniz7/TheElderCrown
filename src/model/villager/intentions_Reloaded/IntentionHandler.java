package model.villager.intentions_Reloaded;

import java.util.PriorityQueue;

import model.villager.Villager;

public class IntentionHandler {

	private PriorityQueue<Intent> pq;
	
	private EatIntent eatInt;
	private DrinkIntent drinkInt;
	
	public IntentionHandler(Villager villager){
		pq = new PriorityQueue<Intent>(5, new IntentComparator());
	
		eatInt = new EatIntent(villager);
		drinkInt = new DrinkIntent(villager);
		
		pq.add(eatInt);
		pq.add(drinkInt);
	}
	
	public void update(){
		eatInt.calculateDesire();
		drinkInt.calculateDesire();
		
		PriorityQueue<Intent> newPQ = new PriorityQueue<Intent>(5, new IntentComparator());
		while(!pq.isEmpty())
			newPQ.add(pq.poll());
		pq = newPQ;
	}
	
	public Plan getFirstPlan(){
		return pq.peek().getPlan();
	}

	public EatIntent getEatInt() {
		return eatInt;
	}
	
	
	
}
