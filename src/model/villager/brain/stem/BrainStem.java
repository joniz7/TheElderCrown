package model.villager.brain.stem;

import head.Tickable;
import model.villager.brain.Brain;
import model.villager.need.Hunger;
import model.villager.need.Sleepyness;
import model.villager.need.Thirst;

public class BrainStem implements Tickable{

	private Brain brain;
	
	private Hunger hunger;
	private Thirst thirst;
	private Sleepyness sleep;
	
	public BrainStem(Brain brain){
		this.brain = brain;
		hunger = new Hunger(brain);
		thirst = new Thirst(brain);
		sleep = new Sleepyness(brain);
	}

	@Override
	public void tick() {
		hunger.tick();
		thirst.tick();
		sleep.tick();
	}
	
	public Hunger getHunger() {
		return hunger;
	}

	public Thirst getThirst() {
		return thirst;
	}

	public Sleepyness getSleep() {
		return sleep;
	}
	
}
