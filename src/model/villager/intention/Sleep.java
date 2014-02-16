package model.villager.intention;

import model.villager.Villager;
import model.villager.brain.Brain;

public class Sleep extends Intention{

	public void act(Brain brain){
		System.out.println("Sleep!");
//		villager.sleep();
	}
	
}
