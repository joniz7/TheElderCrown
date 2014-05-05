package model.villager.intentions.plan;

import java.awt.Point;
import java.beans.PropertyChangeEvent;

import model.path.PathFinder;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.MoveAction;
import model.villager.intentions.action.TalkAction;
import model.villager.intentions.action.WaitForAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.Constants;
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

	private int otherId;
	
	/**
	 * Create a new SocialisePlan.
	 * 
	 * @param villager - the receiving villager, who should walk to the sender 
	 * @param myPos - the position we should walk to
	 * @param otherPos - the sender's position 
	 * @param otherId - the id of the sender
	 */
	public SocialisePlan(Villager villager, Point myPos, Point otherPos, int otherId) {
		super(villager, "Joining social interaction");
		
		this.otherId = otherId;
		
		System.out.println(villager.getId()+" joining social interaction with "+otherId);
		// TODO roughly this:
		// MoveAction
		// WaitForAction
		// TalkAction

		// Move to position
		Point startPos = villager.getPosition();
		Path p = PathFinder.getPath(startPos.x, startPos.y, myPos.x, myPos.y);
		addAction(new MoveAction(villager, p));
		
		// WaitForAction
		addAction(new WaitForAction(villager, otherPos, otherId));
		
		// TalkAction
		addAction(new TalkAction(villager, otherPos, otherId));
	
	}

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	/**
	 * Handle events sent from any Action
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