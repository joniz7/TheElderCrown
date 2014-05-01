package model.villager.intentions.plan;

import java.awt.Point;

import model.path.PathFinder;
import model.villager.Villager;
import model.villager.intentions.action.MoveAction;
import model.villager.intentions.action.TalkAction;
import model.villager.intentions.action.WaitForAction;

import org.newdawn.slick.util.pathfinding.Path;
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
	 * @param otherPos - the sender's position (position we should walk to)
	 * @param otherId - the id of the sender
	 */
	public SocialisePlan(Villager villager, Point otherPos, int otherId) {
		super(villager, "Joining social interaction");
		
		System.out.println(villager.getId()+" joining social interaction with "+otherId);
		// TODO roughly this:
		// MoveAction
		// WaitForAction
		// TalkAction

		// Move to position
		Point startPos = villager.getPosition();
		Path p = PathFinder.getPath(startPos.x, startPos.y, otherPos.x, otherPos.y);
		actionQueue.addLast(new MoveAction(villager, p));
		
		// WaitForAction
		actionQueue.addLast(new WaitForAction(villager, otherPos, otherId));
		
		// TalkAction
		actionQueue.addLast(new TalkAction(villager, otherPos, otherId));
	
		System.out.println("Starting to find another villager \n");
	}

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}

}