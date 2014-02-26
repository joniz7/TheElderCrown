package model.villager.intentions_Reloaded;

import java.awt.Point;
import java.util.LinkedList;

import model.TestWorld;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class EatPlan extends Plan{

	public EatPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		
		System.out.println("EatPlan: TICK ");
		Point p = FindObject.findObjectNeighbour((TestWorld)villager.getWorld(), new HasFruit(), EntityType.TREE, 
				villager.getX(), villager.getY());
		System.out.println("EatPlan: TICK 2");
		Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
		System.out.println("EatPlan: TICK 3");
		actionQueue.add(new MoveAction(villager, movePath));
		System.out.println("EatPlan: TICK 4");
		actionQueue.addLast(new EatAction(villager));
		System.out.println("EatPlan: TICK 5");
	}
}
