package model.villager.intention;

import model.villager.brain.Brain;
import util.ObjectType;

public class Eat extends Intention{

	private boolean hasIssuedMove;
	private boolean hasEaten;
	
	public void act(Brain brain){
		if(!hasIssuedMove && !brain.getVillager().eat()){
			brain.walkToObjectType(ObjectType.TREE);
			hasIssuedMove = true;
		}
		
		if(brain.getVillager().eat())
			brain.activeTaskDone();
		else if(!brain.getVillager().isMoving())
			brain.activeTaskDone();
	}
	
}
