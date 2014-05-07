package model.villager.intentions.plan;

import java.awt.Point;

import model.villager.Villager;

/**
 * A plan to socialise with another villager, whom have called out to us.
 * 
 * In other words, this is for the receiving party - to
 * initialise a social interaction, use SocialiseInitPlan
 * 
 * @author Niklas
 */
public class SocialisePlan extends Plan{

	/**
	 * Create a new SocialisePlan.
	 * 
	 * @param villager - the receiving villager, who should walk to the sender 
	 * @param otherPos - the sender's position
	 * @param otherId - the id of the sender
	 */
	public SocialisePlan(Villager villager, Point otherPos, int otherId) {
		super(villager, "Joining social interaction");
		
		// TODO roughly this:
		// MoveAction
		// WaitForAction
		// TalkAction
		
		System.out.println("Starting to find another villager \n");
	}

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}

}