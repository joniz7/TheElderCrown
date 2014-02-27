package model.entity;

import java.awt.Point;

import model.villager.intentions.Action;

public interface Agent {
	public void update(Point pos);
	public Action getAction();
	public void actionDone();
	public boolean isDead();
}
