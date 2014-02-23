package model.villager.intentions_Reloaded;

import java.awt.Point;
import java.util.LinkedList;

import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class EatPlan extends Plan{

	private LinkedList<Action> actionQueue;
	
	public EatPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		
		Point p = FindObject.findObjectNeighbour(villager.getWorld(), new HasFruit(), EntityType.TREE, 
				villager.getX(), villager.getY());
		Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
		actionQueue.add(new MoveAction(villager, movePath));
		
		actionQueue.addLast(new EatAction(villager));
	}
}
