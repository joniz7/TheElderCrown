package model.entity;

import model.villager.Perception;
import model.villager.intentions.Action;

public interface Agent {
	public Action getAction();
	public void actionDone();
	void update(Perception p);
}
