package model.villager.intention;

import java.awt.Point;

import resource.SoundP;
import model.TestWorld;
import model.path.FindObject;
import model.path.PathFinder;
import model.tile.WaterTile;
import model.villager.Brain;
import model.villager.Villager;

public class Drink extends Intention{

	private boolean hasIssuedMove;
	private boolean hasDrunk;
	
	
	public void act(Brain brain){
		if(!hasIssuedMove) {
			brain.walkToTileType(1);
//			SoundP.playSound("ph", "findwater.wav");
			hasIssuedMove = true;
		}
		
		if(brain.getVillager().drink())
			brain.activeTaskDone();
	}
	
}
