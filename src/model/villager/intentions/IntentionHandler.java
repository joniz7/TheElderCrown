package model.villager.intentions;

import java.util.PriorityQueue;

import model.villager.Villager;

public class IntentionHandler {

	private PriorityQueue<Intent> pq;
	private IntentComparator intentComparator;
	
	public IntentionHandler(Villager villager){

		// Initialize intention queue (= planning algorithm)
		intentComparator = new IntentComparator();
		pq = new PriorityQueue<Intent>(5, intentComparator);
	
		// Add primitive intents
		pq.add(new EatIntent(villager));
		pq.add(new DrinkIntent(villager));
		pq.add(new SleepIntent(villager));
	}
	
	public void update(){
		
		// Calculate all intent's desires
		for (Intent p : pq) {
			p.calculateDesire();
		}
		
		//  Update order of intents
		// TODO necessary to use PQ like this? quite resource intensive
		PriorityQueue<Intent> newPQ = new PriorityQueue<Intent>(5, intentComparator);
		while(!pq.isEmpty())
			newPQ.add(pq.poll());
		pq = newPQ;
	}
	
	public Plan getFirstPlan(){
		return pq.peek().getPlan();
	}
	
	/**
	 * Adds an intent to do something,
	 * which will be considered in the next update.
	 */
	public void addIntent(Intent i) {
		pq.add(i);
	}
	
}
