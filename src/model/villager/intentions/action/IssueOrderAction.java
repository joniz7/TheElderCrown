package model.villager.intentions.action;

import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import model.villager.order.Order;

/**
 * Issues an order to another entity.
 * 
 * @author Niklas
 */
public class IssueOrderAction extends Action {

	private Order o;
	
	/**
	 * constructor
	 * 
	 * @param villager - the villager to give an order
	 * @param o - The order to be issued. Will be given to receiving entity the next tick
	 */
	public IssueOrderAction(Villager villager, Order o) {
		super(villager);
		this.o = o;		
	}

	@Override
	public void tick(ImpactableByAction world) {
		// TODO issue order to REAL world, not to any imaginary perception or whatever
		// world.addOrder(o);
		actionFinished();
	}

	@Override
	public float getCost() {
		return 0;
	}

}
