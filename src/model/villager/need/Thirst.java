package model.villager.need;

import model.villager.brain.Brain;
import model.villager.brain.Instinct;
import model.villager.intention.Drink;
import util.RandomClass;

public class Thirst extends BasicNeed{
	
	private float decay;
	
	public Thirst(Brain brain) {
		super(-100.0f, 100.0f, brain);
		value = 1.0f;
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
		
		if(cD == cDRoof && value < 0 && RandomClass.getRandomInt(10, 0) == 0){
			triggerThirst();
		}
		
		else if(cD == cDRoof && value < -75 && RandomClass.getRandomInt(10, 0) == 0){
			triggerStarvation();
		}
	}
	
	public void satisfy(float amount){
		value = value + amount;
		if(value > max)
			value = max;
	}

	private void triggerThirst(){
		cD = 0;
		brain.input(new Instinct(new Drink()));
	}
	
	private void triggerStarvation() {
		
	}
}
