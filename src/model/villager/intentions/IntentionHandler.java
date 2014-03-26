package model.villager.intentions;

import java.util.PriorityQueue;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;

public class IntentionHandler {

	private int timer;
	
	private PriorityQueue<Intent> pq;
	private IntentComparator intentComparator;
	
	private Plan activePlan;
	private Intent lastIntent;
	
	private Villager villager;
	
	public IntentionHandler(Villager villager){

		timer = (int)(Math.random()*100);
		
		this.villager = villager;
		
		// Initialize intention queue (= planning algorithm)
		intentComparator = new IntentComparator();
		pq = new PriorityQueue<Intent>(5, intentComparator);
	
		// Add primitive intents
		pq.add(new EatIntent(villager));
		pq.add(new DrinkIntent(villager));
		pq.add(new SleepIntent(villager));
		pq.add(new IdleIntent(villager));
		
		//pq.add(new ExploreIntent(villager));
	}
	
	public void update(){
		
		
		// Calculate all intent's desires
		for (Intent p : pq) {
			p.calculateDesire();
		}
		
		//  Update order of intents
		// TODO necessary to use PQ like this? quite resource intensive
		if(timer % 150 == 0) {
			PriorityQueue<Intent> newPQ = new PriorityQueue<Intent>(5, intentComparator);
			while(!pq.isEmpty())
				newPQ.add(pq.poll());
			pq = newPQ;
		}
		timer++;
	}
	
	/**
	 * Gets the topmost Intent's Plan.
	 * 
	 * @author Niklas
	 */
	public Plan getFirstPlan(){
		if(lastIntent != pq.peek()) {
			lastIntent = pq.peek();
			activePlan = lastIntent.getPlan();
			villager.updateStatus("statusEnd");
		}
		
		return activePlan;
	}
	
	/**
	 * Removes the topmost Intent if it's not a PrimitiveIntent
	 * 
	 * @author Jonathan Orrö
	 */
	public void intentDone(){
		if(!(pq.peek() instanceof PrimitiveIntent)) {
			System.out.println("GET NEW PLAN");
			pq.poll();
		}
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
