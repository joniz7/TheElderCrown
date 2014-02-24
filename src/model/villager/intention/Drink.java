package model.villager.intention;

import model.villager.brain.Brain;

public class Drink extends Intention{

	private boolean hasIssuedMove;
	private boolean hasDrunk;
	
	
	public void act(Brain brain){
		if(!hasIssuedMove && !brain.getVillager().drink()){
			brain.walkToTileType(1);
			hasIssuedMove = true;
		}
		
		if(brain.getVillager().drink()){
			brain.activeTaskDone();
		}
	}
	
}
