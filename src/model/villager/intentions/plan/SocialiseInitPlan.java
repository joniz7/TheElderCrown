package model.villager.intentions.plan;

import java.awt.Point;

import model.villager.Villager;
import model.villager.intentions.action.IssueOrderAction;
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

	/**
	 * Create a new SocialisePlan.
	 * 
	 * @param villager - the receiving villager, who should walk to the sender 
	 * @param otherPos - the sender's position
	 * @param otherId - the id of the sender
	 */
	public SocialiseInitPlan(Villager villager, Order otherOrder, Point otherPos, int otherId) {
		super(villager);
		
		// IssueOrderAction
		actionQueue.addLast(new IssueOrderAction(villager, otherOrder));
		
		// WaitForAction
		
		// TalkAction
		
		System.out.println("Starting to find another villager \n");
		
		
		/*
		Villager otherVillager = (Villager) FindObject.getAdjacentObject(villager.getWorld(), new isSocial(),
		EntityType.VILLAGER, villager.getX(), villager.getY());
		
		
		
		
		if(otherVillager != null){
		actionQueue.addLast(new SocialAction(villager));
		otherVillager.setAction(new SocialPlan(otherVillager));
		}else{
		Point p = FindObject.findObjectNeighbour(villager.getWorld(), new isSocial(), EntityType.VILLAGER,
		villager.getX(), villager.getY());
		Path movePath = null;
		if(p != null)
		movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
		
		actionQueue.add(new MoveAction(villager, movePath));
		actionQueue.addLast(new SocialAction(villager));
		*/
		
	}

}