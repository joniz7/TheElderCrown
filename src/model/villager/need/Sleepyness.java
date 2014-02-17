package model.villager.need;

import model.villager.brain.Brain;
import model.villager.intention.Sleep;
import resource.RandomClass;

public class Sleepyness extends BasicNeed{

	public Sleepyness(Brain brain) {
		super(-100, 100.0f, brain);
		value = 100.0f;
	}

	@Override
	public void tick() {
		value = value - 0.00001f;
		
		if(value < 0 && RandomClass.getRandomInt(10, 0) == 0){
			triggerSleepy();
		}
		else if(value < -75 && RandomClass.getRandomInt(10, 0) == 0){
			triggerInsomnia();
		}
	}
		
	public void satisfy(float amount){
		value = value + amount;
		if(value > max)
			value = max;
	}
	
	private void triggerSleepy() {
		brain.addIntention(new Sleep());
	}
	
	private void triggerInsomnia() {
		
	}

	

}
