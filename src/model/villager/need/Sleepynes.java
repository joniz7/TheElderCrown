package model.villager.need;

import model.villager.Brain;
import model.villager.intention.Sleep;
import resource.RandomClass;

public class Sleepynes extends BasicNeed{

	public Sleepynes(Brain brain) {
		super(-100, 100.0f, brain);
		value = 100.0f;
	}

	@Override
	public void tick() {
		value = value - 0.01f;
		
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
		System.out.println("Feel sleepy!");
		brain.addIntention(new Sleep());
	}
	
	private void triggerInsomnia() {
		// TODO Auto-generated method stub
	}

	

}
