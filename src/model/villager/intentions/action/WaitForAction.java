package model.villager.intentions.action;

import java.awt.Point;

import model.entity.Entity;
import model.villager.Villager;

/**
 * Stands and waits for another entity.
 * 
 * Expects a specific entity at a specific position, and continually checks this.
 * Eventually gives up and becomes sad, should the rendezvous subject not appear
 * 
 * @author Niklas
 */
public class WaitForAction extends Action {

	private Point otherPos;
	private int otherId;
	// How long we should wait for the other entity
	private int ticksToWait;
	
	/**
	 * The villager who is waiting
	 * @param villager
	 * @param otherPos - the position we're expecting company at
	 * @param otherId - the ID of the entity we're waiting for
	 */
	public WaitForAction(Villager villager, Point otherPos, int otherId) {
		super(villager, "Waiting");
		villager.updateStatus("waiting");
		
		this.otherPos = otherPos;
		this.otherId = otherId;
		this.ticksToWait = 300;
		
	}

	@Override
	public void tick(ImpactableByAction world) {

		// Look at position
		Entity other = world.getMidEntities().get(otherPos);
		// We found who we're waiting for!
		if (other != null && other.getId() == otherId) {
			villager.updateStatus("statusEnd");
			actionSuccess();
		}
		// We did not find who we're waiting for, yet
		else {
			if (--ticksToWait < 0) {
				System.out.println("I'm done waiting for you!");
				villager.updateStatus("statusEnd");
				actionFail();
			} else {
//				System.out.println("Waiting a lil bit longer... "+ticksToWait);
			}
		}
		
		

		
	}

	@Override
	public float getCost() {
		return 0;
	}

}
