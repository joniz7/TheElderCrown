package model.villager.intentions;

import java.util.PriorityQueue;

import model.villager.Villager;
import model.villager.intentions.gathering.GatherFoodIntent;

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
		//pq.add(new IdleIntent(villager));
		
		pq.add(new GatherFoodIntent(villager));
		
		//pq.add(new ExploreIntent(villager));
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
	
	/**
	 * Gets the topmost Intent's Plan.
	 * 
	 * Also removes the intent from the queue (unless it's a primitive intent)
	 * @author Niklas
	 */
	public Plan getFirstPlan(){
		Intent i = pq.peek();
		if (!(i instanceof Intent)) {
			pq.poll();
		}
		return i.getPlan();
	}
	
	/**
	 * Adds an intent to do something,
	 * which will be considered in the next update.
	 */
	public void addIntent(Intent i) {
		// System.out.println("addin "+i.toString());
		pq.add(i);
		update();
	}
	
}
