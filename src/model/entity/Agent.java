package model.entity;

import java.awt.Point;

import model.villager.intentions_Reloaded.Action;

public interface Agent {
	public void update(Point pos);
	public Action getAction();
	public void actionDone();
}
