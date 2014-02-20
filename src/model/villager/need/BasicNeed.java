package model.villager.need;

import util.Tickable;
import model.villager.brain.Brain;

public abstract class BasicNeed implements Tickable {
	
	protected float min, max, value;
	protected Brain brain;
	protected int cD = 50, cDRoof = 50;
	
	public BasicNeed(float min, float max, Brain brain){
		this.min = min;
		this.max = max;
		this.brain = brain;
	}
}
