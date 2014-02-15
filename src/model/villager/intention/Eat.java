package model.villager.intention;

import resource.ObjectID;
import resource.SoundP;
import model.villager.Brain;
import model.villager.Villager;

public class Eat extends Intention{

	private boolean hasIssuedMove;
	private boolean hasEaten;
	
	public void act(Brain brain){
		if(!hasIssuedMove){
			brain.walkToObjectType(ObjectID.TREE);
//			SoundP.playSound("ph", "findnuts.wav");
			hasIssuedMove = true;
		}
		
		if(brain.getVillager().eat())
			brain.activeTaskDone();
		else if(!brain.getVillager().isMoving())
			brain.activeTaskDone();
	}
	
}
