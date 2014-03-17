package model.entity;

import java.io.Serializable;

import model.villager.Perception;
import model.villager.intentions.Action;
import util.Copyable;

public interface Agent extends Copyable, Serializable {
	public Action getAction();
	public void actionDone();
	void update(Perception p);
	public boolean isDead();
}
