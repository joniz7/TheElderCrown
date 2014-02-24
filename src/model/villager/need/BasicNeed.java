package model.villager.need;

import head.Tickable;
import model.villager.brain.Brain;

/**
 * Super class for all the basic needs such as thirst and hunger
 * 
 * @author Simon Eliasson
 *
 */
public abstract class BasicNeed implements Tickable {
	
	protected float min, max, value;
	protected Brain brain;
	
	public BasicNeed(float min, float max, Brain brain){
		this.min = min;
		this.max = max;
		this.brain = brain;
	}
}
