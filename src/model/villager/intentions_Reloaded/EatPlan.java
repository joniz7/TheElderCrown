package model.villager.intentions_Reloaded;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.util.pathfinding.Path;

import util.ObjectType;

import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.villager.Villager;

public class EatPlan extends Plan{

	private LinkedList<Action> actionQueue;
	
	public EatPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		
		Point p = FindObject.findObjectNeighbour(villager.getWorld(), new HasFruit(), ObjectType.TREE, 
				villager.getX(), villager.getY());
		Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
		
		
	}
}
