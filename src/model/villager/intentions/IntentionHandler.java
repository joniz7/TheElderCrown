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
	// Whether any of the social intents has already been added
	private boolean initSocialise, joinSocialise; 
	
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
	
	/**
	 * Updates the priority queue of this IntentionHandler.
	 * May do nothing, if last update was too soon
	 */
	public void update() {
		update(false);
	}
	
	/**
	 * Updates the priority queue of this IntentionHandler.
	 * 
	 * @param force - whether we should force an update
	 */
	public void update(boolean force){
		
		// Calculate all intents' desires
		for (Intent p : pq) {
			p.calculateDesire();
		}
		
		//  Update order of intents
		// TODO necessary to use PQ like this? quite resource intensive
		if(force || timer % 150 == 0) {
			PriorityQueue<Intent> newPQ = new PriorityQueue<Intent>(6, intentComparator);
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
		} else if(activePlan != null) {
			activePlan = lastIntent.getPlan();
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
		update();
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
	/**
	 * @deprecated
	 * Adds an intent to do something,
	 * which will be considered in the next update.
	 * 
	 * @param social - true if added intent is a socialising intent
	 */
	public void addIntent(Intent i, boolean socialising) {
		pq.add(i);
		update();
		if (socialising) {
			if (i instanceof SocialiseIntent) {
				this.joinSocialise = true;
			}
			else if (i instanceof SocialiseInitIntent) {
				this.initSocialise = true;
			}
		}
	}
	
	/**
	 * @deprecated
	 * Returns whether any social intent has been added to the handler
	 */
	public boolean isSocialising() {
		return initSocialise || joinSocialise;
	}
	/**
	 * @deprecated
	 * Returns whether a SocialiseInitIntent has been added to the handler
	 */
	public boolean isInitSocialising() {
		return initSocialise;
	}
	/**
	 * @deprecated
	 * Returns whether a SocialiseIntent has been added to the handler
	 */
	public boolean isJoinSocialising() {
		return joinSocialise;
	}
	
	@Override
	/**
	 * Returns a string containing all intents currently in this Intention Handler.
	 */
	public String toString() {
		String s = "";
		for (Intent i : pq) {
			s += i.toString()+"\n";
		}
		return s;
	}
	
}
