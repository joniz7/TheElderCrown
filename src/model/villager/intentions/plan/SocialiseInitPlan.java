package model.villager.intentions.plan;

import java.awt.Point;

import model.villager.Villager;
import model.villager.intentions.action.IssueOrderAction;
import model.villager.intentions.action.TalkAction;
import model.villager.intentions.action.WaitForAction;
import model.villager.order.Order;

/**
 * A plan to socialise with another villager, whom have called out to us.
 * 
 * In other words, this is for the receiving party - to
 * initialise a social interaction, use SocialiseInitPlan
 * 
 * @author Niklas
 */
public class SocialiseInitPlan extends Plan{

	private int otherId;
	
	/**
	 * Create a new SocialisePlan.
	 * 
	 * @param villager - the receiving villager, who should walk to the sender 
	 * @param otherPos - the sender's position
	 * @param otherId - the id of the sender
	 */
	public SocialiseInitPlan(Villager villager, Order otherOrder, Point otherPos, int otherId) {
		super(villager, "Starting social interaction");
		
		this.otherId = otherId;
		
		// IssueOrderAction
		addAction(new IssueOrderAction(villager, otherOrder));
		
		// WaitForAction
		addAction(new WaitForAction(villager, otherPos, otherId));
		
		// TalkAction
		addAction(new TalkAction(villager, otherPos, otherId));
		
		System.out.println("Starting to find another villager \n");		
	}

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Returns the villager we're initiating a social action with
	 */
	public int getOtherId() {
		return otherId;
	}

}