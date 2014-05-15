package model.villager.order;

import model.villager.intentions.Intent;

/**
 * An order given to an Agent.
 * Consists of an intent, and the sender/receiver of the order.
 * 
 * @author Niklas
 */
public class Order {

	// Sender and receiver of order
	private int fromId, toId;

	// TODO should probably consist of more than an intent
	protected Intent intent;
	
	public Order(int fromId, int toId, Intent intent) {
		this.fromId = fromId;
		this.toId = toId;
		this.intent = intent;
	}

	public int getFromId() {
		return fromId;
	}

	public int getToId() {
		return toId;
	}

	public Intent getIntent() {
		return intent;
	}
	
	
	
}
