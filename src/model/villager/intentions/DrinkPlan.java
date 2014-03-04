package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.path.FindObject;
import model.path.PathFinder;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class DrinkPlan extends Plan{

	public DrinkPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		
		Point p = FindObject.findTileNeighbour(villager.getWorld(), EntityType.WATER_TILE, 
				villager.getX(), villager.getY());
		Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
		actionQueue.add(new MoveAction(villager, movePath));
		
		actionQueue.addLast(new DrinkAction(villager));
	}
}
