package model.villager.intention;

import java.awt.Point;

import util.SoundP;
import model.TestWorld;
import model.entity.bottom.WaterTile;
import model.path.FindObject;
import model.path.PathFinder;
import model.villager.Villager;
import model.villager.brain.Brain;

public class Drink extends Intention{

	private boolean hasIssuedMove;
	private boolean hasDrunk;
	
	
	public void act(Brain brain){
//		if(!hasIssuedMove && !brain.getVillager().drink()){
//			brain.walkToTileType(1);
//			hasIssuedMove = true;
//		}
//		
//		if(brain.getVillager().drink()){
//			brain.activeTaskDone();
//		}
	}
	
}
