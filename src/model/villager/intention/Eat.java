package model.villager.intention;

import util.EntityType;
import util.SoundP;
import model.villager.Villager;
import model.villager.brain.Brain;

public class Eat extends Intention{

	private boolean hasIssuedMove;
	private boolean hasEaten;
	
	public void act(Brain brain){
		if(!hasIssuedMove && !brain.getVillager().eat()){
			brain.walkToObjectType(EntityType.TREE);
			hasIssuedMove = true;
		}
		
		if(brain.getVillager().eat())
			brain.activeTaskDone();
		else if(!brain.getVillager().isMoving())
			brain.activeTaskDone();
	}
	
}
