package model.entity;

import java.io.Serializable;

import model.villager.AgentWorld;
import model.villager.Perception;
import model.villager.intentions.action.Action;
import util.Copyable;

public interface Agent extends Copyable, Serializable {
	public Action getAction();
	public void actionDone();
	void update(Perception p, int time);
	public boolean isDead();
	public AgentWorld getAgentWorld();
}
