package model.villager.intentions.action;

import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import model.villager.order.Order;

public class IssueOrderAction extends Action {

	private Order o;
	
	public IssueOrderAction(Villager villager, Order o) {
		super(villager);
		this.o = o;		
	}

	@Override
	public void tick(VillagersWorldPerception world) {
		world.addOrder(o);
		actionFinished();
	}

	@Override
	public float getCost() {
		return 0;
	}

}
