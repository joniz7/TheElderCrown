package model.entity;

import java.awt.Point;

import model.villager.intentions.Action;
import model.villager.order.Order;

public interface Agent {
	// TODO should use Perception, which may include Orders 
	public void update(Point pos, Order o);
	public Action getAction();
	public void actionDone();
	public boolean isDead();
}
