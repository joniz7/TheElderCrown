package model.villager.need;

import model.villager.brain.Brain;
import model.villager.brain.Instinct;
import model.villager.intention.Eat;
import util.RandomClass;

public class Hunger extends BasicNeed{

	private float decay;
	
	public Hunger(Brain brain) {
		super(-100.0f, 100.0f, brain);
		value = 5.0f;
		int rnd = RandomClass.getRandomInt(10, 1);
		float rndF = (float) rnd;
		decay = 0.001f + (rndF / 10000);
	}

	@Override
	public void tick() {
		value = value - decay;
		
		cD = cD + 1;
		if(cD > cDRoof)
			cD = cDRoof;
		
		if(cD == cDRoof && value < 0.0f && RandomClass.getRandomInt(200, 0) == 0){
			triggerHunger();
		}
		else if(cD == cDRoof && value < -75.0f && RandomClass.getRandomInt(200, 0) == 0){
			triggerStarvation();
		}
	}
	
	public void satisfy(float amount){
		value = value + amount;
		if(value > max)
			value = max;
	}
	
	private void triggerHunger(){
		cD = 0;
		brain.input(new Instinct(new Eat()));
	}
	
	private void triggerStarvation() {
		cD = 0;
	}

}
