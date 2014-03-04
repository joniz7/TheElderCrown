package model.villager.order;

import java.awt.Desktop.Action;

/**
 * An order given to an Agent.
 * Consists of an action, and the sender/receiver of the order.
 * 
 * @author Niklas
 */
public class Order {

	// Sender and receiver of order
	private int fromId, toId;

	// TODO should probably consist of more than an action
	private Action action;
	
	public Order(int fromId, int toId, Action action) {
		this.fromId = fromId;
		this.toId = toId;
		this.action = action;
	}

	public int getFromId() {
		return fromId;
	}

	public int getToId() {
		return toId;
	}

	public Action getAction() {
		return action;
	}
	
	
	
}
