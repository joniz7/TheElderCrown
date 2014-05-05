package model.villager.intentions.plan;

import java.awt.Point;
import java.beans.PropertyChangeEvent;

import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.IssueOrderAction;
import model.villager.intentions.action.TalkAction;
import model.villager.intentions.action.WaitForAction;
import model.villager.order.Order;
import util.Constants;

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
		super(villager, "Initiating social interaction");
		System.out.println(villager.getId()+" initiating social interaction");		

		this.otherId = otherId;
		
		// IssueOrderAction
		addAction(new IssueOrderAction(villager, otherOrder));
		
		// WaitForAction
		addAction(new WaitForAction(villager, otherPos, otherId));
		
		// TalkAction
		addAction(new TalkAction(villager, otherPos, otherId));
		
	}

	@Override
	public void retryAction() {
		throw new UnsupportedOperationException("SocialiseInitIntent should never receive retry");
	}
	
	/**
	 * Returns the villager we're initiating a social action with
	 */
	public int getOtherId() {
		return otherId;
	}
	
	@Override
	/**
	 * Handle events sent from any Action.
	 */
	public void propertyChange(PropertyChangeEvent e) {
		Action source = (Action) e.getSource();
		String status = (String) e.getNewValue();

		if ("fail".equals(status)) {
			if (source instanceof WaitForAction) {
				// become angrier at other party (atm, never socialise again)
				villager.decreaseRelation(otherId, -Constants.NEGATIVE_SOCIAL_IMPACT);
			}
		}
		
		// Let Plan do its things
		super.propertyChange(e);
	}


}